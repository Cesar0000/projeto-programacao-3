package br.upe.userinterface;

import java.util.concurrent.ExecutorService;

import javafx.stage.Stage;

public class AppContext {
    public static ExecutorService threadPool;
    public static Stage mainStage;

    private AppContext() {}
}