package world;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;

public class World {
    private List<WorldObject> objects = new ArrayList<>();

    public void addObject(WorldObject o) {
        objects.add(o);
    }

    public void removeObject(WorldObject o) {
        objects.remove(o);
    }

    public void paintWorld(Canvas worldCanvas) {
        worldCanvas.getGraphicsContext2D().clearRect(0, 0, worldCanvas.getWidth(), worldCanvas.getHeight());
        for(PaintableObject object : objects)
            object.paint(worldCanvas.getGraphicsContext2D());
    }

    public void update() {
        List<WorldObject> phaseTwoList = new ArrayList<>();

        for(WorldObject object : objects)
            if( object.prepareUpdate(this) )
                phaseTwoList.add(object);

        for(WorldObject object : phaseTwoList)
            object.applyUpdate(this);

    }
}
