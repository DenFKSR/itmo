package com.example.itmo.service.impl.carsImpl;

import com.example.itmo.model.db.entity.Car;
import com.example.itmo.model.dto.request.CarsInfoRequest;
import com.example.itmo.model.dto.response.CarsInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CarsService {
    CarsInfoResponse createCar(CarsInfoRequest request);
    CarsInfoResponse getCar(Long id);
    CarsInfoResponse updateCar (Long id, CarsInfoRequest request);
    void deleteCar(Long id);
    Page<CarsInfoResponse> getAllCars(Integer page, Integer perPage, String sort, Sort.Direction order);

    CarsInfoResponse linkCarAndDriver(Long userId, Long carId);
    List<CarsInfoResponse> getUserCar(Long id);
}
