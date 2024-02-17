package com.example.itmo.service.impl;

import com.example.itmo.model.dto.request.UserInfoRequest;
import com.example.itmo.model.dto.response.UserInfoResponse;
import com.example.itmo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final ObjectMapper mapper;
    private  List<UserInfoResponse> users = new ArrayList<>();
    private long id=1;

    @Override
    public UserInfoResponse createUser(UserInfoRequest request) {
        UserInfoResponse user = mapper.convertValue(request, UserInfoResponse.class);
        user.setId(++id);
        users.add(user);
        return null;
    }

    @Override
    public UserInfoResponse getUser(Long id) {
        List<UserInfoResponse> all= this.users.stream()
                .filter(u -> u.getId().equals(id))
                .collect(Collectors.toList());
        UserInfoResponse user = null;
        if (CollectionUtils.isEmpty(all)){
            log.error(String.format("user with id: %s not found", id));
            return user;
        }
        return all.get(0);
    }

    @Override
    public UserInfoResponse updateUser(Long id, UserInfoRequest request) {
        UserInfoResponse user = getUser(id);
        if (Objects.isNull(user)){
            log.error("user is not delete");
            return null;
        }
        UserInfoResponse response = mapper.convertValue(request, UserInfoResponse.class);
        response.setId(user.getId());
        return response;
    }

    @Override
    public void deleteUser(Long id) {
        UserInfoResponse user = getUser(id);
        if (Objects.isNull(user)){
            log.error("user is not delete");
            return;
        }

        this.users.remove(user);
    }

    @Override
    public List<UserInfoResponse> getAllUsers() {
        return users;
    }
}
