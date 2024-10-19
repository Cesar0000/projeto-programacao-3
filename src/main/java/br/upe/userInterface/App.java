package br.upe.userInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Usando o caminho correto para o arquivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScreen.fxml"));
        primaryStage.setTitle("Login");

        primaryStage.setScene(new Scene(root, 920, 520));
        primaryStage.show();
    }
}
