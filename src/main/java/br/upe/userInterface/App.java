package br.upe.userInterface;

import br.upe.userInterface.AppContext;
import br.upe.userInterface.ScreenManager;
import br.upe.database.Database;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {
        Exception firstException = null;
        ExecutorService threadPool;
        try {
            Database.initializeDatabase();

            threadPool = Executors.newFixedThreadPool(1);
            AppContext.setThreadPool(threadPool);

            Map<String, String> screenPaths = new HashMap<String, String>();
            screenPaths.put("loginScreen", "/fxml/LoginScreen.fxml");

            ScreenManager mainStageScreenManager = new ScreenManager(mainStage, screenPaths);
            AppContext.setMainStageScreenManager("mainStage", mainStageScreenManager);

            mainStage.setTitle("Nexus");
            Parent loginScreen = mainStageScreenManager.getScreen("loginScreen");
            Scene mainStageScene = new Scene(loginScreen, 920, 520);
            mainStage.setScene(mainStageScene);
            mainStage.show();
        }
        catch (Exception error) {
            firstException = error;
            throw error;
        }
        finally {
            try {
                threadPool.shutdownNow();
                Database.shutDown();
            }
            catch (Exception error) {
                if (firstException != null) {
                    error.addSuppressed(firstException);
                }
                throw error;
            }
        }
    }
}
