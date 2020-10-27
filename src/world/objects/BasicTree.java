package world.objects;

import graphics.Utils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import world.World;
import world.WorldObject;

public class BasicTree implements WorldObject {

    public static final double MAX_GROWTH = 100;
    public static final double MAX_WATER = 100;
    public static final double FULL_UPKEEP_WATER_COST = 0.08;
    public static final double FULL_ROOT_RADIUS = 120;
    public static final double FULL_TRUNK_RADIUS = 40;
    public static final double GROWTH_WATER_THRESHOLD = 0.8;

    private double x;
    private double y;
    private double waterLevel;
    private double growth;


    public BasicTree(double x, double y) {
        this.x = x;
        this.y = y;
        this.waterLevel = 50;
        this.growth = 10;
    }

    public double growthRatio() {
        return growth / MAX_GROWTH;
    }

    public double trunkSize() {
        return growthRatio() * FULL_TRUNK_RADIUS;
    }

    public double rootSize() {
        return growthRatio() * FULL_ROOT_RADIUS;
    }

    public double upKeepCost() {
        return FULL_UPKEEP_WATER_COST * trunkSize();
    }

    //@Override
    public void paint(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.BROWN);
        gc.fillOval(x - trunkSize(), y - trunkSize(), 2 * trunkSize(), 2 * trunkSize());
        gc.strokeOval(x - rootSize(), y - rootSize(), 2 * rootSize(), 2 * rootSize());
        gc.setFill(Color.BLUE);
        Utils.paintCamemberg(gc, x, y, trunkSize() * 0.9, (waterLevel / MAX_WATER));
        gc.restore();
    }

    @Override
    public void setup(World world) {}

    @Override
    public void removal(World world) {
        world.getWaterReagent().removeDemand(this);
    }

    @Override
    public boolean prepareUpdate(World world) {
        world.getWaterReagent().addDemand(this, x, y, rootSize());
        return true;
    }

    @Override
    public void applyUpdate(World world) {
        this.waterLevel += world.getWaterReagent().getWater(this);
        this.waterLevel -= upKeepCost();
        if(waterLevel <= 0) {
            world.removeObject(this);
            return;
        }
        if(waterLevel > MAX_WATER) waterLevel = MAX_WATER;

        if(waterLevel >= MAX_WATER * GROWTH_WATER_THRESHOLD && growth < MAX_GROWTH) {
            double newGrowth = waterLevel * 0.01; // if water > 60%, take 3% of it for growth.
            waterLevel -= newGrowth;
            growth += newGrowth;
            if(growth > MAX_GROWTH) growth = MAX_GROWTH;
        }
    }
}
