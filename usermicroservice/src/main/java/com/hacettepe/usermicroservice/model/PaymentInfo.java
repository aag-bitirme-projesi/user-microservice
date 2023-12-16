package com.hacettepe.usermicroservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@Entity
@Table(name = "payment_infos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @NonNull
    @Column(name = "card_number")
    private int cardNumber;

    @NonNull
    @Column(name = "cvc")
    private int cvc;

    @NonNull
    @Column(name = "expiration_month")
    private int expirationMonth;

    @NonNull
    @Column(name = "expiration_year")
    private int expirationYear;

    @NonNull
    @Column(name = "owner")
    private String owner;

    @Column(name = "card_name")
    private String cardName;
}
