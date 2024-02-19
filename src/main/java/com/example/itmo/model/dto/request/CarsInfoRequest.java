package com.example.itmo.model.dto.request;

import com.example.itmo.model.enums.Gender;
import com.example.itmo.model.enums.carsEnums.Engine;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarsInfoRequest {
    String nameBrand;
    String model;
    String numRegistration;
    Engine typeEngine;
    double engineСapacity;

}
