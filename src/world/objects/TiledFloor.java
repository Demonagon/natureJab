package world.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import world.World;
import world.WorldObject;

public class TiledFloor implements WorldObject {
    private int width = 1000;
    private int height = 1000;
    private double lineThickness = 1;
    private int lineSeparation = 100;

    @Override
    public void paint(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.rgb(100, 100, 100));
        gc.fillRect(0, 0, width, height);
        gc.setFill(Color.BLACK);
        for(int k = 0; k < width; k+= lineSeparation)
            gc.fillRect(k, 0, lineThickness, height);
        for(int k = 0; k < height; k+= lineSeparation)
            gc.fillRect(0, k, width, lineThickness);
        gc.restore();
    }

    @Override
    public void setup(World world) {}

    @Override
    public void removal(World world) {}

    @Override
    public boolean prepareUpdate(World world) {
        return false;
    }

    @Override
    public void applyUpdate(World world) {}
}
