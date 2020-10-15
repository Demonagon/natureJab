package world;

import java.util.ArrayList;
import java.util.List;

public class WaterReagent {

    private List<Demand> demands = new ArrayList<>();

    public void addDemand(Object o, double x, double y, double r) {
        demands.add(new Demand(o, x, y ,r));
    }

    public void removeDemand(Object o) {
        List<Demand> removals = new ArrayList<>();
        for(Demand d : demands)
            if(d.reference == o)
                removals.add(d);
        demands.removeAll(removals);
    }

    public void clear() {
        demands.clear();
    }

    public double getWater(Object o) {
        double water = 0;
        for(Demand d : demands)
            if( d.reference == o )
                water += demandGetWater(d);
        return water;
    }

    private double demandGetWater(Demand demand) {
        double offer = 0.05 * demand.radius;

        double competition = 0;

        for(Demand d : demands) {
            if (d == demand) continue;
            if (d.isNextTo(demand))
                competition += d.radius;
        }

        return (offer * demand.radius) / (demand.radius + competition);
    }

    private class Demand {
        public Object reference;
        public double x;
        public double y;
        public double radius;

        public Demand(Object o, double x, double y, double radius) {
            reference = o;
            this.x = x;
            this.y = y;
            this.radius = radius;
        }

        public boolean isNextTo(Demand d) {
            return (x - d.x) * (x - d.x) + (y - d.y) * (y - d.y) <= (radius + d.radius) * (radius + d.radius);
        }

    }
}
