package service;

import java.util.Random;

public class GenerateOTP {
    public static String getOtp(){
        Random random = new Random();
        //we have used string formating to get only four digits
        return String.format("%04d", random.nextInt(10000));
    }
}
