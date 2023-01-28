package src.routing;

public class Location {
    private String name;
    private final double POSX;
    private final double POSY;

    public Location(String name, double x, double y) {
        this.name = name;
        this.POSX = x;
        this.POSY = y;
    }

    public String getName() {
        return name;
    }

    public double getPOSX() {
        return POSX;
    }

    public double getPOSY() {
        return POSY;
    }
}
