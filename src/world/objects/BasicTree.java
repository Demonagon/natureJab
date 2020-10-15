package world.objects;

import graphics.Utils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import world.PaintableObject;
import world.World;
import world.WorldObject;

public class BasicTree implements WorldObject {

    public static final int MAX_WATER = 100;

    private double x;
    private double y;
    private int waterLevel;


    public BasicTree(double x, double y) {
        this.x = x;
        this.y = y;
        this.waterLevel = 0;
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.BROWN);
        gc.fillOval(x - 20, y - 20, 40, 40);
        gc.setFill(Color.BLUE);
        Utils.paintCamemberg(gc, x, y, 18, (waterLevel / (double) MAX_WATER));
        //gc.fillRect(x - 5 , y + 10  - 20 * (waterLevel / (double) MAX_WATER), 10, 20 * (waterLevel / (double) MAX_WATER));
        gc.restore();
    }

    @Override
    public boolean prepareUpdate(World world) {
        return true;
    }

    @Override
    public void applyUpdate(World world) {
        if ( waterLevel >= 80 )
            waterLevel -= 80;
        waterLevel++;
    }
}
