package world.objects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import world.World;
import world.WorldObject;

public class GrowTree implements WorldObject {

    private double x, y;
    private double size;

    public GrowTree(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void paint(GraphicsContext gc) {
        gc.save();
        gc.setFill(Color.BROWN);
        gc.fillOval(x - size, y - size, size * 2, size * 2);
        gc.setFill(Color.BLACK);
        gc.strokeOval(x - size, y - size, size * 2, size * 2);
        gc.restore();
    }

    @Override
    public void setup(World world) {}

    @Override
    public void removal(World world) {}

    @Override
    public boolean prepareUpdate(World world) {
        return false;
    }

    @Override
    public void applyUpdate(World world) {
    }


    protected abstract class Part {

        private Part parent;
        private double angle;
        private double distance;

        public void Part() {
            parent = null;
            angle = 0;
            distance = 0;
        }

        public void Part(Part parent, double angle, double distance) {
            this.parent = parent;
            this.angle = angle;
            this.distance = distance;
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
            gc.translate(Math.cos(angle) * distance, Math.sin(angle) * distance);
        }

        protected abstract void paintPart(GraphicsContext gc);
        protected abstract double grow(double credits);
    }
}