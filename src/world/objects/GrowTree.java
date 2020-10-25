package world.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import world.World;
import world.WorldObject;

import java.util.ArrayList;
import java.util.List;

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
        root.grow(1);
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

        protected void paint(GraphicsContext gc) {
            gc.save();
            gc.rotate(angle);
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
        public final static double MAX_DISTANCE = 100;

        private double size;
        //private double distance;

        public Bamboo(Part parent) {
            super(parent, 0, 0);
        }

        @Override
        protected void paintPart(GraphicsContext gc) {
            gc.setFill(Color.BROWN);
            gc.fillOval(-size, -size, size * 2, size * 2);
            gc.setFill(Color.BLACK);
            gc.strokeOval(-size, -size, size * 2, size * 2);
        }

        @Override
        protected double grow(double credits) {
            if( getChildren().size() > 0 ) {
                return getChildren().get(0).grow(credits);
            }

            if ( size < MAX_SIZE ) {
                size += Math.min(MAX_SIZE - size, credits);
                credits -= Math.min(MAX_SIZE - size, credits);
            }

            if( size >= MAX_SIZE && parent == null ) {
                addChild(new Bamboo(this));
                return getChildren().get(0).grow(credits);
            }

            if ( credits > 0 && getDistance() < MAX_DISTANCE ) {
                tuneDistance(Math.min(MAX_DISTANCE - getDistance(), credits));
                credits -= Math.min(MAX_DISTANCE - getDistance(), credits);

                if(getDistance() >= MAX_DISTANCE) {
                    addChild(new Bamboo(this));
                    return getChildren().get(0).grow(credits);
                }
            }

            return credits;
        }
    }
}