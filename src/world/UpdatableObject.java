package world;

public interface UpdatableObject {
    void setup(World world);
    void removal(World world);
    boolean prepareUpdate(World world);
    void applyUpdate(World world);
}
