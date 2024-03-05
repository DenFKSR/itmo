package com.example.itmo.contollers;

import com.example.itmo.model.dto.request.UserInfoRequest;
import com.example.itmo.model.dto.response.CarsInfoResponse;
import com.example.itmo.model.dto.response.UserInfoResponse;
import com.example.itmo.service.UserService;
import com.example.itmo.service.impl.carsImpl.CarsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarsController {
    private final CarsService carsService;

    @PostMapping
    public CarsInfoResponse createCar(@RequestBody CarsInfoResponse request) {
        return carsService.createCar(request);
    }

    @GetMapping("/{id}")
    public CarsInfoResponse getCar(@PathVariable Long id) {
        return carsService.getCar(id);
    }

    @PutMapping("/{id}")
    public CarsInfoResponse updateCar(@PathVariable Long id, @RequestBody CarsInfoResponse request) {
        return carsService.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carsService.deleteCar(id);
    }

    @GetMapping("/all")
    public Page<CarsInfoResponse> getAllCars(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer perPage,
                                             @RequestParam(defaultValue = "nameBrand") String sort,
                                             @RequestParam(defaultValue = "ASC") Sort.Direction order) {
        return carsService.getAllCars(page, perPage, sort,order);
    }

    @PostMapping("/linkCarAndDriver/{userId}/{carId}")
    public CarsInfoResponse linkCarAndDriver(@PathVariable Long userId, @PathVariable Long carId) {
        return carsService.linkCarAndDriver(userId, carId);
    }
}
