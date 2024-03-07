package com.example.itmo.service.impl.carsImpl;

import com.example.itmo.exceptions.CustomException;
import com.example.itmo.model.db.entity.Car;
import com.example.itmo.model.db.entity.User;
import com.example.itmo.model.db.repository.CarsRepo;
import com.example.itmo.model.db.repository.UserRepo;
import com.example.itmo.model.dto.request.CarsInfoRequest;
import com.example.itmo.model.dto.response.CarsInfoResponse;
import com.example.itmo.model.dto.response.UserInfoResponse;
import com.example.itmo.model.enums.carsEnums.CarsStatus;
import com.example.itmo.service.UserService;
import com.example.itmo.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CarsServiceImpl implements CarsService {
private final UserRepo userRepo;
    private final CarsRepo carsRepo;
    private final ObjectMapper mapper;
    private final UserService userService;

    public static final String ERR_MSG = "car not found";

    @Override
    public CarsInfoResponse createCar(CarsInfoRequest request) {
        carsRepo.findByNumRegistration(request.getNumRegistration())
                .ifPresent(car -> {
                    throw new CustomException("Car already exists", HttpStatus.CONFLICT);//исключение:добавление повтор. пользователя
                });
        Car car = mapper.convertValue(request, Car.class);
        car.setStatus(CarsStatus.CREATED);
        car.setCreatedAt(LocalDateTime.now());
        car = carsRepo.save(car);
        return mapper.convertValue(car, CarsInfoResponse.class);
    }

    private Car getCarDb(Long id) {
        return carsRepo.findById(id).orElseThrow(() -> new CustomException(ERR_MSG, HttpStatus.NOT_FOUND));
    }


    @Override
    public CarsInfoResponse getCar(Long id) {
        return mapper.convertValue(getCarDb(id), CarsInfoResponse.class);
    }

    @Override
    public CarsInfoResponse updateCar(Long id, CarsInfoRequest request) {
        Car car = getCarDb(id);
            car.setNameBrand(request.getNameBrand() == null ? car.getNameBrand() : request.getNameBrand());
            car.setModel(request.getModel() == null ? car.getModel() : request.getModel());
            car.setNumRegistration(request.getNumRegistration() == null ? car.getNumRegistration() : request.getNumRegistration());
            car.setTypeEngine(request.getTypeEngine() == null ? car.getTypeEngine() : request.getTypeEngine());
            car.setEngineСapacity((request.getEngineСapacity() == null) ? car.getEngineСapacity() : request.getEngineСapacity());
            car.setStatus(CarsStatus.UPDATED);
            car.setUpdatedAt(LocalDateTime.now());
            car = carsRepo.save(car);
        return mapper.convertValue(car, CarsInfoResponse.class);
    }

    @Override
    public void deleteCar(Long id) {
        Car car = getCarDb(id);
        carsRepo.findById(id).orElseThrow(() -> new CustomException(ERR_MSG, HttpStatus.NOT_FOUND));
            car.setStatus(CarsStatus.DELETED);
            car.setUpdatedAt(LocalDateTime.now());
            carsRepo.save(car);
    }

    @Override
    public Page<CarsInfoResponse> getAllCars(Integer page, Integer perPage, String sort, Sort.Direction order) {
        Pageable request = PaginationUtil.getPageRequest(page, perPage, sort, order);
        List<CarsInfoResponse> all = carsRepo.findAll(request)
                .getContent()
                .stream()
                .map(car-> mapper.convertValue(car, CarsInfoResponse.class))
                .collect(Collectors.toList());
        return new PageImpl<>(all);
    }

    @Override
    public CarsInfoResponse linkCarAndDriver(Long userId, Long carId) {
        Car car = getCarDb(carId);
        carsRepo.findById(carId).orElseThrow(() -> new CustomException(ERR_MSG, HttpStatus.NOT_FOUND));
        User user = userService.getUserDb(userId);
        userRepo.findById(userId).orElseThrow(() -> new CustomException("User not found", HttpStatus.NOT_FOUND));
        user.getCars().add(car);
        userService.updateCarList(user);
        car.setUser(user);
        car = carsRepo.save(car);
        UserInfoResponse userInfoResponse = mapper.convertValue(user, UserInfoResponse.class);
        CarsInfoResponse carsInfoResponse = mapper.convertValue(car, CarsInfoResponse.class);
        carsInfoResponse.setUser(userInfoResponse);
        return carsInfoResponse;
    }

    public List<CarsInfoResponse> getUserCar(Long id) {
        User user = userService.getUserDb(id);
        List<CarsInfoResponse> carsUser = user.getCars().stream()
                .map(car -> {
                    CarsInfoResponse carsInfoResponse = mapper.convertValue(car, CarsInfoResponse.class);
                    carsInfoResponse.setUser(mapper.convertValue(user, UserInfoResponse.class));
                    return carsInfoResponse;
                })
                .collect(Collectors.toList());

        return carsUser;
    }
}


