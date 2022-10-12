package com.example.demo2.controller;

import com.example.demo2.model.UserModel;
import com.example.demo2.repository.UserRepo;
import com.example.demo2.request.UpdateRequest;
import com.example.demo2.request.UserRequest;
import com.example.demo2.response.GeneralResponse;
import com.example.demo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    //create
    @PostMapping("register")
    public ResponseEntity<?>userReg(@RequestBody UserRequest userRequest){
        GeneralResponse response = new GeneralResponse();
        try{
            userService.createUser(userRequest);
            response.setMessage("Register success");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //login
    @PostMapping("login")
    public ResponseEntity<?>login(@RequestBody UserRequest userRequest){
        try{
            UserModel user = userService.validateUserLogin(userRequest.getEmail(), userRequest.getPassword());
            return ResponseEntity.ok(user);
        }catch (Exception e){
            GeneralResponse response = new GeneralResponse();

            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //find all
    @GetMapping("listuser")
    public ResponseEntity<?> listuser(){
        try{
            List<UserModel> list = userService.listUser();
            return ResponseEntity.ok(list);
        }catch (Exception e){
            GeneralResponse response = new GeneralResponse();
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //find one
    @GetMapping("listuser/{user_id}")
    public ResponseEntity<?> findById(@PathVariable Integer user_id){
        try{
            UserModel userModel = userService.findById(user_id);
            return ResponseEntity.ok(userModel);
        }catch (Exception e){
            GeneralResponse response = new GeneralResponse();
            response.setMessage("Not found");
            return ResponseEntity.badRequest().body(response);
        }
    }

    //update
    @PostMapping("update")
    public ResponseEntity<?> updateById(@RequestBody UpdateRequest updateRequest){
        GeneralResponse response = new GeneralResponse();
        try{
            userService.updateById(updateRequest);
            response.setMessage("Update success");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //delete
    @PostMapping("delete/{user_id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer user_id){
        GeneralResponse response = new GeneralResponse();
        try{
            userService.deleteUser(user_id);
            response.setMessage("User deleted");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

}