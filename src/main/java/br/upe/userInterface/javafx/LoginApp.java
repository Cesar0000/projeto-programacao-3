package br.upe.userInterface.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Usando o caminho correto para o arquivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/LoginScreen.fxml"));
        primaryStage.setTitle("Login");

        primaryStage.setScene(new Scene(root, 920, 520));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
