package world.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Bamboo extends GrowTree.Part {

    public final static double MAX_SIZE = 20;
    public final static double MAX_DISTANCE = 80;

    protected double size;

    private GrowTree.Part nextPiece;
    private GrowTree.Part leaf;
    //private double distance;

    public Bamboo(GrowTree.Part parent) {
        super(parent, 0, 0);
        nextPiece = null;
        leaf = null;
    }

    public void paintBranch(GraphicsContext gc) {
        if( parent == null ) return;
        if(! (parent instanceof Bamboo) ) return;
        Bamboo bambooParent = (Bamboo) parent;
        double xs[] = {-bambooParent.size, -size, size, bambooParent.size};
        double ys[] = {0, -getDistance(), -getDistance(), 0};
        gc.setFill(Color.BROWN);
        gc.fillPolygon(xs, ys, 4);
    }

    @Override
    protected void paintPart(GraphicsContext gc) {
        gc.setFill(Color.BROWN);
        gc.fillOval(-size, -size, size * 2, size * 2);
        gc.setFill(Color.BLACK);
        gc.strokeOval(-size, -size, size * 2, size * 2);
    }

    private double growSize(double credits) {
        size += Math.min(MAX_SIZE - size, credits);
        return credits - Math.min(MAX_SIZE - size, credits);
    }

    private double growDistance(double credits) {
        tuneDistance(Math.min(MAX_DISTANCE - getDistance(), credits));
        return credits - Math.min(MAX_DISTANCE - getDistance(), credits);
    }

    private double growSelf(double credits) {
        return growDistance(growSize(credits));
    }

    private double growTrunk(double credits) {
        if(credits <= 0) return 0;

        if(nextPiece == null) {
            nextPiece = new Bamboo(this);
            addChild(nextPiece);
        }

        return nextPiece.grow(credits);
    }

    private double growLeaf(double credits) {
        if(credits <= 0) return 0;

        if(leaf == null) {
            leaf = new Leaf(this);
            addChild(leaf);
        }

        return leaf.grow(credits);
    }

    @Override
    protected double grow(double credits) {
        if( parent == null )
            return growTrunk(growSize(credits));
        credits = growSelf(credits);
        return growTrunk(credits / 2 + growLeaf(credits / 2));
    }

    public static class Leaf extends GrowTree.Part {

        public final static double MAX_SIZE = 5;
        public final static double MAX_DISTANCE = 100;

        public double size;

        public Leaf(GrowTree.Part parent) {
            super(parent, new Random().nextBoolean() ? 30 : -30, 0);
        }

        public void paintBranch(GraphicsContext gc) {
            if( parent == null ) return;
            if(! (parent instanceof Bamboo) ) return;
            Bamboo bambooParent = (Bamboo) parent;
            double xs[] = {-size, -size, size, size};
            double ys[] = {0, -getDistance(), -getDistance(), 0};
            gc.setFill(Color.BROWN);
            gc.fillPolygon(xs, ys, 4);
        }

        @Override
        protected void paintPart(GraphicsContext gc) {
            gc.setFill(Color.BROWN);
            gc.fillOval(-size, -size, size * 2, size * 2);
            gc.setFill(Color.BLACK);
            gc.strokeOval(-size, -size, size * 2, size * 2);
        }

        private double growSize(double credits) {
            size += Math.min(MAX_SIZE - size, credits);
            return credits - Math.min(MAX_SIZE - size, credits);
        }

        private double growDistance(double credits) {
            tuneDistance(Math.min(MAX_DISTANCE - getDistance(), credits));
            return credits - Math.min(MAX_DISTANCE - getDistance(), credits);
        }

        @Override
        protected double grow(double credits) {
            return growDistance(growSize(credits));
        }
    }
}