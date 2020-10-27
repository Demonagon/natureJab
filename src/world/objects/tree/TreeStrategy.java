package world.objects.tree;

import util.Task;

public class TreeStrategy {
    public static Task growSize(Tree tree, double objective) {
        return new Task() {

            @Override
            public double allocate(double credits) {
                double growth = Math.min(credits, objective - tree.getSize());
                tree.tuneSize(growth);
                return credits - growth;
            }

            @Override
            public boolean isOver() {
                return tree.getSize() >= objective;
            }
        };
    }

    public static Task growDistance(Tree tree, double objective) {
        return new Task() {

            @Override
            public double allocate(double credits) {
                double growth = Math.min(credits, objective - tree.getDistance());
                tree.tuneDistance(growth);
                return credits - growth;
            }

            @Override
            public boolean isOver() {
                return tree.getDistance() >= objective;
            }
        };
    }
}
