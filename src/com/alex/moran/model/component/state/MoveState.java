
package com.alex.moran.model.component.state;

import com.alex.moran.model.component.Component;
import com.alex.moran.model.component.ComponentPair;
import com.alex.moran.service.ComponentService;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class MoveState extends ComponentState {

    private static MoveState instance;
    private static Component focusedComponent;
    
    private MoveState() {
        
    }
    
    public static MoveState getMoveState() {
        if (instance == null) {
            instance = new MoveState();
        }
        return instance;
    }

    public void setFocus(Component component) {
        if (component.isFocus) {
            component.isFocus = false;
            component.disableFocus();
            ComponentPair.disableFocusLines(component);
        } else if (focusedComponent != null && !component.isFocus) {
            focusedComponent.disableFocus();
            focusedComponent.isFocus = false;
            ComponentPair.disableFocusLines(focusedComponent);
            component.setFocus();
            component.isFocus = true;
            focusedComponent = component;
            ComponentPair.setFocusLines(component);
        } else {
            focusedComponent = component;
            component.isFocus = true;
            component.setFocus();
            ComponentPair.setFocusLines(component);
        }
    }
    
    @Override
    public void onPress(MouseEvent event, Component component) {
        setFocus(component);
        if (event.getButton() == MouseButton.PRIMARY) {
            component.setM_nMouseX(event.getSceneX());
            component.setM_nMouseY(event.getSceneY());

            double m_nX = component.getGridPane().getLayoutX();
            double m_nY = component.getGridPane().getLayoutY();
            component.setM_nX(m_nX);
            component.setM_nY(m_nY);
        }
    }

    @Override
    public void onDrag(MouseEvent event, Component component) {
        if (event.getButton() == MouseButton.PRIMARY) {
            double m_nX = component.getM_nX();
            double m_nY = component.getM_nY();
            double m_nMouseX = component.getM_nMouseX();
            double m_nMouseY = component.getM_nMouseY();
            double deltaX = event.getSceneX() - m_nMouseX;
            double deltaY = event.getSceneY() - m_nMouseY;

            component.setM_nX(m_nX + deltaX);
            component.setM_nY(m_nY + deltaY);
            component.getGridPane().setLayoutX(m_nX);
            component.getGridPane().setLayoutY(m_nY);
            component.setM_nMouseX(event.getSceneX());
            component.setM_nMouseY(event.getSceneY());
            ComponentService.getComponentService().updateLines();
        }
    }

    @Override
    public void onReleased(MouseEvent event, Component component) {
        
    }

}
