package world.objects.tree;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.Task;
import world.World;

import java.util.Random;

public class Bamboo {
    public static final double LEAF_SIZE = 5;
    public static final double LEAF_DISTANCE = 100;
    public final static double TRUNK_SIZE = 20;
    public final static double TRUNK_DISTANCE = 80;

    public static Task leafTask(Tree leaf) {
        return new Task.OrderedTasks(new Task[]{
                TreeStrategy.growSize(leaf, LEAF_SIZE),
                TreeStrategy.growDistance(leaf, LEAF_DISTANCE)
            }
        );
    }

    public static Tree leaf(Tree parent) {
        Tree leaf = new Tree(parent);
        leaf.setGrowTask(leafTask(leaf));
        return leaf;
    }

    public static Task growLeafTask(Tree parent) {
        return new Task() {

            Tree leaf = null;

            @Override
            public double allocate(double credits) {
                if (leaf == null) {
                    leaf = leaf(parent);
                    parent.addChild(leaf, new Random().nextBoolean() ? 80 : -80);
                }

                return leaf.grow(credits);
            }

            @Override
            public boolean isOver() {
                if(leaf == null)
                    return false;
                return leaf.getGrowTask().isOver();
            }
        };
    }

    public static Task growTrunkTask(Tree parent) {
        return new Task() {

            Tree trunk = null;

            @Override
            public double allocate(double credits) {
                if (trunk == null) {
                    trunk = trunk(parent);
                    parent.addChild(trunk, new Random().nextBoolean() ? 5 : -5);
                }

                return trunk.grow(credits);
            }

            @Override
            public boolean isOver() {
                if(trunk == null)
                    return false;
                return trunk.getGrowTask().isOver();
            }
        };
    }

    public static Task trunkTask(Tree trunk) {
        return new Task.OrderedTasks(new Task[] {
                TreeStrategy.growSize(trunk, TRUNK_SIZE),
                trunk.getParent() == null ? new Task.EmptyTask() : TreeStrategy.growDistance(trunk, TRUNK_DISTANCE),
                new Task.ParallelTasks(new Task[]{
                        growLeafTask(trunk),
                        growTrunkTask(trunk)
                })
        });
    }

    public static Tree trunk(Tree parent) {
        Tree trunk = new Tree(parent);
        trunk.setGrowTask(trunkTask(trunk));
        return trunk;
    }

    public static class CanvasBambooDecorator extends CanvasTreeDecorator {

        GraphicsContext gc;

        public CanvasBambooDecorator(Canvas canvas) {
            super(canvas);
            gc = canvas.getGraphicsContext2D();
        }

        public void paintBranch(Tree tree) {
            if( tree.getParent() == null ) return;
            double xs[] = {-tree.getSize(), -tree.getSize(), tree.getSize(), tree.getSize()};
            double ys[] = {0, tree.getDistance(), tree.getDistance(), 0};
            gc.setFill(Color.BROWN);
            gc.fillPolygon(xs, ys, 4);
        }

        @Override
        public void paintObject(Tree tree) {
            gc.save();

            if(tree.getParent() != null)
                gc.translate(0, -tree.getDistance());

            paintBranch(tree);

            gc.setFill(Color.BROWN);
            gc.fillOval(-tree.getSize(), -tree.getSize(), tree.getSize() * 2, tree.getSize() * 2);
            gc.setFill(Color.BLACK);
            gc.strokeOval(-tree.getSize(), -tree.getSize(), tree.getSize() * 2, tree.getSize() * 2);

            for(Tree.Child child : tree.getChildren()) {
                gc.save();
                gc.rotate(child.angle);
                paintObject(child.tree);
                gc.restore();
            }


            gc.restore();
        }
    }
}
