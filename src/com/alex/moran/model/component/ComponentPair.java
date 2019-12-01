
package com.alex.moran.model.component;

import com.alex.moran.model.component.Component.ComponentCircle;
import com.alex.moran.model.component.state.DeleteState;
import com.alex.moran.model.component.state.MoveState;
import com.alex.moran.service.ComponentService;

import java.util.Objects;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class ComponentPair {

    private static int count = 0;

    private final Component firstComponent;
    private final Component secondComponent;
    private final ComponentCircle firstComponentCircle;
    private final ComponentCircle secondComponentCircle;

    private final CubicCurve cubicCurve;
    private final Line line;
    private double firstX;
    private double secondX;

    private double firstY;
    private double secondY;
    private double firstAddX;
    private double secondAddX;

    public ComponentPair(Component firstComponent, Component secondComponent, ComponentCircle firstComponentCircle, ComponentCircle secondComponentCircle) {
        this.firstComponent = firstComponent;
        this.secondComponent = secondComponent;
        this.firstComponentCircle = firstComponentCircle;
        this.secondComponentCircle = secondComponentCircle;
        cubicCurve = new CubicCurve();
//        cubicCurve.setStyle("-fx-stroke-dash-array: 2 12 12 2;");
        firstAddX = firstComponent.getAddX(firstComponentCircle);
        secondAddX = secondComponent.getAddX(secondComponentCircle);
        line = new Line();
        initCubicCurve();
        updateLine();
    }

//    public ComponentPair(Component firstComponent, Component secondComponent) {
//        this.firstComponent = firstComponent;
//        this.secondComponent = secondComponent;
//        cubicCurve = new CubicCurve();
//        cubicCurve.setStyle("-fx-stroke-dash-array: 2 12 12 2;");
//        firstAddX = firstComponent.getAddX();
//        secondAddX = secondComponent.getAddX();
//        line = new Line();
//        initCubicCurve();
//        updateLine();
//    }

    public static void setFocusLines(Component component) {
        for (ComponentPair pair :
                ComponentService.getComponentService().getPairs()) {
            if (pair.contain(component)) {
                pair.firstComponentCircle.getCircle().setFill(Color.rgb(55, 153, 137));
                pair.secondComponentCircle.getCircle().setFill(Color.rgb(55, 153, 137));
                pair.cubicCurve.setStroke(Color.rgb(55, 153, 137));
            }
        }
    }

    public static void disableFocusLines(Component component) {
        for (ComponentPair pair :
                ComponentService.getComponentService().getPairs()) {
            pair.cubicCurve.setStroke(Color.BLACK);
        }
        for (Component c :
                ComponentService.getComponentService().getComponents()) {
            Circle[] upCircles = c.getUpCircles();
            Circle[] downCircles = c.getDownCircles();
            for (int i = 0; i < c.getUpCircles().length; i++) {
                upCircles[i].setFill(Color.SALMON);
                downCircles[i].setFill(Color.SALMON);
            }
        }
    }

    public void addLines() {
        firstComponentCircle.setPair(true);
        secondComponentCircle.setPair(true);
        firstComponent.update();
        secondComponent.update();
    }

    public Component getFirstComponent() {
        return firstComponent;
    }

    public Component getSecondComponent() {
        return secondComponent;
    }

    public ComponentCircle getFirstComponentCircle() {
        return firstComponentCircle;
    }

    public ComponentCircle getSecondComponentCircle() {
        return secondComponentCircle;
    }

    public void initCubicCurve() {
        cubicCurve.setOnMousePressed(event -> {
            if (Component.getState().equals(DeleteState.getDeleteState())) {
                ComponentService.getComponentService().removeLine(this);
                Component.setState(MoveState.getMoveState());
                ComponentService.getComponentService().updateLines();
            }
        });
        cubicCurve.setStroke(Color.BLACK);
        cubicCurve.setStrokeWidth(4);
        cubicCurve.setStrokeLineCap(StrokeLineCap.ROUND);
        cubicCurve.setFill(Color.CORNSILK.deriveColor(1, 1, 1, 0));
    }

    public Shape getLine() {
        return cubicCurve;
    }

    public void updateLine() {
//        firstX = firstComponent.getGridPane().getLayoutX();
//        firstY = firstComponent.getGridPane().getLayoutY();
//        secondX = secondComponent.getGridPane().getLayoutX();
//        secondY = secondComponent.getGridPane().getLayoutY();
//        firstX += firstAddX;
//        secondX += secondAddX;
        firstX = firstComponentCircle.getCircle().getCenterX();
        secondX = secondComponentCircle.getCircle().getCenterX();
        firstY = firstComponentCircle.getCircle().getCenterY();
        secondY = secondComponentCircle.getCircle().getCenterY();
//        if (firstY < secondY && Math.abs(firstY - secondY) >= 100) {
//            firstY += firstComponent.getHeight();
//        } else if (firstY > secondY && Math.abs(firstY - secondY) >= 100) {
//            secondY += firstComponent.getHeight();
//        } else if (Math.abs(firstY - secondY) < 100) {
//            firstY += firstComponent.getHeight();
//            secondY += secondComponent.getHeight();
//        }
        if (Math.abs(firstY - secondY) < 200) {
            cubicCurve.setControlY1(secondY + 40);
            cubicCurve.setControlX1(firstX);
            cubicCurve.setControlY2(firstY + 40);
            cubicCurve.setControlX2(secondX);
        } else {
            cubicCurve.setControlY1(secondY - 10);
            cubicCurve.setControlX1(firstX - 10);
            cubicCurve.setControlY2(firstY - 10);
            cubicCurve.setControlX2(secondX - 10);
        }
//        if (firstX <= secondX && Math.abs(firstY - secondY) < 200 && Math.abs(firstX - secondX) > 100) {
//            firstY += firstComponent.getHeight() / 2;
//            firstX += firstComponent.getWidth();
//            secondY += secondComponent.getHeight() / 2;
//            upDown = true;
//        } else if (firstX >= secondX && Math.abs(firstY - secondY) < 200 && Math.abs(firstX - secondX) > 100) {
//            secondY += secondComponent.getHeight() / 2;
//            secondX += secondComponent.getWidth();
//            firstY += firstComponent.getHeight() / 2;
//            upDown = true;
//        } else if (firstX <= secondX && firstY <= secondY) {
//            firstX += firstComponent.getWidth() / 2;
//            firstY += firstComponent.getHeight();
//            secondX += secondComponent.getWidth() / 2;
//        } else if (firstX <= secondX && firstY >= secondY) {
//            secondY += secondComponent.getHeight();
//            secondX += secondComponent.getWidth() / 2;
//            firstX += firstComponent.getWidth() / 2;
//        } else if (firstX >= secondX && firstY <= secondY) {
//            firstY += firstComponent.getHeight();
//            firstX += firstComponent.getWidth() / 2;
//            secondX += secondComponent.getWidth() / 2;
//        } else if (firstX >= secondX && firstY >= secondY) {
//            secondX += secondComponent.getWidth() / 2;
//            secondY += secondComponent.getHeight();
//            firstX += firstComponent.getWidth() / 2;
//        }
        cubicCurve.setStartX(firstX);
        cubicCurve.setStartY(firstY);
        cubicCurve.setEndX(secondX);
        cubicCurve.setEndY(secondY);
    }

    public double getSecondX() {
        return secondX - secondAddX;
    }

    public double getFirstX() {
        return firstX - firstAddX;
    }

    public boolean contain(Component component) {
        return firstComponent == component || secondComponent == component;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ComponentPair other = (ComponentPair) obj;
        if (!Objects.equals(this.firstComponent, other.firstComponent) &&
                !Objects.equals(this.firstComponent, other.secondComponent)) {
            return false;
        }
        if (!Objects.equals(this.secondComponent, other.secondComponent) &&
                !Objects.equals(this.secondComponent, other.firstComponent)) {
            return false;
        }

        return true;
    }


}
