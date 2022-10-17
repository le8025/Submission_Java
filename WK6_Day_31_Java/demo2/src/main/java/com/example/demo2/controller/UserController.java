package com.example.demo2.controller;

import com.example.demo2.model.UserModel;
import com.example.demo2.repository.UserRepo;
import com.example.demo2.request.UpdateRequest;
import com.example.demo2.request.UserRequest;
import com.example.demo2.response.GeneralResponse;
import com.example.demo2.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:3000") // common config not working, putting here first
@RequestMapping("/user")
public class UserController {

    static String imagePath = "C:\\Users\\L1200\\Desktop\\Java-Training-GECO\\Training\\Java-Intellij-IDE\\WK6_Day_30_Java\\demo2\\image\\";

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

    //logout
    @PostMapping("logout/{user_id}")
    public ResponseEntity<?> logout(@PathVariable Integer user_id){
        GeneralResponse response = new GeneralResponse();
        try{
            userService.logout(user_id);
            response.setMessage("Logout successful");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //logout
    @PostMapping("logout")
    public ResponseEntity<?> logout2(@PathVariable Integer user_id){
        GeneralResponse response = new GeneralResponse();
        try{
            userService.logout(user_id);
            response.setMessage("Logout successful");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    //find all + requestheader
    //changed from GET to POST
    @GetMapping("listuser")
//    public ResponseEntity<?> listuser(@RequestHeader String token,
//                                      @RequestHeader Integer user_id){
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
    public ResponseEntity<?> findById(@PathVariable Integer userId){
        try{
            UserModel userModel = userService.findById(userId);
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
    @PostMapping("delete/{userId}")
    public ResponseEntity<?> deleteById(@PathVariable Integer userId){
        GeneralResponse response = new GeneralResponse();
        try{
            userService.deleteUser(userId);
            response.setMessage("User deleted");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.setMessage(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping(value = "imageupload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> imageUpload(@RequestParam String userId,
                                         @RequestParam MultipartFile file)
    throws Exception{
        System.out.println(file.getName());
        GeneralResponse response = new GeneralResponse();
        response.setMessage("File Upload Success:" + file.getOriginalFilename());
        //write the file in the server location
        FileOutputStream out=new FileOutputStream(imagePath +file.getOriginalFilename());
        out.write(file.getBytes());
        //call service method with userId and image name for update

        return ResponseEntity.ok(response);
    }

    @GetMapping(
            value="imageread/{fileName}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] imageRead(@PathVariable String fileName)
            throws Exception{
        FileInputStream input = new FileInputStream(imagePath+fileName);
        return IOUtils.toByteArray(input);
    }


    //Day 30 Assignment 2
    @PostMapping(value = "profileUpload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> imageUploadUser(@RequestParam Integer userId,
                                         @RequestParam MultipartFile file)
            throws Exception{
        GeneralResponse response = new GeneralResponse();
        String fileName = file.getOriginalFilename();
        String profilePic = imagePath + file.getOriginalFilename();

        FileOutputStream out=new FileOutputStream(profilePic);
        out.write(file.getBytes());
        userService.updateImage(fileName,userId);

        response.setMessage("File Upload Success: " + fileName + " | UserId: " + userId);
        return ResponseEntity.ok(response);
    }

    //Day 30 Assignment 3
    @GetMapping(
            value="profileLoad/{userId}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] imageReadUser(@PathVariable Integer userId)
            throws Exception{
        String fileName = userService.findById(userId).getProfilepic();
        FileInputStream input = new FileInputStream(imagePath+fileName);
        return IOUtils.toByteArray(input);
    }

}