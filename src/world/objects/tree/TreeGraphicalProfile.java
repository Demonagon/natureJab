package world.objects.tree;

public class TreeGraphicalProfile {
    public enum NodeStyle {
        SPHERE,
        JOINT
    }

    public enum BranchStyle {
        AIR,
        COMPLETE,
        MINIMALRADIUS,
        POINTY
    }

    public enum Material {
        BAMBOO,
        BARK,
        LEAF
    }

    public NodeStyle nodeStyle;
    public BranchStyle branchStyle;
    public Material nodeMaterial;
    public Material branchMaterial;

    public TreeGraphicalProfile(NodeStyle nodeStyle, Material nodeMaterial, BranchStyle branchStyle, Material branchMaterial) {
        this.nodeStyle = nodeStyle;
        this.branchStyle = branchStyle;
        this.nodeMaterial = nodeMaterial;
        this.branchMaterial = branchMaterial;
    }
}
