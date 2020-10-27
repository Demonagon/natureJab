package world.objects.tree;

import util.Task;

public class Bamboo {
    public static final double LEAF_SIZE = 5;
    public static final double LEAF_DISTANCE = 5;

    public static Task leafTask(Tree leaf) {
        return new Task.OrderedTasks(new Task[]{
                TreeStrategy.growSize(leaf, LEAF_SIZE),
                TreeStrategy.growDistance(leaf, LEAF_DISTANCE)
            }
        );
    }

    public static Tree leaf(Tree parent) {
        Tree leaf = new Tree();
        leaf.setGrowTask(leafTask(leaf));
        return leaf;
    }
}
