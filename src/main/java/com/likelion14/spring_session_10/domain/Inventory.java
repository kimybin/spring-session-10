package com.likelion14.spring_session_10.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FoodType foodType;

    @NotNull
    private java.time.LocalDate expirationDate;

    @NotNull
    private Integer quantity;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Unit unit;

    private String nutritionImageURL;

    private Boolean isSnack;

}
