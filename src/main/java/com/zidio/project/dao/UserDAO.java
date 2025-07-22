package com.zidio.project.dao;
import org.springframework.http.ResponseEntity;

import com.zidio.project.api.ApiResponse;
import com.zidio.project.model.User;
public interface UserDAO {
    public ResponseEntity<ApiResponse> userLogin(User user)throws Exception;
    public ResponseEntity<String> changePassword(String username, String oldPassword, String newPassword)throws Exception;
    public ResponseEntity<ApiResponse> registerUser(User user) throws Exception;
    public ResponseEntity<Boolean> isUserExists(String username)throws Exception;
    public ResponseEntity<Boolean> isUserPresent(String username)throws Exception;
    public ResponseEntity<String> resetPassword(String username, String newPassword)throws Exception;
}
