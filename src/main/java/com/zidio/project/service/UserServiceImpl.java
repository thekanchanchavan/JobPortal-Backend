package com.zidio.project.service;

import com.zidio.project.validator.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zidio.project.api.ApiResponse;
import com.zidio.project.dao.UserDAO;
import com.zidio.project.model.User;

@Transactional
@Service(value="UserService")
public class UserServiceImpl {
    @Autowired
    private UserDAO userDAO;
    public ResponseEntity<ApiResponse> authenticateUser(User user)throws Exception{
        String username = user.getUsername();
        String password = user.getPassword();
        if(username == null || username.isEmpty() || password == null || password.isEmpty()){
            return new ResponseEntity<>(new ApiResponse(400,
            "required fields are missing"), HttpStatus.BAD_REQUEST);
        }
        return userDAO.userLogin(user);
    }
    public ResponseEntity<Boolean> isUserExists(String username)throws Exception{
        if(username == null || username.isEmpty()){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        return userDAO.isUserExists(username);
    }
    public ResponseEntity<Boolean> isUserPresent(String username)throws Exception{
        if(username == null || username.isEmpty()){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
        return userDAO.isUserPresent(username);
    }
    public ResponseEntity<ApiResponse> registerUser(User user)throws Exception{
        String username = user.getUsername();
        String password = user.getPassword();
        String usertype = user.getUsertype();
        if(username == null || username.isEmpty() || password == null ||password.isEmpty() ||
           usertype == null || usertype.isEmpty()){
            return new ResponseEntity<>(new ApiResponse(400,
            "Username requirement not matched"), HttpStatus.BAD_REQUEST);
        }
        if(!Validator.isValidUsername(username)){
            return new ResponseEntity<>(new ApiResponse(400,
            "Username requirement not matched"), HttpStatus.BAD_REQUEST);
        }
        if(!Validator.isValidPassword(password)){
            return new ResponseEntity<>(new ApiResponse(400,
            "Password requirement not matched"), HttpStatus.BAD_REQUEST);
        }
        if(!Validator.isValidUsertype(usertype)){
            return new ResponseEntity<>(new ApiResponse(400,
            "Invalid usertype"), HttpStatus.BAD_REQUEST);
        }
        return userDAO.registerUser(user);
    }
    public ResponseEntity<String> changePassword(String username, String oldPassword,String newPassword)throws Exception{
        return userDAO.changePassword(username,oldPassword, newPassword);
    }
    public ResponseEntity<String> resetPassword(String username, String newPassword)throws Exception{
        if(username == null || username.isEmpty() || newPassword == null ||  newPassword.isEmpty()){
            return new ResponseEntity<>("Password requirement not  matched", HttpStatus.BAD_REQUEST);
        }
        return userDAO.resetPassword(username, newPassword);
    }
    
}
