
package finaltesting;

import java.awt.Graphics;
import java.util.*;

/**
 * Defines the "Scene" - this class is effectively my Rendering Engine. It takes in cubes and spits out 2D polygons
 * @author willi
 */

public class Scene {
    // yucky public variables, may change the structure later
    public ArrayList<Block> blocks = new ArrayList<>(); 
    
    public final Camera cam;
    
    /**
     * Constructs a scene. Integrates all the types of blocks sent into one big ArrayList for rendering
     * @param blocksRec
     * @param character
     * @param falling
     * @param camLoc 
     */
    public Scene(ArrayList<Block> blocksRec, MovableBlock character, ArrayList<MovableBlock> falling, Point camLoc) {
        for (Block b : blocksRec) {
            blocks.add(b);
        }
        
        for (MovableBlock b : falling) {
            blocks.add(b);
        }
        
        blocks.add(character);
        
        this.cam = new Camera(camLoc);
    }
    
    /**
     * Using the ArrayList of cubes, the camera, and the desired image dimensions, it produces a set of RenderablePolygons
     *      that can be drawn to a graphics object for a 2D conversion of a 3D scene
     * @param width image width
     * @param height image height
     * @return ArrayList of final products for drawing to Graphics
     */
    public RenderablePoly[] renderCubes(int width, int height) {
        
        // create array list to store all the polygons that will be drawn
        ArrayList<RenderablePoly> polysList = new ArrayList<>();
        
        // loop through blocks
        for (Block curBlock : this.blocks) {
            // loop through faces
            for (Face curFace : curBlock.faces) {
                
                // check if the current face is covered by another face
                if (curFace.covered()) {
                    // it is, so skip this face
                    continue;
                }
                
                // compare the normal
                // get a vector between a vertex and the camera
                Vector3 ptToCam = new Vector3(curFace.points[0], cam.camLoc());
                
                // check if the object is behind the camera
                if (cam.normal().dot(ptToCam) < 0) {
                    // check if oriented properly
                    if (ptToCam.dot(curFace.normal()) > 0) {
                        // visible to camera 
                        
                        // the x and y values of the polygon. Set to 0, will be replaced
                        int[] polyXVals = {0, 0, 0, 0};
                        int[] polyYVals = {0, 0, 0, 0};
                        
                        // for which coordinate of the polygon we're solving for
                        int index = 0;
                        
                        double distToFace = 0;
                                
                        // loop through points in the visible face
                        for (Point pt : curFace.points) {
                            // vector to the camera
                            Vector3 ray = new Vector3(pt, cam.camLoc());
                            // Point pt - current point
                            
                            // check where the intersection is
                            double t, xPos, yPos, zPos;
                            Vector3 norm = cam.normal();
                            
                            // solve for D (in scalar equation of a plane)
                            double D = 0 - (norm.x() * cam.origin().x()) - (norm.y() * cam.origin().y()) - (norm.z() * cam.origin().z());
                            
                            // solve for t for the parametrics
                            t = ((0 - D - (norm.x() * pt.x()) - (norm.y() * pt.y()) - (norm.z() * pt.z())) / ((norm.x() * ray.x()) + (norm.y() * ray.y()) + (norm.z() * ray.z())));
                            
                            // parametric equations
                            xPos = pt.x() + (t * ray.x());
                            yPos = pt.y() + (t * ray.y());
                            zPos = pt.z() + (t * ray.z());
                            
                            // create the intersection point
                            Point intersect = new Point(xPos, yPos, zPos);
                            
                            // DEBUGGING
                            //Point.printPoint(intersect);
                            
                            Vector3 ptToConvert = new Vector3(cam.origin(), intersect);
                            
                            // now convert to polygon coords
                            double xRawMag = ((ptToConvert.dot(cam.vecW())) / cam.vecW().magnitude());
                            double yRawMag = ((ptToConvert.dot(cam.vecH())) / cam.vecH().magnitude());
                            
                            // DEBUGGING 
                            //System.out.println("xRawMag: " + xRawMag);
                            //System.out.println("yRawMag: " + yRawMag);
                            
                            
                            // convert those to actual pixel coords
                            // first get the percentage of the screen its on
                            double xFactor = xRawMag / cam.vecW().magnitude();
                            double yFactor = yRawMag / cam.vecH().magnitude();
                            
                            // get the actual pixel
                            int xPixel = (int) (xFactor * width);
                            int yPixel = (int) (yFactor * height);
                            
                            // DEBUGGING
                            //System.out.println(xPixel);
                            //System.out.println(yPixel);
                            
                            // log the coords
                            polyXVals[index] = xPixel;
                            polyYVals[index] = yPixel;
                            
                            // iterate the index
                            index++;
                            
                            distToFace = new Vector3(this.cam.camLoc(), curFace.center()).magnitude();
                            
                        }
                        
                        // create the polygon
                        RenderablePoly drawableFace = new RenderablePoly(polyXVals, polyYVals, curFace.colour(), distToFace);
                        
                        // append to list
                        polysList.add(drawableFace);
                    }
                
                }
                   
            }
            
        }
        
        
        // convert the list to a simple array
        RenderablePoly[] returnArray = new RenderablePoly[polysList.size()];
        polysList.toArray(returnArray);
        
        returnArray = sortByDistance(returnArray);
        
        
        // return
        return returnArray; 
    }
    
    /**
     * Calculates which faces are adjacent to one another, and flags them so they won't be processed by RenderCubes
     * Is only called once per scene creation, since it has a lot of nested for loops and would degrade performance if run
     *      every frame
     */
    public void calculateAdjacents() {
        // maybe check for distance first 
        // actually could get the center point of the face, and then see if that single point equals another
        //      -> gets rid of the need to loop through the points 
        for (Block curBlock : this.blocks) {
            // cycle through all its faces
            for (Face currentFace : curBlock.faces) {
                boolean hasSameFace = false;
                
                // loop through all other blocks
                for (Block checkBlock : this.blocks) {
                    
                    // its the same block, continue to the next block
                    if (checkBlock.ID() == curBlock.ID()) {
                        continue;
                    }

                    // loop through its faces
                    for (Face checkFace : checkBlock.faces) {
                        
                        // now check if the face is the same
                        if (currentFace.isSameAs(checkFace)) {
                            currentFace.setCovered(true);
                            hasSameFace = true;
                        }
                    }
                    
                }
                
                // see if the face is exposed
                if (hasSameFace == false) {
                    // must be exposed on this cycle
                    currentFace.setCovered(false);
                }
            }
        }
        
    } // end of method
    
    /**
     * Sorts the array (may need to replace with a QuickSort algorithm, since this has to run frequently)
     * I used a SortSample from ICS3U for this method.
     * @param arr the array to be sorted
     * @return 
     */
    public RenderablePoly[] sortByDistance(RenderablePoly[] arr) {
         // stores the block with the maximum value
        
        for (int j = 0; j < arr.length; j++) {
            int maxIndex = 0;
            double maxValue = 1000000; // stores the max distance, set to an arbitrarily large number to start with
            RenderablePoly maxPoly = arr[0];
            
            for (int i = 0; i < arr.length - j; i++) {
                // get distance from camera to the center of the block
                
                
                if (arr[i].distToCam() <= maxValue) {
                    // store
                    maxValue = arr[i].distToCam();
                    
                    maxPoly = arr[i];
                    
                    maxIndex = i;
                }
                
            }
            
            // swap
            arr[maxIndex] = arr[arr.length - 1 - j];
            
            arr[arr.length - 1 - j] = maxPoly;
            
        }
        
        return arr;
    }
    
    /**
     * Draws the scene to a graphics object
     * @param g the graphics object being drawn to 
     */
    public void drawScene(Graphics g) {
        RenderablePoly[] polys = this.renderCubes(500, 500);
        
        for (RenderablePoly shape : polys) {
            g.setColor(shape.col());
            
            g.fillPolygon(shape.poly());
        }
    }

} // end of Scene class
