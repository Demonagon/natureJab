package world;

public interface UpdatableObject {
    boolean prepareUpdate(World world);
    void applyUpdate(World world);
}
