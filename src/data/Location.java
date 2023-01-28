package src.data;

public class Location {
    public final String NAME;
    public final String ABBR;
    public final double POSX;
    public final double POSY;

    public Location(String name, double x, double y) {
        this(name, "", x, y);
    }

    public Location(String name, String abbreviation, double x, double y) {
        this.NAME = name;
        this.ABBR = abbreviation;
        this.POSX = x;
        this.POSY = y;   
    }
}
