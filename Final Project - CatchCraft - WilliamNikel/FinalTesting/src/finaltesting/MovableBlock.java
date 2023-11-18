
package finaltesting;

import java.awt.Color;

/**
 * Defines a Block that can move
 * @author willi
 */
public class MovableBlock extends Block {
    // unique to a moveable block
    private double speed;
    private double LxBound, RxBound, UpYBound, LoYBound;
    private boolean character;
    
    /**
     * Constructor for MovableBlock
     * @param pos the origin of the Block
     * @param size size of the Block
     * @param spd speed of the MovableBlock
     * @param charac whether or not the MovableBlock is the character or not
     */
    public MovableBlock(Point pos, double size, double spd, boolean charac, Color UD, Color EW, Color NS) {
        super(pos, size, UD, EW, NS);
        
        this.character = charac;
        this.speed = spd;
        
        
        updateBounds();
    }
    
    /**
     * Update the bounds of the block for collision detection ..origin
     */
    private void updateBounds() {
        this.LxBound = this.origin().x();
        this.RxBound = this.origin().x() + this.size();
        
        this.UpYBound = this.origin().y() + this.size();
        this.LoYBound = this.origin().y();
        
    }
    
    /**
     * Moves the block by a specified amount
     * @param x
     * @param y
     * @param z 
     */
    public void move(double x, double y, double z) {
        
        // remakes the origin
        this.origin = new Point(this.origin.x() + x, this.origin.y() + y, this.origin.z() + z);
        
        // remakes the vertices
        for (int i = 0; i < this.vertices.length; i++) {
            this.vertices[i] = new Point(this.vertices[i].x() + x, this.vertices[i].y() + y, this.vertices[i].z() + z);
        }
        
        // remake the faces
        Face up = new Face(vertices[4], vertices[5], vertices[6], vertices[7], this.getUD()); //good
        Face down = new Face(vertices[1], vertices[0], vertices[3], vertices[2], this.getUD()); //good
        
        Face north = new Face(vertices[2], vertices[6], vertices[5], vertices[1], this.getNS()); // good
        Face south = new Face(vertices[0], vertices[4], vertices[7], vertices[3], this.getNS()); // good
        
        Face east = new Face(vertices[3], vertices[7], vertices[6], vertices[2], this.getEW()); //good
        Face west = new Face(vertices[1], vertices[5], vertices[4], vertices[0], this.getEW()); //good
        
        // create array of faces
        // north, south, east, west, up, down
        this.faces = new Face[] {north, south, east, west, up, down};
        
        // update the boundaries for collisons
        updateBounds();
        
    }
    
    /**
     * sets the block to a specific x, y, z location
     * @param x
     * @param y
     * @param z 
     */
    public void setLocation(double x, double y, double z) {
        double oldX, oldY, oldZ;
        
        this.origin = new Point(x, y, z);
        
        for (int i = 0; i < this.vertices.length; i++) {
            this.vertices = new Point[] {
                // bottom face
                this.origin,                                                        // 0
                new Point(this.origin.x(), this.origin.y() + this.size(), this.origin.z()),                // 1
                new Point(this.origin.x() + this.size(), this.origin.y() + this.size(), this.origin.z()),         // 2
                new Point(this.origin.x() + this.size(), this.origin.y(), this.origin.z()),                // 3

                // upper face
                new Point(this.origin.x(), this.origin.y(), this.origin.z() + this.size()),                // 4
                new Point(this.origin.x(), this.origin.y() + this.size(), this.origin.z() + this.size()),         // 5
                new Point(this.origin.x() + this.size(), this.origin.y() + size(), this.origin.z() + this.size()),  // 6
                new Point(this.origin.x() + this.size(), this.origin.y(), this.origin.z() + this.size())          // 7

            };
        }
        
        Face up = new Face(vertices[4], vertices[5], vertices[6], vertices[7], Color.GRAY); //good
        Face down = new Face(vertices[1], vertices[0], vertices[3], vertices[2], Color.BLACK); //good
        
        Face north = new Face(vertices[2], vertices[6], vertices[5], vertices[1], Color.PINK); // good
        Face south = new Face(vertices[0], vertices[4], vertices[7], vertices[3], Color.MAGENTA); // good
        
        Face east = new Face(vertices[3], vertices[7], vertices[6], vertices[2], Color.MAGENTA); //good
        Face west = new Face(vertices[1], vertices[5], vertices[4], vertices[0], Color.YELLOW); //good
        
        // create array of faces
        // north, south, east, west, up, down
        this.faces = new Face[] {north, south, east, west, up, down};
        
        updateBounds();
    }
    
    /**
     * 
     * @return whether this is a character or not
     */
    public boolean isCharacter() {
        return this.character;
    }
    
    /**
     * 
     * @return the speed of the block 
     */
    public double speed() {
        return this.speed;
    }
    
    // Accessors for the boundaries
    
    public double getLeftXBound() {
        return this.LxBound;
    }
    
    public double getRightXBound() {
        return this.RxBound;
    }
    
    public double getUpperYBound() {
        return this.UpYBound;
    }
    
    public double getLowerYBound() {
        return this.LoYBound;
    }
}
