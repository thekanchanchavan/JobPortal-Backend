package com.zidio.project.api;


import com.zidio.project.validator.Validator;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zidio.project.model.User;
import com.zidio.project.service.UserServiceImpl;

@RestController
@RequestMapping("/user")
public class UserAPI {
    @org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handleHttpessageNotReadableException(org.springframework.http.converter.HttpMessageNotReadableException ex){
        return new ResponseEntity<>(new ApiResponse(400,
        "Invalid JSON In Request Boy"), HttpStatus.BAD_REQUEST);
    }
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    public Environment environment;

    @GetMapping("/isuserexist/{username}")
    public ResponseEntity<Boolean> isUserExists(@PathVariable("username")String username)throws Exception{
        try{
            String decodedUsername = java.net.URLDecoder.decode(username, "UTF-8");
            ResponseEntity<Boolean> response = userService.isUserExists(decodedUsername);
            return response;
        }catch(Exception exception){
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    
    @GetMapping("/isuserpresent/{username}")
    public ResponseEntity <Boolean> isUserPresent(@PathVariable("username")String username)throws Exception{
        try{
            String decodedUsername = java.net.URLDecoder.decode(username,"UTF-8");
            ResponseEntity<Boolean> response = userService.isUserPresent(decodedUsername);
            return response;
        }catch(Exception exception){
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/isvalidusernameandpassword")
    public ResponseEntity<ApiResponse> isValidUsername(@RequestBody User user)throws Exception{
        try{
            String username = user.getUsername();
            String password = user.getPassword();
            System.out.println(username+ password+ username);

            if(username == null || username.isEmpty() || password == null || password.isEmpty()){
                return new ResponseEntity<>(new ApiResponse(400,
                "Required fields are missing"), HttpStatus.BAD_REQUEST);
            }
            if(!Validator.isValidUsername(username)){
                return new ResponseEntity<>(new ApiResponse(400, 
            "Invalid Username"), HttpStatus.BAD_REQUEST);
            }
            if(!Validator.isValidPassword(password)){
                return new ResponseEntity<>(new ApiResponse(400,
                "Invalid Password"),HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(new ApiResponse(200,
            "Valid Username & password"), HttpStatus.OK);
        }catch(Exception exception){
            return new ResponseEntity<>(new ApiResponse(501, 
            "Internal Serever Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody User user)throws Exception{
        try{
            ResponseEntity<ApiResponse> response = userService.registerUser(user);
            return response;
        }catch(Exception exception){
            return new ResponseEntity<>(new ApiResponse(501,
            "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody User user)throws Exception{
        try{
            ResponseEntity<ApiResponse> response = userService.authenticateUser(user);
            return response;
        }catch(Exception exception){
            return new ResponseEntity<ApiResponse>(new ApiResponse(501,
            "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/changepassword/{oldpassword}")
    public ResponseEntity<String> changePassword(@PathVariable("oldpassword")String oldpassword, @RequestBody User user)throws Exception{
        try{
            String decodedOldPassword = java.net.URLDecoder.decode(oldpassword, "UTF-8");
            String username = user.getUsername();
            String newPassword = user.getPassword();
            if(username == null || username.isEmpty() || decodedOldPassword == null || decodedOldPassword.isEmpty() || newPassword == null || newPassword.isEmpty()){
                return new ResponseEntity<>("Required fields are missing", HttpStatus.BAD_REQUEST);
            }
            if(!Validator.isValidPassword(newPassword)){
                return new ResponseEntity<>("New Password requirement not matched", HttpStatus.BAD_REQUEST);
            }
            return userService.changePassword(username,decodedOldPassword, newPassword);
        }catch(Exception exception){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestBody User user)throws Exception{
        try{
            String username = user.getUsername();
            String newPassword= user.getPassword();
            if(username == null || username.isEmpty() || newPassword == null || newPassword.isEmpty()){
                return new ResponseEntity<>("Required fields are missing", HttpStatus.BAD_REQUEST);
            }
            if(!Validator.isValidPassword(newPassword)){
                return new ResponseEntity<>("New Password fields are missing", HttpStatus.BAD_REQUEST);
            }
            return userService.resetPassword(username, newPassword);
        }catch(Exception exception){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
