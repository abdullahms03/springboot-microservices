package io.keeplearning.bookcatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.keeplearning.bookcatalogservice.model.Rating;
import io.keeplearning.bookcatalogservice.model.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class RatingInfoService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRating",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50")
            })
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://ratings-data-service/ratings/users/" + userId, UserRating.class);
    }

    public UserRating getFallbackUserRating(String userId) {
        return new UserRating(
                Collections.singletonList(
                        new Rating("0", 0)
                )
        );
    }

// bulkhead pattern
    /*@HystrixCommand(fallbackMethod = "getFallbackUserRating",
            threadPoolKey = "bookInfoPool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })*/

}
