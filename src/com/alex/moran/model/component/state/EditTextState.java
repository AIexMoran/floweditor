package com.alex.moran.model.component.state;

import com.alex.moran.model.component.Component;
import com.alex.moran.service.ButtonService;
import com.alex.moran.tools.Tools;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class EditTextState extends ComponentState {

    private static EditTextState instance;
    private static TextArea textField;
    private static Button okButton;
    private static Button cancelButton;

    private EditTextState() {

    }

    public static EditTextState getEditTextState() {
        if (instance == null) {
            instance = new EditTextState();
        }
        return instance;
    }

    public void createStage(Label label) {
        textField = new TextArea();
        okButton = new Button("Ok");
        cancelButton = new Button("Cancel");
        BorderPane pane = new BorderPane();
        BorderPane buttonPane = new BorderPane();
        Stage stage = Tools.createWindow("Edit component text", pane, 300, 100);

        textField.setWrapText(true);
        okButton.setPrefSize(40, 20);
        okButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            label.setText(textField.getText());
            stage.close();
        });
        cancelButton.setPrefSize(80, 20);
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            stage.close();
        });
        buttonPane.setLeft(okButton);
        buttonPane.setRight(cancelButton);
        pane.setCenter(textField);
        pane.setBottom(buttonPane);
        stage.show();
    }

    @Override
    public void onPress(MouseEvent event, Component component) {
        createStage(component.getLabel());
        Component.setState(MoveState.getMoveState());
        ButtonService.getButtonService().clearEffects();
    }

    @Override
    public void onDrag(MouseEvent event, Component component) {

    }

    @Override
    public void onReleased(MouseEvent event, Component component) {

    }
}
