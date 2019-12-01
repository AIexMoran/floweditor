package com.alex.moran.model.component.state;

import com.alex.moran.model.component.Component;
import com.alex.moran.service.ComponentService;
import com.alex.moran.tools.Tools;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FillColorState extends ComponentState {

    private static FillColorState instance;

    private FillColorState() {

    }

    public static FillColorState getFillColorState() {
        if (instance == null) {
            instance = new FillColorState();
        }
        return instance;
    }

    public FlowPane getColorsPane(Component component) {
        FlowPane pane = new FlowPane();
        pane.setVgap(2);
        pane.setHgap(2);

        Button[] colorButtons = new Button[Component.colors.length];
        for (int i = 0; i < colorButtons.length; i++) {
            final int finalI = i;
            colorButtons[i] = new Button();
            colorButtons[i].setPrefSize(30, 30);
            colorButtons[i].setStyle(Component.colors[i]);
            pane.getChildren().add(colorButtons[i]);
            colorButtons[i].addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                component.setColor(finalI);
                component.setStyleColor(Component.colors[finalI]);
            });
        }
        return pane;
    }

    @Override
    public void onPress(MouseEvent event, Component component) {
        FlowPane pane = getColorsPane(component);
        Stage stage = Tools.createWindow("Fill color", pane, 230, 100);

        stage.show();
    }

    @Override
    public void onDrag(MouseEvent event, Component component) {

    }

    @Override
    public void onReleased(MouseEvent event, Component component) {

    }
}
