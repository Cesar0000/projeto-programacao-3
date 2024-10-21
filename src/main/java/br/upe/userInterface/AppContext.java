package br.upe.userInterface;

import br.upe.userInterface.ScreenManager;

import java.util.concurrent.ExecutorService;

public class AppContext {
    private static ExecutorService threadPool;
    private static ScreenManager mainStageScreenManager;

    public static ExecutorService getThreadPool () {
        return AppContext.threadPool;
    }

    public static void setThreadPool(ExecutorService threadPool) {
        AppContext.threadPool = threadPool;
    }

    public static ScreenManager getMainStageScreenManager() {
        return AppContext.mainStageScreenManager;
    }

    public static void setMainStageScreenManager(ScreenManager mainStageScreenManager) {
        AppContext.mainStageScreenManager = mainStageScreenManager;
    }
}
