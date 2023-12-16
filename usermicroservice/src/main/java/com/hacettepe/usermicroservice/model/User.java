package com.hacettepe.usermicroservice.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @NonNull
    @Column(name = "username")
    private String username;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "password")
    private String password;

    @Column(name = "cv")
    private String cv; // as link or filename

    @Column(name = "github")
    private String github; // link

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_info")
    private PaymentInfo paymentInfo;
}
