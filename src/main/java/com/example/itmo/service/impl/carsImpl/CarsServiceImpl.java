package com.example.itmo.service.impl.carsImpl;

import com.example.itmo.model.dto.request.CarsInfoRequest;
import com.example.itmo.model.dto.response.CarsInfoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarsServiceImpl implements CarsService{
    private final ObjectMapper mapper;
    private  List<CarsInfoResponse> cars = new ArrayList<>();
    private long id=1;


    @Override
    public CarsInfoResponse createCar(CarsInfoRequest request) {
        CarsInfoResponse car = mapper.convertValue(request, CarsInfoResponse.class);
        car.setId(++id);
        cars.add(car);
        return null;
    }

    @Override
    public CarsInfoResponse getCar(Long id) {
        List<CarsInfoResponse> all= this.cars.stream()
                .filter(u -> u.getId().equals(id))
                .collect(Collectors.toList());
        CarsInfoResponse car = null;
        if (CollectionUtils.isEmpty(all)){
            log.error(String.format("user with id: %s not found", id));
            return car;
        }
        return all.get(0);
    }

    @Override
    public CarsInfoResponse updateCar(Long id, CarsInfoRequest request) {
        CarsInfoResponse car = getCar(id);
        if (Objects.isNull(car)){
            log.error("user is not delete");
            return null;
        }
        CarsInfoResponse response = mapper.convertValue(request, CarsInfoResponse.class);
        response.setId(car.getId());
        return response;

    }

    @Override
    public void deleteCar(Long id) {
        CarsInfoResponse car = getCar(id);
        if (Objects.isNull(car)){
            log.error("user is not delete");
            return;
        }

        this.cars.remove(car);
    }

    @Override
    public List<CarsInfoResponse> getAllCars() {
        return cars;
    }
    //CarsInfoResponse


}
