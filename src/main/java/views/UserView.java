package views;

import dao.DataDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String email;
    public UserView(String email){
        this.email = email;
    }
    public void home(){
        do{
            System.out.println("Welcome "+this.email);
            System.out.println("Press 1 to Show Hidden Files");
            System.out.println("Press 2 to Hide a New File");
            System.out.println("Press 3 to Unhide a File");
            System.out.println("Press 4 to Main Menu");
            System.out.println("Press 0 to exit");

            Scanner sc = new Scanner(System.in);
            int ch = Integer.parseInt(sc.nextLine());//using this to avoid bugs lmao
            switch (ch){
                case 1 ->{
                    try {
                        //present data which was stored in list
                        List<Data> files = DataDAO.getAllFiles(this.email);
                        if(files.isEmpty()){
                            System.out.println("No Hidden Files!");
                        }else{
                            System.out.println("ID - File Name");
                            for(Data file : files){
                                System.out.println(file.getId()+" - "+file.getFileName());
                            }

                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }

                case 2 -> {
                    //hide a file - need only path of a file
                    System.out.print("Enter the file path: ");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0,f.getName(),path,this.email);

                    try{
                        int ans = DataDAO.hideFile(file);
                        if(ans > 0){
                            System.out.println("File Hiding Successfull!");
                        }else{
                            System.out.println("Oops Something went wrong!");
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }

                //unhide a file
                case 3 ->{
                    //pehele id select krao fir use del krna h
                    List<Data> files = null;
                    try {
                        files = DataDAO.getAllFiles(this.email);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    if(files.isEmpty()){
                        System.out.println("No Hidden Files!");
                    }else{
                        System.out.println("ID - File Name");
                        for(Data file : files){
                            System.out.println(file.getId()+" - "+file.getFileName());
                        }

                        System.out.print("Enter the id of file to Unhide: ");
                        int id = Integer.parseInt(sc.nextLine());
                        //now check if id is valid or exist or not
                        boolean isValidId = false;
                        for(Data file : files){
                            if(file.getId() == id){
                                isValidId = true;
                                break;
                            }
                        }

                        //if id is valid then unhide it
                        if(isValidId){
                            try {
                                DataDAO.unHide(id);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            System.out.println("Invalid Id");
                        }
                    }
                }
                //go back to main menu
                case 4 ->{
                    Welcome w = new Welcome();
                    w.welcomeScreen();
                }
                //exit
                case 0 -> System.exit(0);
            }
        }while(true);
    }
}
