package com.alex.moran.tools;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Tools {

    public static Stage createWindow(String title, Pane pane, int width, int height) {
        Scene scene = new Scene(pane, width, height);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }

}
