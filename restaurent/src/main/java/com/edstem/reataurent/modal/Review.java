package com.edstem.reataurent.modal;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private LocalDate visitDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReviewStatus status;



    public enum ReviewStatus {
        PENDING, APPROVED
    }
}