
package com.alex.moran;

import com.alex.moran.service.AppService;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        AppService
                .getAppService()
                .init(primaryStage)
                .run();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
