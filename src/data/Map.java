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

    /**
     * add a new connection to the map
     * 
     * @param a location a
     * @param b location b
     * @return whether the connection could be added
     */
    public boolean addConnection(Location a, Location b) {
        if (a.overrides(b))
            return false;
        int at = 0; bt = 0;
        for (Location location : locations) {
            if (location.overrides(a))
                at++;
            if (location.overrides(b))
                bt++;
        }
        connections.add(new Connection(a, b))
        return true
    }

}
