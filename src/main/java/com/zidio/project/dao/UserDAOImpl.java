package com.zidio.project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zidio.project.entity.RecruiterEntity;
import com.zidio.project.entity.StudentEntity;
import com.zidio.project.entity.UserEntity;
import com.zidio.project.model.User;
import com.zidio.project.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Repository(value="UserDAO")
public class UserDAOImpl implements UserDAO {
    @Autowired
    private CrudRepository<UserEntity, String> userRepository;

    @Autowired
    private CrudRepository<StudentEntity, String> studentRepository;

    @Autowired 
    private CrudRepository<RecruiterEntity, String>recruitorRepository;

    public ResponseEntity<ApiResponse> userLogin(User user)throws Exception{
        UserEntity existingUser = userRepository.findById(user.getUsername()).orElse(null);
        if(existingUser != null){
            if(existingUser.getPassword().equals(user.getPassword())){
                return new ResponseEntity<>(new ApiResponse(200,
                existingUser.getUsertype()), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse(401,
                "Incorrect password"), HttpStatus.UNAUTHORIZED);
            }
        }else{
            return new ResponseEntity<>(new ApiResponse(404,
            "User does not exist"), HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Boolean> isUserExists(String username)throws Exception{
        UserEntity existingUser = userRepository.findById(username).orElse(null);
        if(existingUser != null){
            return new ResponseEntity<>(true, HttpStatus.CONFLICT);
        }else{
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
    public ResponseEntity<Boolean> isUserPresent(String username)throws Exception{
        UserEntity existingUser = userRepository.findById(username).orElse(null);
        if(existingUser != null){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
    public ResponseEntity<ApiResponse> registerUser(User user)throws Exception{
        UserEntity existingUser = userRepository.findById(user.getUsername()).orElse(null);
        if(existingUser != null){
                return new ResponseEntity<>(new ApiResponse(409,
                "User already exists"), HttpStatus.CONFLICT);
            }else{
                UserEntity userEntity = new UserEntity();
                userEntity.setUsername(user.getUsername());
                userEntity.setPassword(user.getPassword());
                userEntity.setUsertype(user.getUsertype());
                userRepository.save(userEntity);
                if(user.getUsertype().equals("student")){
                    StudentEntity studentEntity = new StudentEntity();
                    studentEntity.setUsername(user.getUsername());
                    studentRepository.save(studentEntity);  
                }else if(user.getUsertype().equals("recruiter")){
                    RecruiterEntity recruiterEntity = new RecruiterEntity();
                    recruiterEntity.setUsername(user.getUsername());
                    recruitorRepository.save(recruiterEntity);
                }
                return new ResponseEntity<>(new ApiResponse(201,
                "User registered successfully"),HttpStatus.CREATED);
            }
        }
        public ResponseEntity<String> changePassword(String username, String oldPassword, String newPassword)throws Exception{
            UserEntity existingUser = userRepository.findById(username).orElse(null);
            if(existingUser != null){
                if(existingUser.getPassword().equals(oldPassword)){
                    existingUser.setPassword(newPassword);
                    userRepository.save(existingUser);
                    return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
                }else{
                    return new ResponseEntity<>("Old password is incorret", HttpStatus.UNAUTHORIZED);
                }
            }else{
                return new ResponseEntity<>("USer does not exist", HttpStatus.NOT_FOUND);
            }
        }
        public ResponseEntity<String> resetPassword(String username, String newPassword)throws Exception{
            UserEntity existingUser = userRepository.findById(username).orElse(null);
            if(existingUser != null){
                existingUser.setPassword(newPassword);
                userRepository.save(existingUser);
                return new ResponseEntity<>("Password reset successfully", HttpStatus.OK);

            }else{
                return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
            }
        }
    }

