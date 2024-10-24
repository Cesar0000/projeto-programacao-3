package br.upe.userInterface;

import br.upe.userInterface.AppContext;
import br.upe.database.Database;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        ExecutorService threadPool = null;
        try {
            Database.initializeDatabase();

            threadPool = Executors.newFixedThreadPool(1);
            AppContext.threadPool = threadPool;
            AppContext.mainStage = mainStage;

            mainStage.setTitle("Nexus");
            Parent loginScreen = FXMLLoader.load(getClass().getResource("/fxml/screens/LoginScreen.fxml"));
            Scene mainStageScene = new Scene(loginScreen, 920, 520);
            mainStage.setScene(mainStageScene);
            mainStage.show();
        }
        catch (Exception error) {
            if (threadPool != null) {
                threadPool.shutdownNow();
            }

            try {
                Database.shutdown();
            }
            catch (Exception databaseShutdownError) {
                error.addSuppressed(databaseShutdownError);
            }

            error.printStackTrace();

            System.exit(1);
        }
    }

    @Override
    public void stop() {
        AppContext.threadPool.shutdownNow();

        try {
            Database.shutdown();
        }
        catch (Exception error) {
            error.printStackTrace();
        }
    }
}
