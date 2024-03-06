package com.example.itmo.model.dto.request;

import com.example.itmo.model.enums.Gender;
import com.example.itmo.model.enums.carsEnums.Engine;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

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
    @NotEmpty(message = "Registration number must be set")
    String numRegistration;
    Engine typeEngine;
    Double engineСapacity;

}
