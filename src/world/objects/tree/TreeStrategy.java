package world.objects.tree;

import util.Task;

import java.util.Random;

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

    public static Task switchingTask(Tree tree, Task task, TreeGrowChanger changer) {
        return new Task() {
            @Override
            public double allocate(double credits) {
                if(changer.isActive()) {
                    tree.setGrowTask(changer.newTask());
                    return tree.getGrowTask().allocate(credits);
                }

                return task.allocate(credits);
            }

            @Override
            public boolean isOver() {
                return (! changer.isActive()) && task.isOver();
            }
        };
    }

    /*public static Task changeTask(Tree tree, TreeGrowChanger changer) {
        return new Task() {
            public double allocate(double credits) {
                tree.setGrowTask(changer.newTask());
                return credits;
            }

            public boolean isOver() { return ! changer.isActive(); }
        };
    }*/
    
    public interface TreeGrowChanger {
        boolean isActive();
        Task newTask();
    }

    public static Task growChild(Tree parent, Tree newChild, double angle) {
        return new Task() {

            Tree child = null;

            @Override
            public double allocate(double credits) {
                if (child == null) {
                    child = newChild;
                    parent.addChild(child, angle);
                }

                return child.grow(credits);
            }

            @Override
            public boolean isOver() {
                if(child == null)
                    return false;
                return child.getGrowTask().isOver();
            }
        };
    }

    public static Task decoupleChild(Tree parent, Tree exChild) {
        return new Task() {

            boolean isDone = false;

            @Override
            public double allocate(double credits) {
                for(int k = 0; k < parent.getChildren().size(); k++)
                    if(parent.getChildren().get(k).tree == exChild) {
                        parent.getChildren().remove(k);
                        break;
                    }

                isDone = true;

                return credits;
            }

            @Override
            public boolean isOver() {
                return ! isDone;
            }
        };
    }
}
