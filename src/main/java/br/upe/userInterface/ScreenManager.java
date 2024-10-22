package br.upe.userInterface;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

import java.util.Map;
import java.util.HashMap;
import java.util.Deque;
import java.util.LinkedList;

import java.io.IOException;

public class ScreenManager {
    private final Stage stage;
    // Stage name to screen root node
    private final Map<String, Parent> screens = new HashMap<String, Parent>();
    // Stack of screens
    private final Deque<Parent> stack = new LinkedList<Parent>();

    public ScreenManager(Stage stage, Map<String, String> screenPaths) throws IOException {
        this.stage = stage;
        loadScreens(screenPaths);
    }

    private void loadScreens(Map<String, String> screenPaths) throws IOException {
        for (Map.Entry<String, String> entry : screenPaths.entrySet()) {
            String screenName = entry.getKey();
            String screenPath = entry.getValue();
            Parent screen = FXMLLoader.load(getClass().getResource(screenPath));
            screens.put(screenName, screen);
        }
    }

    public void goToScreen(String screenName) {
        Parent currentRoot = stage.getScene().getRoot();
    }

    public void goToScreenAndClearStack() {
    }

    public void goToPreviousScreen() {
    }

    public boolean hasPreviousScreen() {
        return false;
    }

    public Parent getScreen(String screenName) {
        if (!screens.containsKey(screenName)) {
            throw new IllegalArgumentException("There isn't an entry for the screen name passed");
        }

        return screens.get(screenName);
    }
}
