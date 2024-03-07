package com.example.itmo.contollers;

import com.example.itmo.model.db.entity.Car;
import com.example.itmo.model.dto.response.CarsInfoResponse;
import com.example.itmo.service.UserService;
import com.example.itmo.service.impl.carsImpl.CarsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarsController {
    private final CarsService carsService;
     private final UserService userService;

    @PostMapping
    @Operation(summary = "Создание автомобиля")
    public CarsInfoResponse createCar(@RequestBody @Valid CarsInfoResponse request) {
        return carsService.createCar(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Выбор автомобиля")
    public CarsInfoResponse getCar(@PathVariable Long id) {
        return carsService.getCar(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактирование данных автомобиля")
    public CarsInfoResponse updateCar(@PathVariable Long id, @RequestBody @Valid CarsInfoResponse request) {
        return carsService.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Редактирование данных автомобиля")
    public void deleteCar(@PathVariable Long id) {
        carsService.deleteCar(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получение всех данных автомобилей")
    public Page<CarsInfoResponse> getAllCars(@RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer perPage,
                                             @RequestParam(defaultValue = "nameBrand") String sort,
                                             @RequestParam(defaultValue = "ASC") Sort.Direction order) {
        return carsService.getAllCars(page, perPage, sort,order);
    }

    @PostMapping("/linkCarAndDriver/{userId}/{carId}")
    @Operation(summary = "Присвоение автомобиля пользователю")
    public CarsInfoResponse linkCarAndDriver(@PathVariable Long userId, @PathVariable Long carId) {
        return carsService.linkCarAndDriver(userId, carId);
    }

    @GetMapping("/find_cars/{id}")
    @Operation(summary = "Получение автомобиля пользователя")
    public List<CarsInfoResponse> getUserCar (@PathVariable Long id){
        return carsService.getUserCar(id) ;
    }



}
