package com.zidio.project.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zidio.project.service.EmailService;

@RestController
@RequestMapping("/api/otp")
public class OTPController {
    @Autowired
    private EmailService emailService;
    @PostMapping("/send")
    public String sendOTP(@RequestParam String email){
        String otp = OTPUtil.generateOTP();
        emailService.sendEmail(email,"Your OTP for Job-Portal","Your OTP for Job Portal is: " + otp);
        return otp;
    }
}
