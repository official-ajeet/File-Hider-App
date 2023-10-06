package dao;

import database.MyConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {//check if user exists else add in db
    public static boolean isUserExist(String email)throws SQLException {//taking email because , uniqueness is based on it
        //here we are checking the entered email and Email in db is equal or not if equal it means user exists returning true;
        Connection connection = MyConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT email FROM users");
        ResultSet resultSet = pstm.executeQuery();//it returns data so store it
        while(resultSet.next()){
            String e = resultSet.getString(1);
            if(e.equals(email)){
                return true;
            }
        }
        return false;
    }

    //if user does not exist then insert it into database or save it, we have passes user obj because we need to use both attributes of a user
    public static int saveUser(User user)throws SQLException{
        Connection connection = MyConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO users(name,email) Values(?,?)");
        pstm.setString(1,user.getName());
        pstm.setString(2,user.getEmail());
        return pstm.executeUpdate();//returns the no. of rows affected
    }

}
