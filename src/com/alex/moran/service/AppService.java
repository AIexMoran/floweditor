
package com.alex.moran.service;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppService {
    
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private static final String APP_TITLE = "Flow editor";
    private static AppService instance;
    
    private Scene scene;
    private Group root;
    private Stage primaryStage;
    
    private AppService() {
        
    }
    
    public static AppService getAppService() {
        if (instance == null) {
            instance = new AppService();
        }
        return instance;
    }
    
    public static Stage getMainStage() {
        return instance.primaryStage;
    }
    
    public AppService init(Stage primaryStage) {
        this.primaryStage = primaryStage;
        root = new Group();
        scene = new Scene(root, WIDTH, HEIGHT);

        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
        return instance;
    }
    
    public void run() {
        ButtonService buttonService = ButtonService.getButtonService();
        ComponentService componentService = ComponentService.getComponentService();

        root.getChildren().add(componentService.getComponentsPane());
        root.getChildren().add(buttonService.getButtonsPane());
    }
}
