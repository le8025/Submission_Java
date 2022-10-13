package com.example.demo2.repository;

import com.example.demo2.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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

    //to update user with token
    @Modifying
    @Transactional
    @Query("update UserModel set token = ?1 where id = ?2")
    Integer updateTokenForUserId(String token, Integer userId);
    //using Integer, because on SQL side the success/failure of update is 0/1

}
