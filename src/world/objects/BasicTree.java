package world.objects;

import graphics.Utils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import world.PaintableObject;
import world.World;
import world.WorldObject;

public class BasicTree implements WorldObject {

    public static final double MAX_WATER = 100;
    public static final double UPKEEP_WATER_COST = 8;
    public static final double ROOT_RADIUS = 80;

    private double x;
    private double y;
    private double waterLevel;


    public BasicTree(double x, double y) {
        this.x = x;
        this.y = y;
        this.waterLevel = 50;
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.BROWN);
        gc.fillOval(x - 20, y - 20, 40, 40);
        gc.strokeOval(x - ROOT_RADIUS, y - ROOT_RADIUS, 2 * ROOT_RADIUS, 2 * ROOT_RADIUS);
        gc.setFill(Color.BLUE);
        Utils.paintCamemberg(gc, x, y, 18, (waterLevel / MAX_WATER));
        gc.restore();
    }

    @Override
    public void setup(World world) {
        world.getWaterReagent().addDemand(this, x, y, ROOT_RADIUS);
    }

    @Override
    public void removal(World world) {
        world.getWaterReagent().removeDemand(this);
    }

    @Override
    public boolean prepareUpdate(World world) {
        return true;
    }

    @Override
    public void applyUpdate(World world) {
        this.waterLevel += world.getWaterReagent().getWater(this);
        this.waterLevel -= BasicTree.UPKEEP_WATER_COST;
        if(waterLevel <= 0) world.removeObject(this);
        if(waterLevel > MAX_WATER) waterLevel = MAX_WATER;
    }
}
