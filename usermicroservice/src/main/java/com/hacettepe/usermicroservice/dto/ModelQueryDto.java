package com.hacettepe.usermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelQueryDto {
    private long id;
    private String model_link;
    private String model_name;
    private double price;
    private String description;
    private LocalDate created_at;
    private boolean availability;
}
