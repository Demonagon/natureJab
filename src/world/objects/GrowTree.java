package world.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import world.World;
import world.WorldObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GrowTree implements WorldObject {

    private final double x, y;
    private final Part root;

    public GrowTree(double x, double y) {
        this.x = x;
        this.y = y;
        root = new Bamboo(null);
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.save();
        gc.translate(x, y);
        root.paint(gc);
        gc.restore();
    }

    @Override
    public void setup(World world) {}

    @Override
    public void removal(World world) {}

    @Override
    public boolean prepareUpdate(World world) {
        return true;
    }

    @Override
    public void applyUpdate(World world) {
        root.grow(3);
    }


    protected static abstract class Part {

        protected final Part parent;
        private double angle;
        private double distance;
        private final List<Part> children;

        public Part() {
            parent = null;
            angle = 0;
            distance = 0;
            children = new ArrayList<>();
        }

        public Part(Part parent, double angle, double distance) {
            this.parent = parent;
            this.angle = angle;
            this.distance = distance;
            children = new ArrayList<>();
        }

        public List<Part> getChildren() {
            return children;
        }

        public void addChild(Part child) {
            children.add(child);
        }

        public double getX() {
            if( parent == null ) return 0;
            return parent.getX() + Math.cos(angle) * distance;
        }

        public double getY() {
            if( parent == null ) return 0;
            return parent.getY() + Math.sin(angle) * distance;
        }
        
        public double getDistance() {
            return distance;
        }

        public double getAngle() {
            return angle;
        }

        public void setAngle(double angle) {
            this.angle = angle;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public void tuneAngle(double value) {
            this.angle += angle;
        }

        public void tuneDistance(double value) {
            this.distance += value;
        }

        public abstract void paintBranch(GraphicsContext gc);

        public void paint(GraphicsContext gc) {
            gc.save();
            if(parent != null) gc.rotate(angle);

            paintBranch(gc);

            gc.translate(0, - distance);
            paintPart(gc);
            for(Part child : children)
                child.paint(gc);
            gc.restore();
        }

        protected abstract void paintPart(GraphicsContext gc);
        protected abstract double grow(double credits);
    }

    public static class Bamboo extends Part {

        public final static double MAX_SIZE = 20;
        public final static double MAX_DISTANCE = 80;

        protected double size;

        private Part nextPiece;
        private Part leaf;
        //private double distance;

        public Bamboo(Part parent) {
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
    }

    public static class Leaf extends Part {

        public final static double MAX_SIZE = 5;
        public final static double MAX_DISTANCE = 100;

        public double size;

        public Leaf(Part parent) {
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