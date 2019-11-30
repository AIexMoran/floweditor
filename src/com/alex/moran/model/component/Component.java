
package com.alex.moran.model.component;

import com.alex.moran.model.component.state.ComponentState;
import com.alex.moran.model.component.state.CreateLineState;
import com.alex.moran.model.component.state.MoveState;
import java.util.Arrays;
import java.util.Objects;

import com.alex.moran.service.ButtonService;
import com.alex.moran.service.ComponentService;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class Component {

    protected String title;
    protected double width;
    protected double height;
    protected static int id = 0;
    protected double m_nX;
    protected double m_nY;
    protected double m_nMouseY;
    protected double m_nMouseX;
    protected static int show = 80;
    protected GridPane gridPane;
    private static ComponentState state = MoveState.getMoveState();
    private int countLines = 0;
    private int visibleCircles = 1;
    private int visibleUpCircles = 1;
    private int visibleDownCircles = 1;
    private ComponentCircle[] upCircles = new ComponentCircle[4];
    private ComponentCircle[] downCircles = new ComponentCircle[4];
    private static CreateLineState circleState = CreateLineState.getCreateLineState();

    public Component(double x, double y) {
        init();
        gridPane.setLayoutX(x);
        gridPane.setLayoutY(y);
    }

    public Component() {
        init();
    }

    public void init() {
        for (int i = 0; i < upCircles.length; i++) {
            upCircles[i] = new ComponentCircle(true, i, this);
            upCircles[i].getCircle().setVisible(false);
            upCircles[i].getCircle().setFill(Color.SALMON);
            upCircles[i].getCircle().setOnMouseReleased(event -> {
                circleState.onReleased(event, this);
                updateCircles();
            });
            upCircles[i].getCircle().setOnMouseDragged(event -> {
                circleState.onDrag(event, this);
            });
            ComponentCircle tmpComponentCircle = upCircles[i];
            upCircles[i].getCircle().setOnMousePressed(event -> {
                circleState.onPress(event, this, tmpComponentCircle);
            });
        }
        for (int i = 0; i < downCircles.length; i++) {
            downCircles[i] = new ComponentCircle(false, i, this);
            downCircles[i].getCircle().setVisible(false);
            downCircles[i].getCircle().setFill(Color.SALMON);
            downCircles[i].getCircle().setOnMouseReleased(event -> {
                circleState.onReleased(event, this);
                updateCircles();
            });
            downCircles[i].getCircle().setOnMouseDragged(event -> {
                circleState.onDrag(event, this);
            });
            ComponentCircle tmpComponentCircle = downCircles[i];
            downCircles[i].getCircle().setOnMousePressed(event -> {
                circleState.onPress(event, this, tmpComponentCircle);
            });
        }
        gridPane = new GridPane();
        gridPane.setOnMouseReleased(event -> {
            state.onReleased(event, this);
        });
        gridPane.setOnMouseDragged(event -> {
            state.onDrag(event, this);
            updateCircles();
        });
        gridPane.setOnMousePressed(event -> {
            state.onPress(event, this);
            updateCircles();
        });
    }

    public GridPane getGridPane() {
        return gridPane;
    }
    
    public ComponentCircle[] getUpComponentCircles() {
        return upCircles;
    }
    
    public ComponentCircle[] getDownComponentCircles() {
        return downCircles;
    }

    public Circle[] getUpCircles() {
        updateCircles();
        Circle[] circles = new Circle[upCircles.length];
        for (int i = 0; i < circles.length; i++) {
            circles[i] = upCircles[i].getCircle();
        }
        return circles;
    }

    public Circle[] getDownCircles() {
        updateCircles();
        Circle[] circles = new Circle[downCircles.length];
        for (int i = 0; i < circles.length; i++) {
            circles[i] = downCircles[i].getCircle();
        }
        return circles;
    }
    
    public void addCountUpLines() {
        
    }
    
    public void addCountDownLines() {
        
    }
    
    public void update() {
        visibleUpCircles = 1;
        visibleDownCircles = 1;
        for (int i = 0; i < upCircles.length - 1; i++) {
            if (upCircles[i].hasPair()) {
                visibleUpCircles++;
            }
        }        
        for (int i = 0; i < downCircles.length - 1; i++) {
            if (downCircles[i].hasPair()) {
                visibleDownCircles++;
            }
        }
        updateCircles();
    }

    public void addCountLines() {
        if (visibleCircles < 4) {
            visibleCircles++;
        }
        countLines++;
        updateCircles();
    }

    public boolean paneContainsPoint(double x, double y) {
        if (gridPane.getLayoutX() < x && x < gridPane.getLayoutX() + width
                && gridPane.getLayoutY() < y && y < gridPane.getLayoutY() + height) {
            return true;
        }
        return false;
    }

    public void substractCountLines() {
        countLines--;
        if (countLines < 3) {
            visibleCircles--;
        }
        update();
        updateCircles();
    }

    public int getCountLines() {
        return countLines;
    }

    public double getAddX(ComponentCircle componentCircle) {
        double addX = 0;
        addX = componentCircle.getCircle().getCenterX() - gridPane.getLayoutX();
        return addX;
    }
    
    public double getAddX() {
        double addX = 0;
        addX = upCircles[countLines % 4].getCircle().getCenterX() - gridPane.getLayoutX();
        return addX;
    }

    public void updateCircles() {
        double addX = width / 5;
        for (int i = 1; i < upCircles.length + 1; i++) {
            upCircles[i - 1].getCircle().setCenterX(gridPane.getLayoutX() + addX * i);
            downCircles[i - 1].getCircle().setCenterX(gridPane.getLayoutX() + addX * i);
            upCircles[i - 1].getCircle().setCenterY(gridPane.getLayoutY());
            downCircles[i - 1].getCircle().setCenterY(gridPane.getLayoutY() + height);
        }
        for (int i = 0; i < upCircles.length; i++) {
            upCircles[i].getCircle().setVisible(false);
        }
        for (int i = 0; i < downCircles.length; i++) {
            downCircles[i].getCircle().setVisible(false);
        }
        for (int i = 0; i < visibleUpCircles; i++) {
            upCircles[i].getCircle().setVisible(true);
        }
        for (int i = 0; i < visibleDownCircles; i++) {
            downCircles[i].getCircle().setVisible(true);
        }
    }

    public static void setState(ComponentState state) {
        Component.state = state;
    }

    public static ComponentState getState() {
        return state;
    }

    public double getM_nX() {
        return m_nX;
    }

    public void setM_nX(double m_nX) {
        this.m_nX = m_nX;
    }

    public double getM_nY() {
        return m_nY;
    }

    public void setM_nY(double m_nY) {
        this.m_nY = m_nY;
    }

    public double getM_nMouseY() {
        return m_nMouseY;
    }

    public void setM_nMouseY(double m_nMouseY) {
        this.m_nMouseY = m_nMouseY;
    }

    public double getM_nMouseX() {
        return m_nMouseX;
    }

    public void setM_nMouseX(double m_nMouseX) {
        this.m_nMouseX = m_nMouseX;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object ob) {
        if (this == ob) {
            return true;
        }
        if (ob == null) {
            return false;
        }
        if (getClass() != ob.getClass()) {
            return false;
        }
        Component component = (Component) ob;
        if (!title.equals(component.title)) {
            return false;
        }
        if (width != component.width) {
            return false;
        }
        if (height != component.height) {
            return false;
        }
        if (component.m_nX != m_nX) {
            return false;
        }
        if (component.m_nY != m_nX) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.width) ^ (Double.doubleToLongBits(this.width) >>> 32));
        hash = 53 * hash + (int) (Double.doubleToLongBits(this.height) ^ (Double.doubleToLongBits(this.height) >>> 32));
        hash = 53 * hash + Objects.hashCode(this.gridPane);
        hash = 53 * hash + this.countLines;
        hash = 53 * hash + this.visibleUpCircles;
        hash = 53 * hash + this.visibleDownCircles;
        hash = 53 * hash + Arrays.deepHashCode(this.upCircles);
        hash = 53 * hash + Arrays.deepHashCode(this.downCircles);
        return hash;
    }
    
    public ComponentCircle getComponentCircle(boolean up, int id) {
        if (up) {
            return upCircles[id];
        } else {
            return downCircles[id];
        }
    }
    
    public class ComponentCircle {
        
        private boolean up;
        private boolean pair;
        private Circle circle = new Circle(0, 0, 9);
        private int id;
        private Component component;
        
        public ComponentCircle(boolean up, int id, Component component) {
            this.up = up;
            this.id = id;
            this.component = component;
        }
        
        public int getId() {
            return id;
        }
        
        public Component getComponent() {
            return component;
        }
        
        public void setPair(boolean pair) {
            this.pair = pair;
        }
        
        public boolean hasPair() {
            return pair;
        }
        
        public boolean isUp() {
            return up;
        }
        
        public Circle getCircle() {
            return circle;
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
            final ComponentCircle other = (ComponentCircle) obj;
            if (this.up != other.up) {
                return false;
            }
            if (!Objects.equals(this.circle, other.circle)) {
                return false;
            }
            return true;
        }
        
    }

}
