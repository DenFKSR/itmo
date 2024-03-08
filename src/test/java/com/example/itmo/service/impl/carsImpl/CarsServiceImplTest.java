package com.example.itmo.service.impl.carsImpl;

import com.example.itmo.model.db.repository.CarsRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CarsServiceImplTest {

    @InjectMocks
    private CarsServiceImpl carsService;
    @Mock
    private CarsRepo carsRepo;

    @Spy
    private ObjectMapper mapper;

    @Test
    public void createCar() {
    }

    @Test
    public void getCar() {
    }

    @Test
    public void updateCar() {
    }

    @Test
    public void deleteCar() {
    }

    @Test
    public void getAllCars() {
    }

    @Test
    public void linkCarAndDriver() {
    }
}