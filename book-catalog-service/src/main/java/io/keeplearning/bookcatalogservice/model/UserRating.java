package io.keeplearning.bookcatalogservice.model;

import java.util.List;

public class UserRating {

    public List<Rating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<Rating> userRatings) {
        this.userRatings = userRatings;
    }

    private List<Rating> userRatings;

    }
