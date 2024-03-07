package com.example.itmo.model.db.entity;

import com.example.itmo.model.enums.carsEnums.CarsStatus;
import com.example.itmo.model.enums.carsEnums.Engine;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cars")
@FieldDefaults(level = AccessLevel.PRIVATE)


public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "Brand")
    String nameBrand;
    String model;
    @Column(name = "number_registration")
    String numRegistration;
    @Column(name = "type_engine")
    @Enumerated(EnumType.STRING)
    Engine typeEngine;

    @Column(name = "engine_capacity")
    Double engineСapacity;

    @Column(name = "created_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    CarsStatus status;

    @ManyToOne
    @JsonBackReference(value = "driver_cars")
    User user;
}


