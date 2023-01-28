package src.data;

import java.util.ArrayList;

public class Map {
    public final String NAME;
    private ArrayList<Location> locations;
    private ArrayList<Connection> connections;

    public Map(String name) {
        this.NAME = name;
    }

    public void addLocation(Location loc) {
        locations.add(loc);
    }

    public void addConnection(Location loc) {
        locations.add(loc);
    }

}
