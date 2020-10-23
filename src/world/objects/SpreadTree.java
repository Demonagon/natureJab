package world.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import world.World;
import world.WorldObject;

public class SpreadTree implements WorldObject {

    private double x, y;
    private double size;
    private double angle;

    private boolean shouldReproduce;

    public SpreadTree(double x, double y, double size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.shouldReproduce = size > 1;
        this.angle = 0;
    }

    public SpreadTree(double x, double y, double size, double angle) {
        this(x, y, size);
        this.angle = angle;
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.BROWN);
        gc.fillOval(x - size, y - size, size * 2, size * 2);
        gc.setFill(Color.BLACK);
        gc.strokeOval(x - size, y - size, size * 2, size * 2);
        gc.restore();
    }

    @Override
    public void setup(World world) {}

    @Override
    public void removal(World world) {}

    @Override
    public boolean prepareUpdate(World world) {
        return shouldReproduce;
    }

    @Override
    public void applyUpdate(World world) {
        double newAngle = ( ( world.getRandom().nextInt() % 360 ) / 360.0 ) * Math.PI / 4 + this.angle;

        //System.out.println(newAngle);

        double newX = x + Math.cos(newAngle) * size;
        double newY = y + Math.sin(newAngle) * size;
        double newSize = size * 0.85;

        shouldReproduce = world.getRandom().nextBoolean() && world.getRandom().nextBoolean();

        world.addObject(new SpreadTree(newX, newY, newSize, newAngle));
    }
}
