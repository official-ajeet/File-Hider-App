package service;

import dao.UserDAO;
import model.User;

import java.sql.SQLException;

public class UserService {
    public static Integer saveUser(User user){
        try{
            if(UserDAO.isUserExist(user.getEmail())){
                return 0;//means user found , you can not enter this user , duplicate found, return false
            }else{
                return UserDAO.saveUser(user);//else save the user, was type of int so return it
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 1;
    }
}
