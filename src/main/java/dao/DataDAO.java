package dao;

import database.MyConnection;
import model.Data;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DataDAO {
    //get all files - - use list
    public static List<Data> getAllFiles(String email)throws SQLException {
        Connection connection = MyConnection.getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT * FROM data WHERE email = ?");
        pstm.setString(1,email);
            ResultSet resultSet = pstm.executeQuery();
            List<Data> list = new ArrayList<>();
            while(resultSet.next()){
                //now find all values : we need id, name and filepath,
                int id = resultSet.getInt(1);
                String fileName = resultSet.getString(2);
                String filePath = resultSet.getString(3);
                list.add(new Data(id,fileName,filePath));//there is constructor data
            }
            return list;
        }

        //hide a file - will take all the data to hide
        public static int hideFile(Data file)throws SQLException,IOException{//IO for file reader
            Connection connection = MyConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO data(fileName,filePath,email,bin_data) VALUES(?,?,?,?)");
            pstm.setString(1,file.getFileName());
            pstm.setString(2,file.getFilePath());
            pstm.setString(3,file.getEmail());
            //now to take file data (bin_data)blob
            File f = new File(file.getFilePath());//takes file path
            FileReader fr = new FileReader(f);
            //not set
            pstm.setCharacterStream(4,fr,f.length());//PI,filereader,length of file
            int ans = pstm.executeUpdate();//if 1 means successfully executed else not
            fr.close();//closing file
            f.delete();//deleting file:-  motive is to copy the file data in our db and delete it
            return ans;
        }

        //unhide file -  unhide or del only if file exist
        public static void unHide(int id)throws SQLException,IOException{
            Connection connection = MyConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement("SELECT filePath , bin_data FROM data WHERE id = ?");
            pstm.setInt(1,id);
            ResultSet resultSet = pstm.executeQuery();
            resultSet.next();//have only one data so no need while loop just increment pointer
            String filePath = resultSet.getString("filePath");

            //clob - character obj
            Clob c = resultSet.getClob("bin_data");

            //now retrieve data
            Reader r = c.getCharacterStream();
            FileWriter fw = new FileWriter(filePath);
            //in file handling chars come one by one and we get ascii values
            int i;
            while((i = r.read()) != -1){//har char ko read kro or save karo
                fw.write((char)i); // converting ascii to char
            }
            fw.close();
            //now delete that row data from db
            pstm = connection.prepareStatement("DELETE FROM data WHERE id = ?");
            pstm.setInt(1,id);
            pstm.executeUpdate();
            System.out.println("File Unhiding Successfull");


        }
    }

