package io.keeplearning.bookcatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.keeplearning.bookcatalogservice.model.Book;
import io.keeplearning.bookcatalogservice.model.CatalogItem;
import io.keeplearning.bookcatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class BookCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;

    @HystrixCommand(fallbackMethod = "getFallbackCatalog")
    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratings/users/"+userId, UserRating.class);
        return userRating.getUserRatings().stream()
                .map(rating -> {
                    Book book = restTemplate.getForObject("http://book-info-service/books/" + rating.getBookId(), Book.class);
                    return new CatalogItem(book.getName(), book.getDescription(), rating.getRating());
                })
                .collect(Collectors.toList());
    }

    public List<CatalogItem> getFallbackCatalog(@PathVariable("userId") String userId) {
        return Arrays.asList(new CatalogItem("Book", "", 0));
    }
}

/*Book book = webClientBuilder.build()
                    .get()
                    .uri("http://book-info-service/books/"+rating.getBookId())
                    .retrieve()
                    .bodyToMono(Book.class)
                    .block();*/