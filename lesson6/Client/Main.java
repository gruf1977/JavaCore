package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));


        primaryStage.setTitle("MyChat");
        primaryStage.getIcons().add(new Image("file:images/iconfinder.png"));
        primaryStage.setScene(new Scene(root, 300, 275, Color.TRANSPARENT));

        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
