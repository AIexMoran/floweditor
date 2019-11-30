
package com.alex.moran.service;

import com.alex.moran.model.component.Component;
import com.alex.moran.model.component.state.*;
import com.alex.moran.model.xml.SaveXml;
import com.alex.moran.model.xml.UploadXml;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class ButtonService {

    private static ButtonService instance;

    private final List<Button> buttons;
    private final List<ImageView> buttonsWithImages;
    private GridPane gridPane;

    private ButtonService() {
        buttons = new ArrayList<>();
        buttonsWithImages = new ArrayList<>();
        setButtons();
    }

    public static ButtonService getButtonService() {
        if (instance == null) {
            instance = new ButtonService();
        }
        return instance;
    }

    private void setButtons() {
        Glow glow = new Glow();
        Image image = new Image(getClass().getResourceAsStream("createTable.png"));

        glow.setLevel(1.2);
        addButton(new ImageView(image), event -> {
            clearEffects();
            ComponentService.getComponentService().addComponent();
            Component.setState(MoveState.getMoveState());
        });
        image = new Image(getClass().getResourceAsStream("delete.png"));
        addButton(new ImageView(image), event -> {
            buttonsWithImages.get(1).setEffect(glow);
            if (Component.getState().equals(DeleteState.getDeleteState())) {
                buttonsWithImages.get(1).setEffect(null);
                Component.setState(MoveState.getMoveState());
            } else {
                Component.setState(DeleteState.getDeleteState());
            }
        });
        image = new Image(getClass().getResourceAsStream("fillColor.png"));
        addButton(new ImageView(image), event -> {
            buttonsWithImages.get(2).setEffect(glow);
            if (Component.getState().equals(FillColorState.getFillColorState())) {
                buttonsWithImages.get(2).setEffect(null);
                Component.setState(MoveState.getMoveState());
            } else {
                Component.setState(FillColorState.getFillColorState());
            }
        });
        image = new Image(getClass().getResourceAsStream("editText.png"));
        addButton(new ImageView(image), event -> {
            buttonsWithImages.get(3).setEffect(glow);
            if (Component.getState().equals(EditTextState.getEditTextState())) {
                buttonsWithImages.get(3).setEffect(null);
                Component.setState(MoveState.getMoveState());
            } else {
                Component.setState(EditTextState.getEditTextState());
            }
        });
        image = new Image(getClass().getResourceAsStream("clear.png"));
        addButton(new ImageView(image), event -> {
            ComponentService.getComponentService().clearPane();
            clearEffects();
            Component.setState(MoveState.getMoveState());
        });
        image = new Image(getClass().getResourceAsStream("save.png"));
        addButton(new ImageView(image), event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Document");
            FileChooser.ExtensionFilter extFilter
                    = new FileChooser.ExtensionFilter("XML", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(AppService.getMainStage());
            if (file != null) {
                SaveXml.getSaveXml().saveXml(file.toString());
            }
        });
        image = new Image(getClass().getResourceAsStream("upload.png"));
        addButton(new ImageView(image), event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Document");
            FileChooser.ExtensionFilter extFilter
                    = new FileChooser.ExtensionFilter("XML", "*.xml");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showOpenDialog(AppService.getMainStage());
            if (file != null) {
                UploadXml.getUploadXml().readXml(file);
            }
        });
    }

    public void clearEffects() {
        for (ImageView i :
                buttonsWithImages) {
            i.setEffect(null);
        }
    }

    private boolean addButton(ImageView image, EventHandler event) {
        image.setFitHeight(50);
        image.setFitWidth(50);
        image.setPickOnBounds(true);
        image.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        return buttonsWithImages.add(image);
    }

    public GridPane getButtonsPane() {
        gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #3b3b3b;");
        gridPane.setVgap(10);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(7.0);
        dropShadow.setOffsetX(4.0);
        dropShadow.setOffsetY(4.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        gridPane.setEffect(dropShadow);
        gridPane.setMinHeight(800);
        for (int i = 0; i < buttonsWithImages.size(); i++) {
            gridPane.add(buttonsWithImages.get(i), 0, i);
        }
        return gridPane;
    }

}
