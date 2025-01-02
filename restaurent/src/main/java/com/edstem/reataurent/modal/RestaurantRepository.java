package com.edstem.reataurent.modal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r.id, AVG(rev.rating) as avg_rating " +
            "FROM Restaurant r " +
            "LEFT JOIN r.reviews rev " +
            "GROUP BY r.id")
    Map<Long, Double> findAverageRatings();
//    @Query("SELECT r, AVG(rev.rating) as averageRating " +
//            "FROM Restaurant r " +
//            "LEFT JOIN r.reviews rev " +
//            "GROUP BY r")
//    Map<Restaurant, Double> findAverageRatings();

    List<Restaurant> findTop3ByCuisineTypeOrderByAverageRatingDesc(Restaurant.CuisineType cuisineType);
}

