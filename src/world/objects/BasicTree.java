package world.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import world.PaintableObject;

public class BasicTree implements PaintableObject {

    private double x;
    private double y;

    public BasicTree(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.BROWN);
        gc.fillOval(x - 20, y - 20, 40, 40);
        gc.restore();
    }
}
