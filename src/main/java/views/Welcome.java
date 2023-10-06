package views;

import dao.UserDAO;
import model.User;
import service.GenerateOTP;
import service.SendOtpService;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {
    Scanner sc = new Scanner(System.in);

    public void welcomeScreen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Welcome to the File Hiding App");
        System.out.println("Press 1 to LogIn");
        System.out.println("Press 2 to SignUp");
        System.out.println("Press 0 to Exit");

        int choice = 0;
        try {
            choice = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (choice) {
            case 1 -> logIn();
            case 2 -> signUp();
            case 0 -> System.exit(0);
        }
    }

    private void logIn() {
        System.out.print("Enter your registered email: ");
        String email = sc.nextLine();
        //check if user already exists then welcome otherwise go to signup
        try {
            if (UserDAO.isUserExist(email)) {
                String genOtp = GenerateOTP.getOtp();
                SendOtpService.sendOTP(email, genOtp);
                System.out.print("Enter the OTP: ");
                String otp = sc.nextLine();
                if (otp.equals(genOtp)) {
                    new UserView(email).home();//to show welcome abc@gmail.com,,, home , to cont, logged in display

                } else {
                    System.out.println("Invalid Otp!");
                }
            } else {
                System.out.println("User doesn't exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void signUp() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();
        System.out.print("Enter your email: ");
        String email = sc.nextLine();
        //check if the email is already registered or not

        try {
            if(UserDAO.isUserExist(email)){
                System.out.println("Email already registered Press 1 to login");
            }else{
                String genOtp = GenerateOTP.getOtp();
                SendOtpService.sendOTP(email, genOtp);
                System.out.print("Enter the Otp: ");
                String otp = sc.nextLine();
                if (otp.equals(genOtp)) {
                    //if email verifies then add user to db
                    User user = new User(name, email);
                    int response = UserService.saveUser(user);//return an int value
                    switch (response) {
//                        case 0 -> System.out.println("User already exists");
                        case 1 -> System.out.println("Signed Up Successfully");
                    }

                } else {
                    System.out.println("Invalid Otp");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}



