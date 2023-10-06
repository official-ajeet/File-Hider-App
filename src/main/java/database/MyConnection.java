package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    public static Connection connection;
    public static Connection getConnection(){
        try{
        connection = DriverManager.getConnection("jdbc:mysql://localHost:3306/fileHider_db?useSSL=false","root","1234");
        }catch(SQLException e){
            e.printStackTrace();
        }
//        System.out.println("Connected Successfully!");
        return connection;
    }

    public static void closeConnection(){
        try{
            if(connection != null){//by default, it is null, if we don't check it will give null pointer exception
                connection.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
