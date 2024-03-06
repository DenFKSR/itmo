package com.example.itmo.model.db.repository;

import com.example.itmo.model.db.entity.Car;
import com.example.itmo.model.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarsRepo  extends JpaRepository<Car, Long> {
    Optional<Car> findByNumRegistration(String number);
    @Query("select c from Car c where c.user.firstName =:userFirstName")
    List<Car> findCars(@Param("userFirstName") String userFirstName);
}
