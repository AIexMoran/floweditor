
package com.alex.moran.model.component.state;

import com.alex.moran.model.component.Component;
import javafx.scene.input.MouseEvent;

public abstract class ComponentState {

    protected Component component;
    
    public abstract void onPress(MouseEvent event, Component component);
    public abstract void onDrag(MouseEvent event, Component component);
    public abstract void onReleased(MouseEvent event, Component component);
    
}
