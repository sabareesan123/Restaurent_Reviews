package com.edstem.reataurent.resources;

import com.edstem.reataurent.modal.Restaurant;
import com.edstem.reataurent.modal.Review;
import com.edstem.reataurent.resources.exceptions.ResourceNotFoundException;
import com.edstem.reataurent.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
@Tag(name = "Restaurant", description = "API for managing restaurants and their reviews")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(summary = "Create a new restaurant", description = "Creates a new restaurant resource.")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return new ResponseEntity<>(restaurantService.createRestaurant(restaurant), HttpStatus.CREATED);
    }

    @Operation(summary = "Get restaurant by ID", description = "Retrieves a restaurant by its unique ID.")
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Restaurant> getRestaurantById(
            @Parameter(description = "ID of the restaurant", required = true) @PathVariable Long id) {
        return restaurantService.getRestaurantById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    @Operation(summary = "Submit a review for a restaurant", description = "Submits a new review for a specific restaurant.")
    @PostMapping("/{restaurantId}/reviews")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<Review> submitReview(
            @Parameter(description = "ID of the restaurant", required = true) @PathVariable Long restaurantId,
            @Valid @RequestBody Review review) {
        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId).orElseThrow(
                () -> new ResourceNotFoundException("Restaurant not found with id: " + restaurantId));
        restaurant.setAverageRating((restaurant.getAverageRating() + review.getRating() ) / 2 );
        review.setRestaurant(restaurant);
        return new ResponseEntity<>(restaurantService.submitReview(review), HttpStatus.CREATED);
    }

    @Operation(summary = "Get reviews for a restaurant", description = "Retrieves all reviews for a specific restaurant.")
    @GetMapping("/{restaurantId}/reviews")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<Page<Review>> getReviewsByRestaurantId(
            @Parameter(description = "ID of the restaurant", required = true) @PathVariable Long restaurantId,
            Pageable pageable) {
        return ResponseEntity.ok((Page<Review>) restaurantService.getReviewsByRestaurantId(restaurantId, pageable));
    }



    @Operation(summary = "Get average ratings for all restaurants", description = "Retrieves a map of restaurant IDs to their average ratings.")
    @GetMapping("/average-ratings")
    @PreAuthorize("hasRole('USER')")

    public ResponseEntity<Map<Long, Double>> getAverageRatings() {
        return ResponseEntity.ok(restaurantService.getAverageRatings());
    }

    @Operation(summary = "Get top 3 restaurants by cuisine", description = "Retrieves the top 3 restaurants for a given cuisine type.")
    @GetMapping("/top3/{cuisine}")

    public ResponseEntity<List<Restaurant>> getTop3ByCuisine(
            @Parameter(description = "Cuisine type", required = true) @PathVariable Restaurant.CuisineType cuisine) {
        return ResponseEntity.ok(restaurantService.getTop3ByCuisine(cuisine));
    }


}

