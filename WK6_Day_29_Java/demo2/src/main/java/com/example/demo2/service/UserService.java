package com.example.demo2.service;

import com.example.demo2.controller.UserController;
import com.example.demo2.model.UserModel;
import com.example.demo2.repository.UserRepo;
import com.example.demo2.request.UpdateRequest;
import com.example.demo2.request.UserRequest;
import com.example.demo2.response.GeneralResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.expression.ExpressionException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.w3c.dom.ls.LSException;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    //create
    public boolean createUser(UserRequest userRequest) throws Exception{
        try{
            Optional<UserModel> user = userRepo.getUserByEmail(userRequest.getEmail());
            if(user.isPresent()){ //if email already existed
                throw new Exception("Email already registered"); //will throw exception and go into catch
            }
            //so even if we don't use else, it will move here.
            UserModel userModel = new UserModel();
            userModel.setEmail(userRequest.getEmail());
            userModel.setMobile(userRequest.getMobile());
            userModel.setName(userRequest.getName());
            userModel.setAddress(userRequest.getAddress());
            userModel.setPassword(userRequest.getPassword());
            userRepo.save(userModel);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    //update
    public boolean updateById(UpdateRequest updateRequest) throws Exception {
        try {
            //UserModel userModel = userRepo.findById(userRequest.getId()).orElseThrow(() -> new Exception("no user found"));

            Optional<UserModel> userModel = userRepo.findById(updateRequest.getId());

            if (userModel.isPresent()) {
                //get details of the user
                UserModel userModelTmp = userModel.get();

                if(!updateRequest.getName().equals("")){
                    userModelTmp.setName(updateRequest.getName());
                }
                if(!updateRequest.getEmail().equals("")){
                    userModelTmp.setEmail(updateRequest.getEmail());
                }
                if(!updateRequest.getMobile().equals("")){
                    userModelTmp.setMobile(updateRequest.getMobile());
                }
                if(!updateRequest.getAddress().equals("")){
                    userModelTmp.setAddress(updateRequest.getAddress());
                }

                userRepo.save(userModelTmp); //update data into table
                return true;
            } else {
                throw new Exception("Update failed as user is not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //delete
    public boolean deleteUser(Integer user_id) throws Exception{
        UserModel userModelTmp = userRepo.findById(user_id).orElseThrow(() -> new Exception("no user found"));
        userRepo.delete(userModelTmp);
        return true;
    }

    //tokencheck - 01:57


    //find all
    public List<UserModel> listUser() throws Exception{
        return userRepo.findAll();

    }

    //find
    public UserModel findById(Integer user_id) throws Exception{
        Optional <UserModel> userModel = userRepo.findById(user_id);
        if(userModel.isPresent()){
            return userModel.get();
        }else{
            throw new Exception("User not found");
        }
    }

    //login with token - task 1
    public UserModel validateUserLogin(String email, String password) throws Exception{
        UserModel userModel = userRepo.getUserByEmailAndPassword(email,password).orElseThrow(() -> new Exception("User and email does not match"));
        String token = getToken(userModel.getEmail());
//        userRepo.updateTokenForUserId(token, userModel.getId()); //create token here
        updateToken(token,userModel.getId()); //update token with value for successful login in the database
        userModel.setToken(token); //sharing with react front app, in the model
//        userRepo.save(userModel);
        return userModel;
    }

    //logout - task 3
    public boolean logout(int user_id) throws Exception{
        updateToken("", user_id);
        return true;
    }

    //update token method
    private void updateToken(String token, int user_id){
        userRepo.updateTokenForUserId(token, user_id);
    }

    public boolean validateToken(String token, Integer user_id) throws Exception{
        UserModel userModel = findById(user_id);
        if(userModel.getToken().equals(token)){
            return true;
        }else{
            throw new Exception(("Token Mismatch"));
        }
    }

    //manually-created token
    private String getToken(String email){
        String emailEncoded = Base64.getEncoder().encode(email.getBytes()).toString();
        String token = emailEncoded + System.currentTimeMillis(); //randomly generate the token base on time
        return token;
//        Random r = new Random(System.currentTimeMillis());
//        return 10000 + r.nextInt(20000);
    }




}
