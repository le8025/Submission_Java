package com.example.demo2.service;

import com.example.demo2.controller.UserController;
import com.example.demo2.model.User;
import com.example.demo2.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    //For Task 1
    public boolean validateUser(String email, String password) throws Exception{

        //extract list of users from userMap - how to extract from static map to get static arraylist to use in controller?
        ArrayList<User> userListVal = new ArrayList<>();
        for (Integer user_id : UserController.userMap.keySet()) {
            userListVal.add(UserController.userMap.get(user_id));
        }

        for (User userVal: userListVal) {
            if (email.equals(userVal.getEmail()) && password.equals(userVal.getPassword())) {
                return true;
            }
        }
        throw new Exception("Email and password does not match");
    }

    //For Task 2
    public boolean findUser(Integer id) throws Exception{
        //call static map from usercontroller class
        for (Integer user_id : UserController.userMap.keySet()) {
            if (id == user_id) {
                return true;
            }
        }
        throw new Exception("User does not exist"); //is this syntax correct though
    }
}
