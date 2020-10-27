package world.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import world.World;
import world.WorldObject;

public class Grass implements WorldObject {
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
