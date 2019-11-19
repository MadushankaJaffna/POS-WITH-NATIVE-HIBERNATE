package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.db.HibernateUtill;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("viwe/main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 735, 535));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        System.out.println("Closed session Factory");
        HibernateUtill.getSessionFactory().close();
    }
}
