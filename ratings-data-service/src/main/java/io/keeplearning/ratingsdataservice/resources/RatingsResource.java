package io.keeplearning.ratingsdataservice.resources;

import io.keeplearning.ratingsdataservice.model.Rating;
import io.keeplearning.ratingsdataservice.model.UserRating;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingsResource {

    @GetMapping("/{bookId}")
    public Rating getRating(@PathVariable("bookId") String bookId) {
        return new Rating(bookId, 5);
    }

    @RequestMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        List<Rating> ratings = Arrays.asList(
                new Rating("Coding", 5),
                new Rating("Technical", 3)
        );
        UserRating userRating = new UserRating();
        userRating.setUserRatings(ratings);

        return userRating;
    }
}