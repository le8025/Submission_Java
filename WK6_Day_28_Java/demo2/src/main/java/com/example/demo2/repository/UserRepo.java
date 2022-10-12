package com.example.demo2.repository;

import com.example.demo2.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserModel, Integer> {

    @Query("Select u from UserModel u where u.email=?1 and password=?2")
    Optional<UserModel> getUserByEmailAndPassword(String email, String password);

    @Query("select u from UserModel u where u.email=?1")
    Optional<UserModel> getUserByEmail(String email);

    @Query("select count(user) from UserModel user")
    Integer getUserCount();

    @Query("select user from UserModel user")
    List<UserModel> getAllUserQuery();



}
