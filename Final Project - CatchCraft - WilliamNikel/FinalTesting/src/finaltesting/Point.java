
package finaltesting;

/**
 *
 * @author william.nikel
 */
public class Point {
    // the coordinates of the point
    private double x;
    private double y;
    private double z;
    
    /**
     * Constructor based on received coords
     * @param xRec
     * @param yRec
     * @param zRec 
     */
    public Point(double xRec, double yRec, double zRec) {
        this.x = xRec;
        this.y = yRec;
        this.z = zRec;
    }
    
    // ACCESSOR METHODS
    
    public double x() {
        return this.x;
    }
    
    public double y() {
        return this.y;
    }
    
    public double z() {
        return this.z;
    }
    
    /**
     * 
     * @return a String rendition of this point 
     */
    @Override
    public String toString() {
        return ("(" + this.x() + ", " + this.y() + ", " + this.z() + ")");
    }
    
    /**
     * Compares this point to another and decides if they are the same
     * @param pt2 the other point
     * @return if they are the same
     */
    public boolean equals(Point pt2) {
        if ((this.x == pt2.x) && (this.y == pt2.y) && (this.z == pt2.z)) {
            return true;
        }
        
        return false;
    }
}
