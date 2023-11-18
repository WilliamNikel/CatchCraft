
package finaltesting;

import java.awt.Color;

/**
 * Defines the face of a cube
 * @author william.nikel
 */
public class Face {
    // vectors defining the face
    private final Vector3 vecH;
    private final Vector3 vecW;
    private final Vector3 normal;
    
    // the vertices
    public final Point[] points; // DECIDE WHETHER TO KEEP PUBLIC OR MAKE AN ACCESSOR
    
    // the color
    private final Color colour;
    
    private boolean covered = false; // will be true if the face is covered by another face
    
    // the center
    private Point center;
    
    /**
     * Creates a new face
     * @param p1 bottom left
     * @param p2 top left
     * @param p3 top right
     * @param p4 bottom right
     * @param col the color of the face
     */
    public Face(Point p1, Point p2, Point p3, Point p4, Color col) {
        this.points = new Point[] {p1, p2, p3, p4};
        
        this.vecH = new Vector3(p1, p2);
        this.vecW = new Vector3(p1, p4);
        
        this.normal = vecW.cross(vecH);
        
        this.colour = col;
        
        this.center = new Point(
                (p1.x() + p2.x() + p3.x() + p4.x()) / 4, 
                (p1.y() + p2.y() + p3.y() + p4.y()) / 4,
                (p1.z() + p2.z() + p3.z() + p4.z()) / 4
        );
    }
    
    // ACCESSOR METHODS
    
    public Vector3 normal() {
        return this.normal;
    }
    
    public Color colour() {
        return this.colour;
    }
    
    public boolean covered() {
        return this.covered;
    }
    
    public Point center() {
        return this.center;
    }
    
    /**
     * Determines if this face is occupying the same space as another face
     * @param f the face we are comparing to
     * @return whether there is an overlap
     */
    public boolean isSameAs(Face f) {
        int count = 0;
        
        for (Point curPoint : this.points) {
            for (Point checkPoint : f.points) {
                if (curPoint.equals(checkPoint)) {
                    count++;
                }
            }
        }
        
        if (count == 4) {
            return true;
        }
        
        // effectively an else
        return false;
    }
    
    // MUTATORS
    
    /**
     * Sets whether the face is covered
     * @param isCovered 
     */
    public void setCovered(boolean isCovered) {
        this.covered = isCovered;
    }
     
}
