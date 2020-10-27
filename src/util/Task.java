package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Task {

    double allocate(double credits);
    boolean isOver();

    // Prioritize in order
    class OrderedTasks implements Task {

        List<Task> tasks;

        public OrderedTasks(List<Task> tasks) {
            this.tasks = tasks;
        }

        public OrderedTasks(Task[] array) {
            tasks = new ArrayList<>(Arrays.asList(array));
        }

        @Override
        public double allocate(double credits) {
            for(Task task : tasks) {
                if(task.isOver()) continue;
                credits = task.allocate(credits);
                if(credits <= 0) return credits;
            }
            return credits;
        }

        @Override
        public boolean isOver() {
            for(Task task : tasks)
                if( ! task.isOver() ) return false;
            return true;
        }
    }

    // Prioritize all at once
    class ParallelTasks implements Task {

        List<Task> tasks;

        public ParallelTasks(List<Task> tasks) {
            this.tasks = tasks;
        }

        public ParallelTasks(Task[] array) {
            tasks = new ArrayList<>(Arrays.asList(array));
        }

        @Override
        public double allocate(double credits) {
            int openTasks = 0;
            double leftovers = 0;

            for(Task task : tasks)
                if(! task.isOver()) openTasks++;

            for(Task task : tasks) {
                if(task.isOver()) continue;
                leftovers += task.allocate(credits / openTasks);
            }

            return leftovers;
        }

        @Override
        public boolean isOver() {
            for(Task task : tasks)
                if( ! task.isOver() ) return false;
            return true;
        }
    }
}
