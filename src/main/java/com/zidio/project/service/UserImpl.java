// package com.zidio.project.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import com.zidio.project.dao.UserDAO;
// import com.zidio.project.model.User;
// import com.zidio.project.validator.Validator;

// import org.springframework.transaction.annotation.Transactional;
// @Transactional
// @Service
// public class UserImpl {
//     @Autowired
//     private UserDAO  userDAO;
//     public Integer registerUser(User user)throws Exception {
// 		String username = user.getUsername();
// 		String password = user.getPassword();
// 		String usertype = user.getUsertype();
// 		if (username == null || username.isEmpty() || password == null || password.isEmpty() ||
// 		    usertype == null || usertype.isEmpty()) {
// 			throw new Exception("required fields are missing");
// 		}
// 		if(!Validator.isValidUsername(username)) {
// 			throw new Exception("username requirement not matched");
// 		}
//         if(!Validator.isValidPassword(password)) {
// 			throw new Exception("password requirement not matched");
//         }
// 		if (!Validator.isValidUsertype(usertype)){
// 			throw new Exception("invalid usertype");
//         }
// 		return userDAO.registerUser(user)
	
//     }

//     public Integer authenticateUser(User user)throws Exception {
// 		String username = user.getUsername();
// 		String password = user.getPassword();
// 		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
// 		    throw new Exception("required fields are missing");
// 	    }
// 		return userDAO.userLogin(user);
// 	}

// }    