package io.keeplearning.bookcatalogservice.resources;

import io.keeplearning.bookcatalogservice.model.Book;
import io.keeplearning.bookcatalogservice.model.CatalogItem;
import io.keeplearning.bookcatalogservice.model.UserRating;
import io.keeplearning.bookcatalogservice.service.BookInfoService;
import io.keeplearning.bookcatalogservice.service.RatingInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class BookCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    BookInfoService bookInfoService;

    @Autowired
    RatingInfoService ratingInfoService;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
        UserRating userRating = ratingInfoService.getUserRating(userId);
        return userRating.getUserRatings().stream()
                .map(rating -> {
                    Book book = bookInfoService.getBookInfo(rating);
                    return new CatalogItem(book.getName(), book.getDescription(), rating.getRating());
                })
                .collect(Collectors.toList());
    }
}

/*Book book = webClientBuilder.build()
                    .get()
                    .uri("http://book-info-service/books/"+rating.getBookId())
                    .retrieve()
                    .bodyToMono(Book.class)
                    .block();*/