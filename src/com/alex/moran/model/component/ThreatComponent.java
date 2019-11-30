
package com.alex.moran.model.component;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ThreatComponent extends Component {

    public ThreatComponent(double x, double y) {
        super(x, y);
        setSize();
    }

    public ThreatComponent() {
        super();
        setSize();
        if (show < 250) {
            show += 20;
        }
        gridPane.setLayoutX(show);
        gridPane.setLayoutY(show);
    }

    public void setSize() {
        width = 200;
        height = 100;
        gridPane.setMinSize(width, height);
        gridPane.setMaxSize(width, height);
        title = "title" + Math.random();
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(6.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        gridPane.setEffect(dropShadow);
        gridPane.setStyle("-fx-background-color: #5a5a5a;"
                + "-fx-border-radius: 12 12 12 12;"
                + "-fx-background-radius: 12 12 12 12");
        addElements();
    }

    public void addElements() {
        Label label = new Label("  THIS IS GRIDPANE");
        label.setFont(new Font("Arial", 16));
        gridPane.add(label, 0, 0);
    }

}
