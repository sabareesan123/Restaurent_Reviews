package com.edstem.reataurent;


import com.edstem.reataurent.modal.Restaurant;
import com.edstem.reataurent.modal.Review;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RestaurantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createRestaurant_shouldReturnCreated() throws Exception {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setCuisineType(Restaurant.CuisineType.INDIAN);
        restaurant.setAddress("Test Address");
        restaurant.setPriceRange(Restaurant.PriceRange.MEDIUM);

        mockMvc.perform(post("/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andExpect(status().isCreated());
    }

    @Test
    public void getRestaurantById_shouldReturnOk() throws Exception {

        mockMvc.perform(get("/restaurants/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getRestaurantById_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/restaurants/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void submitReview_shouldReturnCreated() throws Exception {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L); // Assuming restaurant with ID 1 exists

        Review review = new Review();
        review.setRating(4);
        review.setComment("Test Review");


        mockMvc.perform(post("/restaurants/1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isCreated());
    }

    @Test
    public void submitReview_shouldReturnNotFound() throws Exception {
        Review review = new Review();
        review.setRating(4);
        review.setComment("Test Review");

        mockMvc.perform(post("/restaurants/999/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getReviewsByRestaurantId_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/restaurants/1/reviews"))
                .andExpect(status().isOk());
    }

}