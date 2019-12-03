
package com.alex.moran.model.component.state;

import com.alex.moran.model.component.Component;
import com.alex.moran.model.component.Component.ComponentCircle;
import com.alex.moran.model.component.ComponentPair;
import com.alex.moran.service.ComponentService;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class CreateLineState {

    private static List<Component> pairs;
    private static List<ComponentCircle> componentCircles;
    private static Line line;
    private static CreateLineState instance;

    private CreateLineState() {

    }

    public static CreateLineState getCreateLineState() {
        if (instance == null) {
            instance = new CreateLineState();
        }
        return instance;
    }

    public void onPress(MouseEvent event, Component component, ComponentCircle circle) {
        pairs = new ArrayList<>();
        componentCircles = new ArrayList<>();
        line = new Line();

        pairs.add(component);
        componentCircles.add(circle);
        ComponentService.getComponentService().addTmpLine(line);
        line.setStrokeWidth(5);
        line.setStartX(event.getSceneX());
        line.setStartY(event.getSceneY());
        line.setEndX(event.getSceneX());
        line.setEndY(event.getSceneY());
    }

    public void onDrag(MouseEvent event, Component component) {
        line.setEndX(event.getSceneX());
        line.setEndY(event.getSceneY());
    }

    public void onReleased(MouseEvent event, Component component) {
        ComponentService.getComponentService().removeTmpLine(line);

        for (Component c : ComponentService.getComponentService().getComponents()) {
            for (ComponentCircle comCircle : c.getUpComponentCircles()) {
                if (comCircle.getCircle().contains(event.getSceneX(), event.getSceneY())) {
                    if (!componentCircles.contains(comCircle) && !pairs.contains(c)) {
                        pairs.add(comCircle.getComponent());
                        componentCircles.add(comCircle);
                        ComponentService.getComponentService().addPairComponentsWithCircles(pairs.get(0), pairs.get(1), componentCircles.get(0), componentCircles.get(1));
                    }
                }
            }
            for (ComponentCircle comCircle : c.getDownComponentCircles()) {
                if (comCircle.getCircle().contains(event.getSceneX(), event.getSceneY())) {
                    if (!componentCircles.contains(comCircle) && !pairs.contains(c)) {
                        pairs.add(comCircle.getComponent());
                        componentCircles.add(comCircle);
                        ComponentService.getComponentService().addPairComponentsWithCircles(pairs.get(0), pairs.get(1), componentCircles.get(0), componentCircles.get(1));
                    }
                }
            }
        }
        for (ComponentPair p : ComponentService.getComponentService().getPairs()) {
            p.updateLine();
        }
    }

}
