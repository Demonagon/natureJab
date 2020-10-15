package graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class Utils {
    public static void paintCamemberg(GraphicsContext gc, double x, double y, double r, double portion) {
        gc.fillArc(x - r, y - r, r * 2, r * 2, 0, 360 * portion, ArcType.ROUND);
    }
}
