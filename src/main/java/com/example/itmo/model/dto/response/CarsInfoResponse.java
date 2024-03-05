package com.example.itmo.model.dto.response;

import com.example.itmo.model.dto.request.CarsInfoRequest;
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
public class CarsInfoResponse extends CarsInfoRequest {
    Long id;
    UserInfoResponse user;
}
