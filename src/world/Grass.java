package world;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Grass implements PaintableObject {
    private int width = 1000;
    private int height = 1000;

    @Override
    public void paint(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.rgb(140, 191, 29));
        gc.fillRect(0, 0, width, height);
        gc.restore();
    }
}
