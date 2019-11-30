
package com.alex.moran.model.component;

import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ThreatComponent extends Component {

    public ThreatComponent(double x, double y, String text, int color) {
        super(x, y, text);
        this.label.setText(text);
        setSize();
        setColor(color);
        setStyleColor(colors[color]);
    }

    public ThreatComponent() {
        super();
        setSize();
        if (show < 250) {
            show += 20;
        }
        gridPane.setLayoutX(show);
        gridPane.setLayoutY(show);
        setStyleColor(colors[0]);
    }

    public void setSize() {
        width = 200;
        height = 100;
        gridPane.setMinSize(width, height);
        gridPane.setMaxSize(width, height);
        title = "title" + Math.random();
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(20.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        gridPane.setEffect(dropShadow);
        gridPane.setStyle("-fx-border-radius: 12 12 12 12;"
                + "-fx-background-radius: 12 12 12 12");
        addElements();
    }

    public void addElements() {
        this.label.setWrapText(true);
        this.label.setStyle("-fx-padding: 7px;");
        this.label.setFont(new Font("Arial", 16));
        gridPane.add(label, 0, 0);
    }

}
