package world.objects.tree;

import javafx.scene.canvas.GraphicsContext;
import world.objects.GrowTree;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

// Version 2D.
public abstract class Tree {
    protected final Tree parent;
    private double size;
    private double distance;
    private final List<Child> children;

    public Tree() {
            parent = null;
            size = 0;
            distance = 0;
            children = new ArrayList<>();
        }

    public Tree(Tree parent, double size, double distance) {
        this.parent = parent;
        this.size = size;
        this.distance = distance;
        children = new ArrayList<>();
    }

    public List<Child> getChildren() {
        return children;
    }

    public void addChild(Tree tree, double angle) {
        children.add(new Child(tree, angle));
    }

    public void addChild(Child child) {
        children.add(child);
    }

    public double getChildAngle(Tree tree) {
        for(Child child : children)
            if(child.tree == tree)
                return child.angle;
        throw new IllegalArgumentException("No child goes by the reference " + tree + " in tree " + this);
    }

    public double getAngle() {
        if( parent == null ) return 0;
        return parent.getAngle() + parent.getChildAngle(this);
    }

    public double getX() {
        if( parent == null ) return 0;
        return parent.getX() + Math.cos(getAngle()) * distance;
    }

    public double getY() {
        if( parent == null ) return 0;
        return parent.getY() + Math.sin(getAngle()) * distance;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public void tuneSize(double value) {
        this.size += value;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void tuneDistance(double value) {
        this.distance += value;
    }

    protected abstract double grow(double credits);

    protected static class Child {
        public Child(Tree tree, double angle) {
            this.tree = tree;
            this.angle = angle;
        }

        public Tree tree;
        public double angle;
    }
}
