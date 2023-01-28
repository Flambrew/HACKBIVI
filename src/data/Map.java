package src.data;

import java.util.ArrayList;

public class Map {
    public final String NAME;
    private ArrayList<Location> locations;
    private ArrayList<Connection> connections;

    public Map(String name) {
        this.NAME = name;
    }

    /**
     * add a new location to the map
     * 
     * @param loc location to add
     * @return whether the location overrode another
     */
    public boolean addLocation(Location loc) {
        for (Location location : locations)
            if (loc.overrides(location)) {
                locations.remove(location);
                locations.add(loc);
                return true;
            }
        locations.add(loc);
        return false;
    }

    public Location getLocations(Location loc) {
        return locations.indexOf(loc) < 0 ? null : locations.get(locations.indexOf(loc));
    }

    public boolean addConnection(Location a, Location b) {
        int go = 0;
        for (Location location : locations) {
            if (a.overrides(b))
        }
        connections.add(new Connection(a, b))
    }

}
