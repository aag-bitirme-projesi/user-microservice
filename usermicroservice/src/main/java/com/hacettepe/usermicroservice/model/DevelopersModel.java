package com.hacettepe.usermicroservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "developers_models")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DevelopersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "developer")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model")
    private Model model;
}
