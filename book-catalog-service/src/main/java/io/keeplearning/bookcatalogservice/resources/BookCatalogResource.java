package io.keeplearning.bookcatalogservice.resources;

import io.keeplearning.bookcatalogservice.model.Book;
import io.keeplearning.bookcatalogservice.model.CatalogItem;
import io.keeplearning.bookcatalogservice.model.Rating;
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

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating userRating = restTemplate.getForObject("http://localhost:8083/ratings/users/"+userId, UserRating.class);

        return userRating.getUserRatings().stream().map(rating -> {
            //Book book = restTemplate.getForObject("http://localhost:8082/books/"+rating.getBookId(), Book.class);

            Book book = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/books/"+rating.getBookId())
                    .retrieve()
                    .bodyToMono(Book.class)
                    .block();

            return new CatalogItem(book.getName(), "Interview", rating.getRating());
        })
                .collect(Collectors.toList());
    }

}
