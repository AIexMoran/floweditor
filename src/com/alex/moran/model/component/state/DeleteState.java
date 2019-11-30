
package com.alex.moran.model.component.state;

import com.alex.moran.model.component.Component;
import com.alex.moran.model.component.ComponentPair;
import com.alex.moran.service.ComponentService;
import java.util.List;
import javafx.scene.input.MouseEvent;

public class DeleteState extends ComponentState {

    private static DeleteState instance;

    private DeleteState() {

    }

    public static DeleteState getDeleteState() {
        if (instance == null) {
            instance = new DeleteState();
        }
        return instance;
    }

    @Override
    public void onPress(MouseEvent event, Component component) {
        List<ComponentPair> pairs = ComponentService.getComponentService().getPairs();

        for (int i = 0; i < pairs.size(); i++) {
            if (pairs.get(i).contain(component)) {
                ComponentService.getComponentService().removeLine(pairs.get(i));
                i--;
            }
        }
        if (ComponentService.getComponentService().removeComponent(component)) {
            Component.setState(MoveState.getMoveState());
        }
    }

    @Override
    public void onDrag(MouseEvent event, Component component) {

    }

    @Override
    public void onReleased(MouseEvent event, Component component) {
        
    }

}
