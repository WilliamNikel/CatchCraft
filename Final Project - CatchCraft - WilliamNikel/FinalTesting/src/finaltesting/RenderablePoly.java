
package finaltesting;

import java.awt.Color;
import java.awt.Polygon;

/**
 * This class combines the shape of a Polygon with a Color for rendering
 * @author william.nikel
 */
public class RenderablePoly {
    // characteristics
    private Color colour;
    private Polygon poly;
    private double distanceToCam;
    
    
    /**
     * Constructs a polygon with color built-in
     * @param xCoords the array of x-coordinates used by Polygon
     * @param yCoords the array of y-coordinates used by Polygon
     * @param col the color of the shape
     */
    public RenderablePoly(int[] xCoords, int[] yCoords, Color col, double dist) {
        this.colour = col;
        
        this.poly = new Polygon(xCoords, yCoords, 4);
        
        this.distanceToCam = dist;
    }
    
    // ACCESSOR METHODS
    
    public Polygon poly() {
        return this.poly;
    }
    
    public Color col() {
        return this.colour;
    }
    
    public double distToCam() {
        return this.distanceToCam;  
    }
}
