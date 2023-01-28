package src.routing;

import java.util.ArrayList;

public class Map {
    private String NAME;
    private ArrayList<String> names;
    private double[][] matrix;

    public Map(String name, Location[] locations, Connection[] connections) {
        this.NAME = name;
        this.matrix = new double[locations.length][locations.length];
        this.names = new ArrayList<String>();

        for (Location location : locations)
            names.add(location.getName());

        for (Connection connection : connections) {
            int a = names.indexOf(connection.getA());
            int b = names.indexOf(connection.getB());
            matrix[a][b] = Math.sqrt(Math.pow(locations[b].getPOSX() - locations[a].getPOSX(), 2)
                    + Math.pow(locations[b].getPOSY() - locations[a].getPOSY(), 2));
            matrix[b][a] = Math.sqrt(Math.pow(locations[b].getPOSX() - locations[a].getPOSX(), 2)
                    + Math.pow(locations[b].getPOSY() - locations[a].getPOSY(), 2));
        }
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public double[][] getMatrix() {
        return matrix;
    }
}