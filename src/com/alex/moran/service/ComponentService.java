
package com.alex.moran.service;

import com.alex.moran.model.component.Component;
import com.alex.moran.model.component.Component.ComponentCircle;
import com.alex.moran.model.component.ComponentPair;
import com.alex.moran.model.component.ThreatComponent;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;


public class ComponentService {

    private static ComponentService instance;

    private final List<Component> components;

    private final List<ComponentPair> componentPairs;
    private final Pane pane;

    private ComponentService() {
        components = new ArrayList<>();
        componentPairs = new ArrayList<>();
        pane = new Pane();
    }
    
    public void clearPane() {
        pane.getChildren().clear();
        components.clear();
        componentPairs.clear();
    }
    
    public void addTmpLine(Line line) {
        pane.getChildren().add(line);
    }
    
    public void removeTmpLine(Line line) {
        pane.getChildren().remove(line);
    }

    public boolean removeComponent(Component component) {
        pane.getChildren().remove(component.getGridPane());
        pane.getChildren().removeAll(component.getUpCircles());
        pane.getChildren().removeAll(component.getDownCircles());
        ButtonService.getButtonService().clearEffects();
        return components.remove(component);
    }

    public List<Component> getComponents() {
        return components;
    }
    
    public static ComponentService getComponentService() {
        if (instance == null) {
            instance = new ComponentService();
        }
        return instance;
    }
    
    public void addPairComponentsWithCircles(Component firstComponent, Component secondComponent, ComponentCircle firstCircleComponent, ComponentCircle secondCircleComponent) {
        ComponentPair pair = new ComponentPair(firstComponent, secondComponent, firstCircleComponent, secondCircleComponent);
        if (!componentPairs.contains(pair)) {
            pair.addLines();
            componentPairs.add(pair);
            pane.getChildren().add(pair.getLine());
        }
    }

//    public void addPairComponents(Component firstComponent, Component secondComponent) {
//        ComponentPair pair = new ComponentPair(firstComponent, secondComponent);
//        if (!componentPairs.contains(pair)) {
//            pair.addLines();
//            componentPairs.add(pair);
//            pane.getChildren().add(pair.getLine());
//        }
//    }

    public List<ComponentPair> getPairs() {
        return componentPairs;
    }

    public boolean removeLine(ComponentPair pair) {
        pair.getFirstComponentCircle().setPair(false);
        pair.getSecondComponentCircle().setPair(false);
        pair.getFirstComponent().update();
        pair.getSecondComponent().update();
        pane.getChildren().remove(pair.getLine());
        ButtonService.getButtonService().clearEffects();
        return componentPairs.remove(pair);
    }

    public void updateLines() {
        for (ComponentPair pair : componentPairs) {
            pair.updateLine();
        }
    }
    
    public boolean loadComponent(Component component) {
        pane.getChildren().add(component.getGridPane());
        pane.getChildren().addAll(component.getUpCircles());
        pane.getChildren().addAll(component.getDownCircles());
        return components.add(component);
    }

    public boolean addComponent() {
        ThreatComponent component = new ThreatComponent();
        pane.getChildren().add(component.getGridPane());
        pane.getChildren().addAll(component.getUpCircles());
        pane.getChildren().addAll(component.getDownCircles());
        return components.add(component);
    }

    public Pane getComponentsPane() {
        return pane;
    }

}
