package br.upe.userInterface;

import br.upe.userInterface.ScreenManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppContext {
    private static final AppContext instance = new AppContext();

    private final ExecutorService threadPool;
    private final Map<String, ScreenManager> screenManagers;

    private AppContext() {
        this.threadPool = Executors.newFixedThreadPool(1);
        this.screenManagers = new HashMap<String, ScreenManager>();
    }

    public static AppContext getContext() {
        return instance;
    }

    public ExecutorService getThreadPool () {
        return threadPool;
    }

    public ScreenManager getStageScreenManager(String stageName) {
        if (!screenManagers.containsKey(stageName)) {
            throw new IllegalArgumentException("There isn't an entry for the stage name passed");
        }

        return screenManagers.get(stageName);
    }

    public void setStageScreenManager(String stageName, ScreenManager screenManager) {
        screenManager.put(stageName, screenManager);
    }
}
