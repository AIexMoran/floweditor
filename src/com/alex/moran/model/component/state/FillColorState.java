package com.alex.moran.model.component.state;

import com.alex.moran.model.component.Component;
import com.alex.moran.service.ComponentService;
import javafx.scene.input.MouseEvent;

public class FillColorState extends ComponentState {

    private static FillColorState instance;
    private static final String[] colors = new String[4];

    private FillColorState() {
        colors[0] = "-fx-background-color: #5a5a5a;";
        colors[1] = "-fx-background-color: #48b84f;";
        colors[2] = "-fx-background-color: #acb848;";
        colors[3] = "-fx-background-color: #b84848;";
    }

    public static FillColorState getFillColorState() {
        if (instance == null) {
            instance = new FillColorState();
        }
        return instance;
    }

    @Override
    public void onPress(MouseEvent event, Component component) {
        int currentColor = component.getColor();

        if (currentColor == 3) {
            currentColor = 0;
        } else {
            currentColor++;
        }
        component.setColor(currentColor);
        component.setStyleColor(colors[currentColor]);
    }

    @Override
    public void onDrag(MouseEvent event, Component component) {

    }

    @Override
    public void onReleased(MouseEvent event, Component component) {

    }
}
