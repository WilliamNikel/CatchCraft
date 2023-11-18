
package finaltesting;

public class Camera {
    // location of the "sensor"
    private Point cam;
    
    // location of the "lens"
    private Point[] imagePlanePts;
    
    // vectors defining the "lens"
    private Vector3 vecH;
    private Vector3 vecW;
    
    // normal vector to the lens
    private Vector3 planeNormal;
    
    // the size of the lens (actual width is doulbe this)
    double lensSize;
    
    
    /**
     * Constructor
     * @param location the location of the "sensor" of the camera 
     */
    public Camera(Point location) {
        this.cam = location;
        
        double yDist = 2;
        
        this.lensSize = 1;
        
        // camera facing down
        this.imagePlanePts = new Point[] {
            new Point(location.x() - this.lensSize, location.y() - this.lensSize, location.z() - yDist),
            new Point(location.x() - this.lensSize, location.y() + this.lensSize, location.z() - yDist),
            new Point(location.x() + this.lensSize, location.y() + this.lensSize, location.z() - yDist),
            new Point(location.x() + this.lensSize, location.y() - this.lensSize, location.z() - yDist)
        };
        
        // having a rectangular image plane is producing distortions
        // the x used to be +/- 1.5 to give a 4:3 aspect ratio
        
        // camera facing forward. If you want to see the effect, comment out the above, and uncomment this
//        this.imagePlanePts = new Point[] {
//            new Point(location.x() - this.lensSize, location.y() + yDist, location.z() - this.lensSize), // bottom left
//            new Point(location.x() - this.lensSize, location.y() + yDist, location.z() + this.lensSize), // top left ("origin")
//            new Point(location.x() + this.lensSize, location.y() + yDist, location.z() + this.lensSize), // top right
//            new Point(location.x() + this.lensSize, location.y() + yDist, location.z() - this.lensSize)  // bottom right
//        };
        
        // define the vectors  
        this.vecH = new Vector3(this.imagePlanePts[1], this.imagePlanePts[0]);
        this.vecW = new Vector3(this.imagePlanePts[1], this.imagePlanePts[2]);
        
        // define the normal
        this.planeNormal = this.vecW.cross(this.vecH);
    }
    
    // MOVEMENT methods
    
    /**
     * Moves the camera
     * @param xMove
     * @param yMove
     * @param zMove 
     */
    public void move(double xMove, double yMove, double zMove) {
        this.cam = new Point(this.cam.x() + xMove, this.cam.y() + yMove, this.cam.z() + zMove);
        
        // move the plane
        for (int i = 0; i < this.imagePlanePts.length; i++) {
            this.imagePlanePts[i] = new Point(
                imagePlanePts[i].x() + xMove,
                imagePlanePts[i].y() + yMove,
                imagePlanePts[i].z() + zMove
            );
        }
         
        
    }
    
    /**
     * UNUSED - moves the camera by a Vector
     * @param movement 
     */
    public void move(Vector3 movement) {
        // make camera and image plane move
        this.cam = new Point(this.cam.x() + movement.x(), this.cam.y() + movement.y(), this.cam.z() + movement.z());
        
        // move the plane
        for (Point pt : this.imagePlanePts) {
            pt = new Point(pt.x() + movement.x(), pt.y() + movement.y(), pt.z() + movement.z());
        }
    }
    
    
    
    // ACCESSORS
    
    /**
     * 
     * @return the location of the "sensor"
     */
    public Point camLoc() {
        return this.cam;
    }
    
    
    /**
     * 
     * @return height vector
     */
    public Vector3 vecH() {
        return this.vecH;
    }
    
    /**
     * 
     * @return width vector
     */
    public Vector3 vecW() {
        return this.vecW;
    }
    
    /**
     * 
     * @return normal vector
     */
    public Vector3 normal() {
        return this.planeNormal;
    }
    
    /**
     * 
     * @return one of the corners of the image plane 
     */
    public Point origin() {
        return this.imagePlanePts[1];
    }
    
    
}
