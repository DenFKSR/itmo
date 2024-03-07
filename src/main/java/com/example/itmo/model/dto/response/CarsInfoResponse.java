package com.example.itmo.model.dto.response;

import com.example.itmo.model.dto.request.CarsInfoRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Data
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarsInfoResponse extends CarsInfoRequest {
    Long id;
    UserInfoResponse user;
}
