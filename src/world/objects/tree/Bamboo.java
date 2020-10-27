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

    public static TreeGraphicalProfile bambooGraphicalProfile() {
        return new TreeGraphicalProfile(TreeGraphicalProfile.NodeStyle.SPHERE,
                                        TreeGraphicalProfile.Material.BAMBOO,
                                        TreeGraphicalProfile.BranchStyle.MINIMALRADIUS,
                                        TreeGraphicalProfile.Material.BAMBOO);
    }

    public static Tree leaf(Tree parent) {
        Tree leaf = new Tree(parent, bambooGraphicalProfile());
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
        Tree trunk = new Tree(parent, bambooGraphicalProfile());
        trunk.setGrowTask(trunkTask(trunk));
        return trunk;
    }
}
