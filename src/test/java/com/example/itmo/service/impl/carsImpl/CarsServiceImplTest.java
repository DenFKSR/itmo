package com.example.itmo.service.impl.carsImpl;

import com.example.itmo.exceptions.CustomException;
import com.example.itmo.model.db.entity.Car;
import com.example.itmo.model.db.entity.User;
import com.example.itmo.model.db.repository.CarsRepo;
import com.example.itmo.model.db.repository.UserRepo;
import com.example.itmo.model.dto.request.CarsInfoRequest;
import com.example.itmo.model.dto.request.UserInfoRequest;
import com.example.itmo.model.dto.response.CarsInfoResponse;
import com.example.itmo.model.dto.response.UserInfoResponse;
import com.example.itmo.model.enums.UserStatus;
import com.example.itmo.model.enums.carsEnums.CarsStatus;
import com.example.itmo.model.enums.carsEnums.Engine;
import com.example.itmo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarsServiceImplTest {

    @InjectMocks
    private CarsServiceImpl carsService;
    @Mock
    private CarsRepo carsRepo;
    @Mock
    private UserRepo userRepo;
    @Mock
    private UserService userService;

    @Spy
    private ObjectMapper mapper;

    @Test
    public void createCar() {
        CarsInfoRequest request = new CarsInfoRequest();
        Car car = new Car();
        car.setId(1l);
        when(carsRepo.save(any(Car.class))).thenReturn(car);
        CarsInfoResponse result = carsService.createCar(request);
        assertEquals(Long.valueOf(1l), result.getId());
    }

    @Test(expected = CustomException.class)
    public void createUserExists() {
        CarsInfoRequest request = new CarsInfoRequest();
        request.setNumRegistration("o555oo");
        Car car = new Car();
        car.setId(1l);
        when(carsRepo.findByNumRegistration(anyString())).thenReturn(Optional.of(car));// когда будет обращение к базе вернуть юзера
        carsService.createCar(request);
    }


    @Test
    public void getCar() {
    }

    @Test
    public void updateCar() {
        CarsInfoRequest request = new CarsInfoRequest();
        request.setNameBrand("Mercedes");
        Car car = new Car();
        car.setId(1l);
        car.setNameBrand("BMW");
        car.setModel("x3");
        when(carsRepo.findById(car.getId())).thenReturn(Optional.of(car));
        when(carsRepo.save(any(Car.class))).thenReturn(car);
        CarsInfoResponse result = carsService.updateCar(car.getId(), request);
        assertEquals(car.getNameBrand(), result.getNameBrand());
        assertEquals(car.getModel(), result.getModel());

    }

    @Test
    public void deleteCar() {
        Car car = new Car();
        car.setId(1l);
        when(carsRepo.findById(car.getId())).thenReturn(Optional.of(car));
        carsService.deleteCar(car.getId());
        verify(carsRepo, times(1)).save(any(Car.class));
        assertEquals(CarsStatus.DELETED, car.getStatus());
    }

    @Test
    public void getAllCars() {
        Integer page = 0;
        Integer perPage = 10;
        String sort = "name";
        Sort.Direction order = Sort.Direction.ASC;
        Pageable request = PageRequest.of(page, perPage, Sort.by(order, sort));

        Car car1 = new Car();
        Car car2 = new Car();
        List<Car> cars = Arrays.asList(car1, car2);

        CarsInfoResponse carsInfoResponse1 = new CarsInfoResponse();
        CarsInfoResponse carsInfoResponse2 = new CarsInfoResponse();
        when(carsRepo.findAll(request)).thenReturn(new PageImpl<>(cars));
        Page<CarsInfoResponse> result = carsService.getAllCars(page, perPage, sort, order);
        assertEquals(2, result.getContent().size());
        assertEquals(carsInfoResponse1, result.getContent().get(0));
        assertEquals(carsInfoResponse2, result.getContent().get(1));
    }

    @Test
    public void linkCarAndDriver() {
        Long userId = 1L;
        Long carId = 2L;
        Car car = new Car();
        car.setId(carId);
        User user = new User();
        user.setId(userId);
        user.setCars(new ArrayList<>());
        CarsInfoResponse carsInfoResponse = new CarsInfoResponse();
        carsInfoResponse.setId(carId);
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setId(userId);
        carsInfoResponse.setUser(userInfoResponse);

        when(carsRepo.findById(carId)).thenReturn(Optional.of(car));
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(userService.getUserDb(userId)).thenReturn(user);
        when(userService.updateCarList(user)).thenReturn(user);
        when(carsRepo.save(car)).thenReturn(car);
        CarsInfoResponse carsInfoResponse2 = carsService.linkCarAndDriver(userId, carId);
        assertEquals(carsInfoResponse, carsInfoResponse2);
        verify(userService, times(1)).updateCarList(user);
        verify(carsRepo, times(1)).save(car);
    }
    @Test
    public void getUserCar() {
            Long userId = 1L;
            Car car1 = new Car();
            car1.setId(1L);
            Car car2 = new Car();
            car2.setId(2L);
            User user = new User();
            user.setId(userId);
            user.setCars(Arrays.asList(car1, car2));
            when(userService.getUserDb(userId)).thenReturn(user);
            List<CarsInfoResponse> actualCarsInfoResponses = carsService.getUserCar(userId);
            assertEquals(2, actualCarsInfoResponses.size());
            assertEquals(Long.valueOf(1L), actualCarsInfoResponses.get(0).getId());
            assertEquals(Long.valueOf(2L), actualCarsInfoResponses.get(1).getId());
            verify(userService, times(1)).getUserDb(userId);

    }


}