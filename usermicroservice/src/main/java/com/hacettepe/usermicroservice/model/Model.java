package com.hacettepe.usermicroservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Entity
@Table(name = "models")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "model_link", length = 512)
    private String modelLink;

    @Column(name = "model_name")
    private String name;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    private LocalDate createdAt;

    @Column()
    private boolean availability = true;
}
