package world.objects.tree;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import world.World;
import world.WorldDecorator;
import world.WorldObject;
import world.objects.Grass;

public abstract class CanvasTreeDecorator implements WorldDecorator {
    public abstract void paintObject(Tree tree);

    protected Canvas canvas;

    public CanvasTreeDecorator(Canvas canvas) {
        this.canvas = canvas;
    }

    public void paint(WorldObject object) {
        if(object instanceof Grass) {
            canvas.getGraphicsContext2D().save();
            canvas.getGraphicsContext2D().setFill(Color.rgb(140, 191, 29));
            canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            canvas.getGraphicsContext2D().restore();
        }
        else if(object instanceof Tree) {
            canvas.getGraphicsContext2D().save();
            canvas.getGraphicsContext2D().translate(canvas.getWidth()/2, 7 * canvas.getHeight()/8);
            paintObject((Tree) object);
            canvas.getGraphicsContext2D().restore();
        }
        else throw new IllegalArgumentException("TreeDecorator can't paint non Tree objects");
    }

    @Override
    public void paint(World world) {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for(WorldObject object : world.getObjects())
            paint(object);
    }

    /*public void paint(TreeDecorator treeDecorator) {
        gc.save();
        if(parent != null) gc.rotate(angle);

        paintBranch(gc);

        gc.translate(0, - distance);
        paintPart(gc);
        for(Tree child : children)
            child.paint(gc);
        gc.restore();
    }*/
}
