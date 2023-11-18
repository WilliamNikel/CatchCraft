
package finaltesting;

import java.awt.Color;

/**
 * Defines a block
 * @author william.nikel
 */
public class Block {
    
    // start at bottom left front corner, go around clockwise, then do the top in the same way
    // Block will extend along positive y-axis, and up positive z-axis
    public Point[] vertices;
    
    public final Face upFace; // +z                
    public final Face downFace; // -z
    public final Face northFace; // AWAY from camera (+y)
    public final Face eastFace; // +x
    public final Face southFace; // TOWARDS camera (-y)
    public final Face westFace; // -x
    
    public Face[] faces; // array to store the block's faces
    
    private static int numBlocks = 0; // counter of number of blocks in the world
    private final int ID; // gives each block a unique ID
    
    public Point center;
    
    public Point origin; 
    private double size;
    
    private Color upDownCol;
    private Color eastWestCol;
    private Color northSouthCol;
    
    /**
     * Constructs a new block from a corner position and the size
     * @param pos the position of the lower left vertex closest to the camera
     * @param size the width, height, and depth of the cube
     * @param UD
     * @param EW
     * @param NS
     */
    public Block(Point pos, double size, Color UD, Color EW, Color NS) {
        this.upDownCol = UD;
        this.eastWestCol = EW;
        this.northSouthCol = NS;
        
        this.origin = pos;
        this.size = size;
        // position will give the bottom left close vertex
        this.vertices = new Point[] {
            // bottom face
            pos,                                                        // 0
            new Point(pos.x(), pos.y() + size, pos.z()),                // 1
            new Point(pos.x() + size, pos.y() + size, pos.z()),         // 2
            new Point(pos.x() + size, pos.y(), pos.z()),                // 3
            
            // upper face
            new Point(pos.x(), pos.y(), pos.z() + size),                // 4
            new Point(pos.x(), pos.y() + size, pos.z() + size),         // 5
            new Point(pos.x() + size, pos.y() + size, pos.z() + size),  // 6
            new Point(pos.x() + size, pos.y(), pos.z() + size)          // 7
            
        };
        
        this.upFace = new Face(vertices[4], vertices[5], vertices[6], vertices[7], upDownCol); //good
        this.downFace = new Face(vertices[1], vertices[0], vertices[3], vertices[2], upDownCol); //good
        
        this.northFace = new Face(vertices[2], vertices[6], vertices[5], vertices[1], northSouthCol); // good
        this.southFace = new Face(vertices[0], vertices[4], vertices[7], vertices[3], northSouthCol); // good
        
        this.eastFace = new Face(vertices[3], vertices[7], vertices[6], vertices[2], eastWestCol); //good
        this.westFace = new Face(vertices[1], vertices[5], vertices[4], vertices[0], eastWestCol); //good
        
        // create array of faces
        // north, south, east, west, up, down
        this.faces = new Face[] {northFace, southFace, eastFace, westFace, upFace, downFace};
        
        numBlocks++;
        this.ID = numBlocks;
        
        // finds the center of the block by taking the average position of the x, y, and z coordinates
        this.center = new Point((int)((pos.x() + size) / 2), (int)((pos.y() + size) / 2), (int)((pos.z() + size) / 2));
    }
    
    // ACCESSOR METHODS
    //      named w/o the "get" prefix because I wanted them to feel more like attributes when I was writing the base
    //          engine code.
    
    public int ID() {
        return this.ID;
    }
    
    public Point center() {
        return this.center;
    }
    
    public Point origin() {
        return this.origin;
    }
    
    public double size() {
        return this.size;
    }
    
    // ACCESSOR METHODS -> properly named
    
    public Color getUD() {
        return this.upDownCol;
    }
    
    public Color getEW() {
        return this.eastWestCol;
    }
    
    public Color getNS() {
        return this.northSouthCol;
    }
}
