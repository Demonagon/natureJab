package world.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import world.PaintableObject;
import world.World;
import world.WorldObject;

public class Grass implements WorldObject {
    private int width = 1000;
    private int height = 1000;

    @Override
    public void paint(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.rgb(140, 191, 29));
        gc.fillRect(0, 0, width, height);
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
