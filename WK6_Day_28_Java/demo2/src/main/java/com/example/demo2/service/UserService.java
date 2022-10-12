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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    //login
    public UserModel validateUserLogin(String email, String password) throws Exception{
        UserModel userModel = userRepo.getUserByEmailAndPassword(email,password).orElseThrow(() -> new Exception("User and email does not match"));
        return userModel;
    }

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

}
