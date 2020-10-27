package world.objects.tree;

public interface TreeDecorator {
    void paintObject(Tree tree);

    /*public void paint(TreeDecorator treeDecorator) {
        gc.save();
        if(parent != null) gc.rotate(angle);

        paintBranch(gc);

        gc.translate(0, - distance);
        paintPart(gc);
        for(Tree child : children)
            child.paint(gc);
        gc.restore();
    }*/
}
