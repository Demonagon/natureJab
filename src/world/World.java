package world;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    private final List<WorldObject> objects = new ArrayList<>();
    private final WorldDecorator decorator;

    private Random random = new Random();

    private WaterReagent waterReagent;

    public World(WorldDecorator decorator) {
        this.decorator = decorator;
        waterReagent = new WaterReagent();
    }

    public WorldDecorator getDecorator() {
        return decorator;
    }

    public Random getRandom() {
        return random;
    }

    public WaterReagent getWaterReagent() {
        return waterReagent;
    }

    public List<WorldObject> getObjects() {
        return objects;
    }

    public void addObject(WorldObject o) {
        objects.add(o);
        o.setup(this);
    }

    public void removeObject(WorldObject o) {
        o.removal(this);
        objects.remove(o);
    }

    public void update() {
        List<WorldObject> phaseTwoList = new ArrayList<>();

        for(WorldObject object : objects)
            if( object.prepareUpdate(this) )
                phaseTwoList.add(object);

        for(WorldObject object : phaseTwoList)
            object.applyUpdate(this);

        waterReagent.clear();
    }
}
