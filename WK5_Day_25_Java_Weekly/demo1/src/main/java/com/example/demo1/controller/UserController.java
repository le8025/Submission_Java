package com.example.demo1.controller;

import com.example.demo1.model.User;
import com.example.demo1.request.MathRequest;
import com.example.demo1.request.UserRequest;
import com.example.demo1.response.GeneralResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
public class UserController {

    //note: learning to add to static arraylist using anonymous function
    public static ArrayList<User> userList = new ArrayList<>() {
        {
            add(new User("ann", "annie123", "ann@gmail.com", "Street Z26"));
            add(new User("bob", "bobbie456", "bob@hotmail.com", "Street Y25"));
            add(new User("charles", "charchar789", "char@outlook.com", "Street X24"));
            add(new User("daisy", "flowers147", "daisy@yahoo.com", "Street W23"));
            add(new User("esther", "esther0258", "esther@apple.com", "Street V22"));
            add(new User("fanny", "f@nni@369", "fanny@zoho.com", "Street U21"));
            add(new User("galvin", "g@l^iN", "galvin@gmail.com", "Street T20"));
            add(new User("henrietta", "h3n753", "henni@hotmail.com", "Street S19"));
            add(new User("ian", "1@n951", "ian.tay@outlook.com", "Street R18"));
            add(new User("joshua", "j0shua7410", "josh_depp@yahoo.com", "Street Q17"));
        }
    };

    //perform operations based on two numbers and operation type
    @PostMapping("math")
    public ResponseEntity<?> math(@RequestBody MathRequest mathRequest) {
        GeneralResponse response = new GeneralResponse();
        int result = 0;

        switch (mathRequest.getType()) {
            case "add":
                result = mathRequest.getNum1() + mathRequest.getNum2();
                break;
            case "sub":
                result = mathRequest.getNum1() - mathRequest.getNum2();
                break;
            case "mul":
                result = mathRequest.getNum1() * mathRequest.getNum2();
                break;
            case "div":
                result = mathRequest.getNum1() / mathRequest.getNum2();
                break;
            default:
                response.setMessage("Please type a valid operation.");
                return ResponseEntity.badRequest().body(response);
        }

        response.setMessage("Operation: " + mathRequest.getType() + " | Result: " + result);
        return ResponseEntity.ok(response);
    }

    //iterate arraylist and verify login details
    @PostMapping("/validate")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {

        GeneralResponse response = new GeneralResponse();
        response.setMessage("The account (" + userRequest.getEmail() + ") does not exist.");

        for (User user : userList) {
            if (userRequest.getEmail().equals(user.getEmail())) { //check if email exists
                if (userRequest.getPassword().equals(user.getPassword())){ //check if email and password matches
                    response.setMessage("Login validated for " + userRequest.getEmail() +
                            ". Welcome back, " + user.getUsername() + "!");
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

    //return all users in arraylist
    @GetMapping("/userlist")
    public ArrayList<User> getUserList() {
        return userList;
    }

}