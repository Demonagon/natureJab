package world.objects.tree;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import world.World;
import world.WorldDecorator;
import world.WorldObject;
import world.objects.Grass;

public class CanvasTreeDecorator implements WorldDecorator {

    protected Canvas canvas;
    protected GraphicsContext gc;

    public CanvasTreeDecorator(Canvas canvas) {
        this.canvas = canvas;
        gc = canvas.getGraphicsContext2D();
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

    public Color materialFillColor(TreeGraphicalProfile.Material material) {
        return switch(material) {
            case BAMBOO -> Color.BROWN;
            case BARK -> Color.SADDLEBROWN;
            case LEAF -> Color.FORESTGREEN;
        };
    }

    public Color materialStrokeColor(TreeGraphicalProfile.Material material) {
        return Color.BLACK;
    }

    public void fillBranch(Tree tree) {
        double xs[] = {0, 0, 0, 0};
        double ys[] = {0, 0, 0, 0};

        switch(tree.getGraphicalProfile().branchStyle) {
            case AIR:
                return;
            case COMPLETE:
                xs[0] = -tree.getParent().getSize();
                xs[1] = -tree.getSize();
                xs[2] = tree.getSize();
                xs[3] = tree.getParent().getSize();
                ys[1] = tree.getDistance();
                ys[2] = tree.getDistance();
                break;
            case MINIMALRADIUS:
                double min = Math.min(tree.getSize(), tree.getParent().getSize());
                xs[0] = -min;
                xs[1] = -min;
                xs[2] = min;
                xs[3] = min;
                ys[1] = tree.getDistance();
                ys[2] = tree.getDistance();
                break;
        }

        gc.setFill(materialFillColor(tree.getGraphicalProfile().branchMaterial));
        gc.fillPolygon(xs, ys, 4);

    }

    public void strokeBranch(Tree tree) {
        double xs[] = {0, 0, 0, 0};
        double ys[] = {0, 0, 0, 0};

        switch(tree.getGraphicalProfile().branchStyle) {
            case AIR:
                return;
            case COMPLETE:
                xs[0] = -tree.getParent().getSize();
                xs[1] = -tree.getSize();
                xs[2] = tree.getSize();
                xs[3] = tree.getParent().getSize();
                ys[1] = tree.getDistance();
                ys[2] = tree.getDistance();
                break;
            case MINIMALRADIUS:
                double min = Math.min(tree.getSize(), tree.getParent().getSize());
                xs[0] = -min;
                xs[1] = -min;
                xs[2] = min;
                xs[3] = min;
                ys[1] = tree.getDistance();
                ys[2] = tree.getDistance();
                break;
        }

        gc.setStroke(materialStrokeColor(tree.getGraphicalProfile().branchMaterial));
        gc.strokePolygon(xs, ys, 4);

    }

    public void paintBranch(Tree tree) {
        if(tree.getParent() == null)
            return;
        fillBranch(tree);
        strokeBranch(tree);
    }

    public void fillNode(Tree tree) {
        gc.setFill(materialFillColor(tree.getGraphicalProfile().nodeMaterial));
        switch(tree.getGraphicalProfile().nodeStyle) {
            case JOINT:
                return;
            case SPHERE:
                gc.fillOval(-tree.getSize(), -tree.getSize(), tree.getSize() * 2, tree.getSize() * 2);
        }
    }

    public void strokeNode(Tree tree) {
        gc.setStroke(materialStrokeColor(tree.getGraphicalProfile().nodeMaterial));
        switch(tree.getGraphicalProfile().nodeStyle) {
            case JOINT:
                return;
            case SPHERE:
                gc.strokeOval(-tree.getSize(), -tree.getSize(), tree.getSize() * 2, tree.getSize() * 2);
        }
    }

    public void paintNode(Tree tree) {
        fillNode(tree);
        strokeNode(tree);
    }

    public void paintObject(Tree tree) {
        gc.save();

        if(tree.getParent() != null)
            gc.translate(0, -tree.getDistance());

        paintBranch(tree);

        paintNode(tree);

        for(Tree.Child child : tree.getChildren()) {
            gc.save();
            gc.rotate(child.angle);
            paintObject(child.tree);
            gc.restore();
        }


        gc.restore();
    }
}
