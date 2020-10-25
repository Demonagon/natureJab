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

    public GrowTree(double x, double y, Part root) {
        this.x = x;
        this.y = y;
        this.root = root;
    }

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


}