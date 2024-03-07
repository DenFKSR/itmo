package com.example.itmo.service.impl;

import com.example.itmo.exceptions.CustomException;
import com.example.itmo.model.db.entity.Car;
import com.example.itmo.model.db.entity.User;
import com.example.itmo.model.db.repository.UserRepo;
import com.example.itmo.model.dto.request.UserInfoRequest;
import com.example.itmo.model.dto.response.UserInfoResponse;
import com.example.itmo.model.enums.UserStatus;
import com.example.itmo.service.impl.carsImpl.CarsService;
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
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepo userRepo;

    @Spy
    private ObjectMapper mapper;


    @Test
    public void createUser() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@tes.com");
        User user = new User();
        user.setId(1L);
        when(userRepo.save(any(User.class))).thenReturn(user);
        UserInfoResponse result = userService.createUser(request);
        assertEquals(Long.valueOf(1l), result.getId());

    }

    @Test(expected = CustomException.class)
    public void createUserInvalidEmail() {
        UserInfoRequest request = new UserInfoRequest();
        userService.createUser(request);
    }


    @Test(expected = CustomException.class)
    public void createUserExists() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@test.com");
        User user = new User();
        user.setId(1L);
        when(userRepo.findByEmail(anyString())).thenReturn(Optional.of(user));// когда будет обращение к базе вернуть юзера
        userService.createUser(request);
    }

    @Test
    public void getUser() {
    }

    @Test
    public void getUserDb() {
    }

    @Test
    public void updateUser() {
        UserInfoRequest request = new UserInfoRequest();
        request.setAge(30);
        User user = new User();
        user.setId(1L);
        user.setAge(35);
        user.setFirstName("Ivan");
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenReturn(user);
        UserInfoResponse result = userService.updateUser(user.getId(), request);
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getAge(), result.getAge());
    }

    @Test
    public void deleteUser() {
        User user = new User();
        user.setId(1L);
        when(userRepo.findById(user.getId())).thenReturn(Optional.of(user));
        userService.deleteUser(user.getId());
        verify(userRepo, times(1)).save(any(User.class));
        assertEquals(UserStatus.DELETED, user.getStatus());
    }

    @Test
    public void updateCarList() {

    }


    @Test
    public void getAllUsers() {
        Integer page = 0;
        Integer perPage = 10;
        String sort = "name";
        Sort.Direction order = Sort.Direction.ASC;
        Pageable request = PageRequest.of(page, perPage, Sort.by(order, sort));

        User user1 = new User();
        User user2 = new User();
        List<User> users = Arrays.asList(user1, user2);

        UserInfoResponse userInfoResponse1 = new UserInfoResponse();
        UserInfoResponse userInfoResponse2 = new UserInfoResponse();
        when(userRepo.findAll(request)).thenReturn(new PageImpl<>(users));
        when(mapper.convertValue(user1, UserInfoResponse.class)).thenReturn(userInfoResponse1);
        when(mapper.convertValue(user2, UserInfoResponse.class)).thenReturn(userInfoResponse2);
        Page<UserInfoResponse> result = userService.getAllUsers(page, perPage, sort, order);
        assertEquals(2, result.getContent().size());
        assertEquals(userInfoResponse1, result.getContent().get(0));
        assertEquals(userInfoResponse2, result.getContent().get(1));
    }
}