package com.example.libmswgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {
    private static String url="jdbc:mysql://localhost:3306/hnd2023";
    private static String password = "iamagoodboy";
    private static String uid="root";
    public static Connection con;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.show();
    }
public static Connection connect(){

    try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        con= DriverManager.getConnection(url,uid,password);
    }catch(Exception e){
        e.printStackTrace();
        connect();
    }


    return con;

}
    public static void main(String[] args) {
        launch();

    }
}