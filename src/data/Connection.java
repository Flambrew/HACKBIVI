package src.data;

public class Connection {
    public final Location A;
    public final Location B;
    public final double DIST;

    public Connection(Location A, Location B) {
        this(A, B, Math.sqrt(Math.pow(B.POSX - A.POSX, 2) + Math.pow(B.POSY - A.POSY, 2)));
    }

    public Connection(Location A, Location B, double distance) {
        this.A = A;
        this.B = B;
        this.DIST = distance;
    }
}
