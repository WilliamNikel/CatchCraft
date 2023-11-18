
package finaltesting;

import java.lang.Math;

/**
 * Defines a 3-dimensional vector
 * @author william.nikel
 */
public class Vector3 {
    
    // the x, y, z components of the Vector
    private double x;
    private double y;
    private double z;
    
    // UNUSED - unit vectors
    static Vector3 i = new Vector3(0, 0, 0, 1, 0, 0);
    static Vector3 j = new Vector3(0, 0, 0, 0, 1, 0);
    static Vector3 k = new Vector3(0, 0, 0, 0, 0, 1);
    
    private double magnitude;
    
    // CONSTRUCTORS
    
    /**
     * Constructs a vector based on the x, y, z coordinates of the start and end points
     * @param initX the x-coordinate of the tail of the vector
     * @param initY the y-coordinate "
     * @param initZ the z-coordinate "
     * @param finalX the x-coordinate of the head of the vector
     * @param finalY the y-coordinate "
     * @param finalZ the z-coordinate "
     */
    public Vector3(double initX, double initY, double initZ, double finalX, double finalY, double finalZ) {
        this.x = finalX - initX;
        this.y = finalY - initY;
        this.z = finalZ - initZ;
        
        this.magnitude = this.calcMagnitude();
    }
    
    /**
     * Constructs a Vector based on an origin and final Point
     * @param init initial Point
     * @param end final Point
     */
    public Vector3(Point init, Point end) {
        this.x = end.x() - init.x();
        this.y = end.y() - init.y();
        this.z = end.z() - init.z();
        
        this.magnitude = this.calcMagnitude();
    }
    
    /**
     * Constructs a Vector based on received components
     * @param xRec x-component
     * @param yRec y-component
     * @param zRec z-component
     */
    public Vector3(double xRec, double yRec, double zRec) {
        this.x = xRec;
        this.y = yRec;
        this.z = zRec;
        
        this.magnitude = this.calcMagnitude();
    }
    
    // METHODS
    
    /**
     * Calculates the dot product of this Vector and another
     * @param vec2 the second vector
     * @return the numerical dot product
     */
    public double dot(Vector3 vec2) {
        return (this.x * vec2.x) + (this.y * vec2.y) + (this.z * vec2.z); 
    }
    
    /**
     * Calculates the cross product of this with another (this x another)
     * @param vec2 the second vector in the calculation
     * @return the cross product
     */
    public Vector3 cross(Vector3 vec2) {
        return new Vector3(
                (this.y * vec2.z - this.z * vec2.y),
                (this.z * vec2.x - this.x * vec2.z),
                (this.x * vec2.y - this.y * vec2.x)
        );
    }
    
    /**
     * Private method to calculate the magnitude of the Vector
     * @return the magnitude of the vector
     */
    private double calcMagnitude() {
        double xSq = this.x * this.x;
        double ySq = this.y * this.y;
        double zSq = this.z * this.z;
        
        return (Math.sqrt(xSq + ySq + zSq));
    }
    
    /**
     * Scales the vector by a scalar
     * @param scalar
     * @return the scaled vector (retains this as is)
     */
    public Vector3 scaleBy(double scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }
    
    // ACCESSORS
    
    public double x() {
        return this.x;
    }
    
    public double y() {
        return this.y;
    }
    
    public double z() {
        return this.z;
    }
    
    public double magnitude() {
        return this.magnitude;
    }
    
    // DEBUG METHOD
    public void printVector() {
        System.out.println("[" + this.x + ", " + this.y + ", " + this.z + "]");
    }
    
}