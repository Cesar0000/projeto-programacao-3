package br.upe.userInteface.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Usando o caminho correto para o arquivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 600, 400)); // Aumente os valores conforme necessário
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
