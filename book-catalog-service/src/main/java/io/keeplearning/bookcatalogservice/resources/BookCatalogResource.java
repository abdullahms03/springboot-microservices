package io.keeplearning.bookcatalogservice.resources;

import io.keeplearning.bookcatalogservice.model.Book;
import io.keeplearning.bookcatalogservice.model.CatalogItem;
import io.keeplearning.bookcatalogservice.model.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class BookCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        List<Rating> ratings = Arrays.asList(
                new Rating("1234", 5),
                new Rating("4567", 3)
        );

        return ratings.stream().map(rating -> {
            Book book = restTemplate.getForObject("http://localhost:8082/books/"+rating.getBookId(), Book.class);
            return new CatalogItem(book.getName(), "Interview", rating.getRating());
        })
                .collect(Collectors.toList());
    }

}
