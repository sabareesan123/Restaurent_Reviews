package com.edstem.reataurent.modal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity

public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CuisineType cuisineType;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PriceRange priceRange;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @Column(nullable = true)
    private Double averageRating;

    public enum CuisineType {
        INDIAN, CHINESE, ITALIAN, MEXICAN
    }

    public enum PriceRange {
        LOW, MEDIUM, HIGH
    }
}

