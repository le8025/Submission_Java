package com.example.demo2.controller;

import com.example.demo2.model.User;
import com.example.demo2.request.MathRequest;
import com.example.demo2.request.UserRequest;
import com.example.demo2.response.GeneralResponse;
import com.example.demo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    //hashmap of users
    public static HashMap<Integer, User> userMap= new HashMap<>(){
        {
            put(1, new User("ann@gmail.com","annie123"));
            put(2, new User("bob@hotmail.com","bobbie456"));
            put(3, new User("char@outlook.com","charchar789"));
            put(4, new User("daisy@yahoo.com","flowers147"));
            put(5, new User("esther@apple.com","esther0258"));
        }
    };


    //Task 1 - linked to react-app-27 folder
    @PostMapping("validateReact")
    public ResponseEntity<?> validateLogin(@RequestBody UserRequest userRequest){
        GeneralResponse response = new GeneralResponse();
        try{
            userService.validateUser(userRequest.getEmail(), userRequest.getPassword());
            response.setMessage("Email matches password.");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //Task 2 - linked to react-app-27 folder
    @GetMapping("pathVar/{user_id}")
    @ResponseBody //to return a JSON object in ResponseEntity
    public ResponseEntity<?> getUserById(@PathVariable Integer user_id) {
        GeneralResponse response = new GeneralResponse();
        try{
            userService.findUser(user_id);
//            User userFound = userMap.get(user_id);
//            userFound.setId(user_id);
//            //create new ResponseEntity with User object to return, and HttpStatus.
//            ResponseEntity userFoundHere = new ResponseEntity<>(userFound, HttpStatus.OK);
            //how to return user object with message included?

            //returning an object (hashmap) + HttpStatus
            HashMap<String, String> userMock = new HashMap<>();
            userMock.put("id", Integer.toString(user_id));
            userMock.put("email", userMap.get(user_id).getEmail());
            userMock.put("password", userMap.get(user_id).getPassword());
            userMock.put("message", "User Found");

            ResponseEntity userFound = new ResponseEntity(userMock, HttpStatus.OK);
            return userFound;

        }catch (Exception e){
            response.setMessage(e.getMessage()); //possible to get message for the HttpStatus.ok version above?
            return ResponseEntity.badRequest().body(response);
        }
    }

    //Task 3
    @PostMapping("loginParam")
    public ResponseEntity<?> userLoginParam(@RequestParam String password,
                                            @RequestParam String email) {

        // extract list of users from hashmap
        ArrayList<User> userList = new ArrayList<User>();
        for (Integer id : userMap.keySet()){
            userList.add(userMap.get(id));
        }

        GeneralResponse response = new GeneralResponse();
        response.setMessage("No such email account exist.");

        for (User user : userList) {
            if (email.equals(user.getEmail())) { //check if email exists
                if (password.equals(user.getPassword())){ //check if email and password matches
                    response.setMessage("Login validated for: " + email );
                    return ResponseEntity.ok(response);
                }
                else { //Email exist but password not correct, return bad request
                    response.setMessage("Password does not match email. Please try again.");
                    return ResponseEntity.badRequest().body(response);
                }
            }
        }
        //Email does not exist, return bad request
        return ResponseEntity.badRequest().body(response);
    }







}