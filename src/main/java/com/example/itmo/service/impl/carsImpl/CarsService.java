package com.example.itmo.service.impl.carsImpl;

import com.example.itmo.model.dto.request.CarsInfoRequest;
import com.example.itmo.model.dto.response.CarsInfoResponse;
import com.example.itmo.model.dto.response.UserInfoResponse;

import java.util.List;

public interface CarsService {
    CarsInfoResponse createCar(CarsInfoRequest request);
    CarsInfoResponse getCar(Long id);
    CarsInfoResponse updateCar (Long id, CarsInfoRequest request);
    void deleteCar(Long id);
    List<CarsInfoResponse> getAllCars();
}
