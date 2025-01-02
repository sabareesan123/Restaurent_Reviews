package com.edstem.reataurent.service;

import com.edstem.reataurent.modal.Restaurant;
import com.edstem.reataurent.modal.RestaurantRepository;
import com.edstem.reataurent.modal.Review;
import com.edstem.reataurent.modal.ReviewRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, ReviewRepository reviewRepository) {
        this.restaurantRepository = restaurantRepository;
        this.reviewRepository = reviewRepository;
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);//.orElse(null)
    }

    public Review submitReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsByRestaurantId(Long restaurantId, Pageable pageable) {
        return reviewRepository.findByRestaurantId(restaurantId, pageable);
    }

    public Map<Long, Double> getAverageRatings() {
        return restaurantRepository.findAverageRatings();
    }

    public List<Restaurant> getTop3ByCuisine(Restaurant.CuisineType cuisineType) {
        return restaurantRepository.findTop3ByCuisineTypeOrderByAverageRatingDesc(cuisineType);
    }
}