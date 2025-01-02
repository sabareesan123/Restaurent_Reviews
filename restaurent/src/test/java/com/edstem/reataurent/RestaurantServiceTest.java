package com.edstem.reataurent;


import com.edstem.reataurent.modal.Restaurant;
import com.edstem.reataurent.modal.RestaurantRepository;
import com.edstem.reataurent.modal.Review;
import com.edstem.reataurent.modal.ReviewRepository;
import com.edstem.reataurent.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RestaurantServiceTest {

    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAverageRatings() {

        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId(1L);
        restaurant1.setReviews(createReviews(restaurant1, 4, 5, 3));

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId(2L);
        restaurant2.setReviews(createReviews(restaurant2, 2, 3, 4));


        Map<Long, Double> expectedAverages = new HashMap<>();
        expectedAverages.put(1L, 4.0); // Average of 4, 5, 3 is 4
        expectedAverages.put(2L, 3.0); // Average of 2, 3, 4 is 3

        when(restaurantRepository.findAverageRatings()).thenReturn(expectedAverages);


        Map<Long, Double> actualAverages = restaurantService.getAverageRatings();


        assertEquals(expectedAverages, actualAverages);
    }

    @Test
    public void testGetTop3ByCuisine() {

        Restaurant indianRestaurant1 = createRestaurant("Indian Restaurant 1", Restaurant.CuisineType.INDIAN, 4.5);
        Restaurant indianRestaurant2 = createRestaurant("Indian Restaurant 2", Restaurant.CuisineType.INDIAN, 4.2);
        Restaurant indianRestaurant3 = createRestaurant("Indian Restaurant 3", Restaurant.CuisineType.INDIAN, 3.8);
        Restaurant chineseRestaurant1 = createRestaurant("Chinese Restaurant 1", Restaurant.CuisineType.CHINESE, 4.8);
        Restaurant chineseRestaurant2 = createRestaurant("Chinese Restaurant 2", Restaurant.CuisineType.CHINESE, 4.1);


        when(restaurantRepository.findTop3ByCuisineTypeOrderByAverageRatingDesc(Restaurant.CuisineType.CHINESE))
                .thenReturn(List.of(chineseRestaurant1, chineseRestaurant2));


        List<Restaurant> top3Chinese = restaurantService.getTop3ByCuisine(Restaurant.CuisineType.CHINESE);


        assertEquals(2, top3Chinese.size());
        assertEquals(chineseRestaurant1, top3Chinese.get(0));
        assertEquals(chineseRestaurant2, top3Chinese.get(1));
    }


    private List<Review> createReviews(Restaurant restaurant, int... ratings) {
        List<Review> reviews = new ArrayList<>();
        for (int rating : ratings) {
            Review review = new Review();
            review.setRestaurant(restaurant);
            review.setRating(rating);
            review.setComment("Test Review");
            review.setVisitDate(LocalDate.now());
            review.setStatus(Review.ReviewStatus.APPROVED);
            reviews.add(review);
        }
        return reviews;
    }


    private Restaurant createRestaurant(String name, Restaurant.CuisineType cuisineType, double averageRating) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setCuisineType(cuisineType);
        restaurant.setPriceRange(Restaurant.PriceRange.MEDIUM); // Set a default price range

        return restaurant;
    }
}