package com.example.demo2.service;

import com.example.demo2.configuration.CustomException;
import com.example.demo2.controller.UserController;
import com.example.demo2.model.UserModel;
import com.example.demo2.repository.UserRepo;
import com.example.demo2.request.UpdateRequest;
import com.example.demo2.request.UserRequest;
import com.example.demo2.response.GeneralResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.env.Environment;
import org.springframework.core.env.EnvironmentCapable;
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
    Environment environment;

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
    public boolean deleteUser(Integer userId) throws Exception{
        UserModel userModelTmp = userRepo.findById(userId).orElseThrow(() -> new Exception("no user found"));
        userRepo.delete(userModelTmp);
        return true;
    }

    //find all
    public List<UserModel> listUser() throws Exception{
        return userRepo.findAll();

    }

    //find
    public UserModel findById(Integer userId) throws Exception{
        Optional <UserModel> userModel = userRepo.findById(userId);
        if(userModel.isPresent()){
            return userModel.get();
        }else{
            throw new Exception("User not found");
        }
    }

    //login with token - task 1
    public UserModel validateUserLogin(String email, String password) throws Exception{
        UserModel userModel = userRepo.getUserByEmailAndPassword(email,password).orElseThrow(() -> new Exception("User and email does not match"));
        String token = getTokenForEmail(userModel);
     //   String token = getToken(userModel.getEmail());
//        userRepo.updateTokenForUserId(token, userModel.getId()); //create token here
        updateToken(token,userModel.getId()); //update token with value for successful login in the database
        userModel.setToken(token); //sharing with react front app, in the model
//        userRepo.save(userModel);
        return userModel;
    }

    //logout - task 3
    public boolean logout(int userId) throws Exception{
        updateToken("", userId);
        return true;
    }

    //update token method
    private void updateToken(String token, int userId){
        userRepo.updateTokenForUserId(token, userId);
    }

    public boolean validateToken(String token, Integer userId) throws Exception{
        UserModel userModel = findById(userId);
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

    //Day 30 Assignment 1
    private String getTokenForEmail (UserModel userModel){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, 10);
//        c.add(Calendar.SECOND, 5);
        String jwtToken = Jwts.builder()
                .claim("email", userModel.getEmail())
                .setSubject(userModel.getName())
                .setId(""+userModel.getId())
                .setIssuedAt(new Date())
                .setExpiration(c.getTime())
                .signWith(SignatureAlgorithm.HS512, "SECRETKEY")
                .compact();
        return jwtToken;
    }

    public boolean checkJWTToken(String token) throws CustomException{
        Jwts.parser().setSigningKey("SECRETKEY").parseClaimsJws(token);
        return true;
    }

    public boolean updateImage(String profilepic, Integer userId) throws Exception {
        UserModel userModel = userRepo.findById(userId).orElseThrow(() -> new Exception("No user found"));
        userRepo.updateProfilePic(profilepic, userId);
        return true;
    }



}
