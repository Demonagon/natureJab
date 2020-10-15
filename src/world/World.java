package world;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;

public class World {
    private List<PaintableObject> objects = new ArrayList<>();

    public void addObject(PaintableObject o) {
        objects.add(o);
    }

    public void removeObject(PaintableObject o) {
        objects.remove(o);
    }

    public void paintWorld(Canvas worldCanvas) {
        worldCanvas.getGraphicsContext2D().clearRect(0, 0, worldCanvas.getWidth(), worldCanvas.getHeight());
        for(PaintableObject object : objects)
            object.paint(worldCanvas.getGraphicsContext2D());
    }
}
