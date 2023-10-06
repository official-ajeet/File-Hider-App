package model;

public class Data {
    private  int id;
    private String fileName, filePath, email;

    public Data(int id, String fileName, String filePath, String email) {
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
        this.email = email;
    }

    //constructor for id name and file path for storing in list
    public Data(int id, String fileName, String filePath){
        this.id = id;
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
