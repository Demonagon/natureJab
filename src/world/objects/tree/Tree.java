package world.objects.tree;

import javafx.scene.canvas.GraphicsContext;
import util.Task;
import world.World;
import world.WorldObject;
import world.objects.GrowTree;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

// Version 2D.
public class Tree implements WorldObject {
    protected Tree parent;
    private TreeGraphicalProfile graphicalProfile;
    private double size;
    private double distance;
    private final List<Child> children;

    protected Task growTask;

    public Tree() {
        parent = null;
        graphicalProfile = null;
        size = 0;
        distance = 0;
        children = new ArrayList<>();
    }

    public Tree(Tree parent, TreeGraphicalProfile graphicalProfile) {
        this.parent = parent;
        this.graphicalProfile = graphicalProfile;
        size = 0;
        distance = 0;
        children = new ArrayList<>();
    }

    public Tree(Tree parent, TreeGraphicalProfile graphicalProfile, double size, double distance) {
        this.parent = parent;
        this.graphicalProfile = graphicalProfile;
        this.size = size;
        this.distance = distance;
        children = new ArrayList<>();
    }

    public TreeGraphicalProfile getGraphicalProfile() { return graphicalProfile; }

    public void setGrowTask(Task task) {
        this.growTask = task;
    }

    public Task getGrowTask() { return growTask; }

    public Tree getParent() {
        return parent;
    }

    public void setParent(Tree parent) { this.parent = parent; }

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

    protected double grow(double credits) {
        return growTask.allocate(credits);
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
        grow(1000);
    }

    public static class Child {
        public Child(Tree tree, double angle) {
            this.tree = tree;
            this.angle = angle;
        }

        public Tree tree;
        public double angle;
    }
}
