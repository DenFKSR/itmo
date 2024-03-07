package com.example.itmo.model.dto.response;

import com.example.itmo.model.db.entity.Car;
import com.example.itmo.model.dto.request.UserInfoRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoResponse extends UserInfoRequest {
    Long Id;
}
