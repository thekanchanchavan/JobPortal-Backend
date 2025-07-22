package com.zidio.project.api;

import java.util.Random;

public class OTPUtil {
    public static String generateOTP(){
        Random random = new Random();
        //generate a 6- digit otp
        return String.format("%06d", random.nextInt(1000000));
        
    }
}
