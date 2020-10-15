package world.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import world.PaintableObject;
import world.World;
import world.WorldObject;

public class BasicTree implements WorldObject {

    private static final int MAX_AGE = 1000;

    private double x;
    private double y;
    private int age;

    public BasicTree(double x, double y) {
        this.x = x;
        this.y = y;
        this.age = 0;
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.BROWN.interpolate(Color.BLACK, age / (double) MAX_AGE));
        gc.fillOval(x - 20, y - 20, 40, 40);
        gc.restore();
    }

    @Override
    public boolean prepareUpdate(World world) {
        return true;
    }

    @Override
    public void applyUpdate(World world) {
        if( age >= MAX_AGE )
            world.removeObject(this);
        else age++;
    }
}
