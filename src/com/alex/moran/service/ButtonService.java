
package com.alex.moran.service;

import com.alex.moran.model.component.Component;
import com.alex.moran.model.component.state.CreateLineState;
import com.alex.moran.model.component.state.DeleteState;
import com.alex.moran.model.component.state.MoveState;
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
        Image image = new Image(getClass().getResourceAsStream("createTable.png"));
        addButton(new ImageView(image), event -> {
            ComponentService.getComponentService().addComponent();
            Component.setState(MoveState.getMoveState());
        });
        image = new Image(getClass().getResourceAsStream("delete.png"));
        addButton(new ImageView(image), event -> {
            Glow glow = new Glow();
            glow.setLevel(1.2);
            buttonsWithImages.get(1).setEffect(glow);
            if (Component.getState().equals(DeleteState.getDeleteState())) {
                buttonsWithImages.get(1).setEffect(null);
                Component.setState(MoveState.getMoveState());
            } else {
                Component.setState(DeleteState.getDeleteState());
            }
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

    private boolean addButton(ImageView image, EventHandler event) {
        image.setFitHeight(50);
        image.setFitWidth(50);
        image.setPickOnBounds(true);
        image.addEventHandler(MouseEvent.MOUSE_CLICKED, event);
        return buttonsWithImages.add(image);
    }

    public GridPane getButtonsPane() {
        gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: #C4C3C3;");
        gridPane.setVgap(10);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(6.0);
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
