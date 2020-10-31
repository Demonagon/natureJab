package world.objects.tree;

import util.Task;

public class AppleTree {

    public static double SPREAD_THRESHOLD = 20;
    public static double SIZE_LIMIT_FACTOR = 0.80;
    public static double SIZE_TO_DISTANCE_RATIO = 3;
    public static double LEAF_SIZE = 100;

    public static Task maxUpSizeTask(Tree node) {
        return new Task() {

            @Override
            public double allocate(double credits) {
                double growth = Math.min(credits, node.getParent().getSize() * SIZE_LIMIT_FACTOR - node.getSize());
                node.tuneSize(growth);
                return credits - growth;
            }

            @Override
            public boolean isOver() {
                return node.getSize() >= node.getParent().getSize() * SIZE_LIMIT_FACTOR;
            }
        };
    }

    public static Task maxUpDistanceTask(Tree node) {
        return new Task() {

            @Override
            public double allocate(double credits) {
                double growth = Math.min(credits, node.getParent().getSize() * SIZE_TO_DISTANCE_RATIO - node.getDistance());
                node.tuneDistance(growth);
                return credits - growth;
            }

            @Override
            public boolean isOver() {
                return node.getDistance() >= node.getParent().getSize() * SIZE_TO_DISTANCE_RATIO;
            }
        };
    }

    public static Task leafTask(Tree leaf) {
        return new Task.ParallelTasks(new Task[]{
                TreeStrategy.growSize(leaf, LEAF_SIZE),
                maxUpDistanceTask(leaf)
        });
    }

    public static TreeGraphicalProfile leafGraphicalProfile() {
        return new TreeGraphicalProfile(TreeGraphicalProfile.NodeStyle.SPHERE,
                TreeGraphicalProfile.Material.LEAF,
                TreeGraphicalProfile.BranchStyle.POINTY,
                TreeGraphicalProfile.Material.BARK);
    }

    public static Tree appleTreeLeaf(Tree parent) {
        Tree leaf = new Tree(parent, leafGraphicalProfile());
        leaf.setGrowTask(leafTask(leaf));
        return leaf;
    }

    public static Task initialBodyTask(Tree body, Tree leaf) {
        return TreeStrategy.switchingTask(body,
            new Task.ParallelTasks(new Task[] {
                maxUpDistanceTask(body),
                maxUpSizeTask(body),
                TreeStrategy.growChild(body, leaf, 0)
            }),
            new TreeStrategy.TreeGrowChanger() {
               @Override
               public boolean isActive() {
                   return body.getSize() >= SPREAD_THRESHOLD;
               }

               @Override
               public Task newTask() {
                   return finalBodyTask(body, leaf);
               }
        });
    }

    public static Task finalBodyTask(Tree body, Tree leaf) {
        return new Task.FirstThenTask(TreeStrategy.decoupleChild(body, leaf),
                new Task.ParallelTasks(new Task[] {
                        maxUpDistanceTask(body),
                        maxUpSizeTask(body),
                        TreeStrategy.growChild(body, appleTreeBodyWithLeaf(body), -25),
                        TreeStrategy.growChild(body, appleTreeBody(body, leaf), 0),
                        TreeStrategy.growChild(body, appleTreeBodyWithLeaf(body), 25)
                })
        );
    }

    public static TreeGraphicalProfile bodyGraphicalProfile() {
        return new TreeGraphicalProfile(TreeGraphicalProfile.NodeStyle.SPHERE,
                TreeGraphicalProfile.Material.BARK,
                TreeGraphicalProfile.BranchStyle.COMPLETE,
                TreeGraphicalProfile.Material.BARK);
    }

    public static Tree appleTreeBody(Tree parent, Tree leaf) {
        Tree body = new Tree(parent, bodyGraphicalProfile());
        body.setGrowTask(initialBodyTask(body, leaf));
        return body;
    }

    public static Tree appleTreeBodyWithLeaf(Tree parent) {
        Tree body = new Tree(parent, bodyGraphicalProfile());
        Tree leaf = appleTreeLeaf(body);
        body.setGrowTask(initialBodyTask(body, leaf));
        return body;
    }

    public static Tree appleTreeBase(double size) {
        Tree tree = new Tree(null, bodyGraphicalProfile());
        tree.setSize(size);
        tree.setGrowTask(TreeStrategy.growChild(tree, appleTreeBodyWithLeaf(tree), 0));
        return tree;
    }
}
