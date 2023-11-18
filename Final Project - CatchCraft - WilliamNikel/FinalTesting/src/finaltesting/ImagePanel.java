
package finaltesting;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

/**
 * The JPanel in which the game exists
 * @author willi
 */
public class ImagePanel extends JPanel implements ActionListener {
    // stores the Scene object where all the rendering takes place
    private Scene theScene;
    
    // timer to control the game flow
    private Timer gameTickTimer;
    
    // timer to control when the game ends
    private Timer endTimer;
    
    // storage of the blocks in the world
    private ArrayList<MovableBlock> fallingBlocks = new ArrayList<>(); // blocks that fall, player wants to avoid
    private MovableBlock characterBlock; // the player
    private ArrayList<Block> backgroundBlocks = new ArrayList<>(); // the static blocks making up the background
    
    // the location of the camera within the world
    private Point cameraLocation;
    
    // limits for player motion (also used to create the world)
    private final double LEFT_LIMIT = -3;
    private final double RIGHT_LIMIT = 3;
    private final double FRONT_LIMIT = 3;
    private final double BACK_LIMIT = 6;
    
    // constants for falling block spawning
    private final int NUM_BLOCKS = 5;
    private final int ROOM_HEIGHT = 5;
    
    // constant for how long the game lasts
    private final int GAME_LENGTH;
    private int timeElapsed; // how long has gone by
    
    // variable to store your score
    private int score = 0;
    
    // whether the game is running or not
    private boolean running = true;
    
    // variables for writing to the score file
    private final String FILE_NAME = "scores.txt";
    
    /**
     * Constructs an image object
     */
    public ImagePanel(int timeAllowed) {
        // assign constant
        this.GAME_LENGTH = timeAllowed;
        
        // point for where the camera is located
        this.cameraLocation = new Point(0.0, 4.5, 10.0);
        
        // create the user-controller block
        this.characterBlock = new MovableBlock(new Point(0, 3, -0.85), 0.6, 0.1, true, new Color(36, 69, 151), new Color(27, 51, 102), new Color(28, 54, 105));

        // create the falling blocks in their initial locations
        createFallingBlocks();
        
        // create the "room"
        fillBackground();
        
        // add the blocks to the scene for future rendering
        this.theScene = new Scene(backgroundBlocks, characterBlock, fallingBlocks, cameraLocation);
        
        // create listener 
        KeyboardListener keyListener = new KeyboardListener();
        
        setFocusable(true);
        
        // add listener
        addKeyListener(keyListener);
        
        // start the timer that controls the motion of the falling blocks
        gameTickTimer = new Timer(16, this); // approx 60 times per second
        gameTickTimer.start();
        
        // start the timer that controls when the game ends
        endTimer = new Timer(1000, this); // every 1 second
        endTimer.start();
        
        // determine if there are faces that are effectively the same
        this.theScene.calculateAdjacents();
    }
    
    /**
     * Fills in the background
     */
    private void fillBackground() {
        // constants for block spawning
        final int ROOM_WIDTH = (int)RIGHT_LIMIT;
        final int ROOM_DEPTH = (int)BACK_LIMIT;
        final double FLOOR_Z = -2;
        final double BLOCK_SIZE = 1;
        final int INIT_Y = 3;
        
        // create the colours
        final Color top = new Color(177, 138, 78);
        final Color leftRight = new Color(110, 86, 49);
        final Color upDown = new Color(145, 113, 64);
        
        // floor
        for (int row = -1 * ROOM_WIDTH; row < ROOM_WIDTH; row++) {
            for (int col = 0; col < ROOM_DEPTH; col++) {
                backgroundBlocks.add(new Block(new Point(row * BLOCK_SIZE, col * BLOCK_SIZE + INIT_Y, FLOOR_Z), BLOCK_SIZE, top, leftRight, upDown));
            }
        }
        
        // back wall
        for (int row = -1 * (ROOM_WIDTH + 1); row < ROOM_WIDTH + 1; row++) {
            for (int col = (int)FLOOR_Z; col < ROOM_HEIGHT; col++) {
                backgroundBlocks.add(new Block(new Point(row * BLOCK_SIZE, ROOM_DEPTH + 1, col * BLOCK_SIZE), BLOCK_SIZE, top, leftRight, upDown));
            }
        }
        
        // left side wall
        for (int col = INIT_Y; col < ROOM_DEPTH + 1; col++) {
            for (int h = (int)FLOOR_Z; h < ROOM_HEIGHT; h++) {
                backgroundBlocks.add(new Block(new Point((-1 * ROOM_WIDTH) - 1, col * BLOCK_SIZE, h * BLOCK_SIZE), BLOCK_SIZE, top, leftRight, upDown));
            }
        }
        
        // right side wall
        for (int col = INIT_Y; col < ROOM_DEPTH + 1; col++) {
            for (int h = (int)FLOOR_Z; h < ROOM_HEIGHT; h++) {
                backgroundBlocks.add(new Block(new Point(ROOM_WIDTH, col * BLOCK_SIZE, h * BLOCK_SIZE), BLOCK_SIZE, top, leftRight, upDown));
            }
        }
        
        
        // front wall
        for (int row = -1 * (ROOM_WIDTH + 1); row < ROOM_WIDTH + 1; row++) {
            for (int col = (int)FLOOR_Z; col < ROOM_HEIGHT; col++) {
                backgroundBlocks.add(new Block(new Point(row * BLOCK_SIZE, INIT_Y - 1, col * BLOCK_SIZE), BLOCK_SIZE, top, leftRight, upDown));
            }
        }
    }
    
    /**
     * Creates the blocks that will fall throughout the game
     */
    private void createFallingBlocks() {
        Random randomizer = new Random();
        
        // create the colours for the faces of the blocks
        final Color top = new Color(231, 66, 24);
        final Color leftRight = new Color(179, 38, 21);
        final Color upDown = new Color(134, 29, 16);
        
        // create the blocks based on random x, y coords
        for (int i = 0; i < NUM_BLOCKS; i++) {
            
            double randX = randomizer.nextDouble((RIGHT_LIMIT - 2) - LEFT_LIMIT + 1) + LEFT_LIMIT;
            double randY = randomizer.nextDouble((BACK_LIMIT - 1.5) - (FRONT_LIMIT - 0.5) + 1) + (FRONT_LIMIT+0.5);
            
            this.fallingBlocks.add(new MovableBlock(new Point(randX, randY, ROOM_HEIGHT + randX), 0.25, 0.05, false, top, leftRight, upDown));
            
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        
        // render the scene
        this.theScene.drawScene(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gameTickTimer) {
            // called approx 60x per second
            updateGame(); 
        }
        
        // tick up the time count for how long the player has been playing
        if (e.getSource() == endTimer) {
            timeElapsed++;
        }
    }
    
    /**
     * Handles updating the game every tick
     */
    private void updateGame() {
        // update the game
        for (MovableBlock b : fallingBlocks) {
            b.move(0, 0, b.speed() * -1);
        }
        
        
        // check if anything has gone through the floor
        checkFallen();
        
        // check for collisions
        checkCollisions();
        
        // check if the time is up 
        if (timeElapsed >= GAME_LENGTH) {
            running = false;
            gameTickTimer.stop();
            
            System.out.println(this.score);
            
            // print the score to the file
            try {printScoreToFile();} catch (IOException ex){};
            
            // show the final screen
            displayEndScreen(this.FILE_NAME);
        }
        
        // update the score and timer displayed to the player
        MenuFrame.game.updateScore(score);
        MenuFrame.game.updateTimer(GAME_LENGTH - timeElapsed);
        
        // calls the paintComponent method
        repaint();        
        
    }
    
    /**
     * Checks whether the falling blocks have passed through the floor or not
     */
    private void checkFallen() {
        for (MovableBlock b : this.fallingBlocks) {
            if (b.origin.z() < -2) {
                // if past a certain z value, reset
                resetBlockPos(b);
                
            }
        }
        
        
    }
    
    /**
     * Resets the specified block to a random position
     * @param b the falling block to be reset
     */
    private void resetBlockPos(MovableBlock b) {
        Random randomizer = new Random();
        
        double randX = randomizer.nextDouble((RIGHT_LIMIT - 2) - LEFT_LIMIT + 1) + LEFT_LIMIT;
        double randY = randomizer.nextDouble((BACK_LIMIT - 1.5) - (FRONT_LIMIT - 0.5) + 1) + (FRONT_LIMIT+0.5);

        b.setLocation(randX, randY, ROOM_HEIGHT + randX);
    }
    
    /**
     * Checks for collisions between the player and the falling blocks. If one occurs, it ticks up the score
     */
    private void checkCollisions() {
        
        // loop through the falling blocks and compare their bounds to the character block
        for (MovableBlock b : this.fallingBlocks) {
            // check "height" (the camera is facing down, so this appears like depth to the user)
            if (b.origin.z() <= characterBlock.origin.z() + characterBlock.size()) {
                boolean LRCollision = false; // for whether there was a collision on the left right axis
                
                if (b.getLeftXBound() <= characterBlock.getRightXBound() && b.getLeftXBound() >= characterBlock.getLeftXBound()) {
                    LRCollision = true;

                } else if (b.getRightXBound() >= characterBlock.getLeftXBound() && b.getLeftXBound() <= characterBlock.getLeftXBound()) {
                    LRCollision = true;
                    
                }
                
                // now check if they also collided along the other axis
                // if yes, tick the score and reset the block
                if (LRCollision) {
                    // top of little block to bottom of player block
                    if (b.getUpperYBound() >= characterBlock.getLowerYBound() && b.getUpperYBound() <= characterBlock.getUpperYBound()) {
                        resetBlockPos(b);
                        score++;

                    } else if (b.getLowerYBound() <= characterBlock.getUpperYBound() && b.getLowerYBound() >= characterBlock.getLowerYBound()) {
                        resetBlockPos(b);
                        score++;
                    }
                }
                
            }
        }
    }
    
    /**
     * Records the date, time, and score for the current game session
     * @throws IOException 
     */
    private void printScoreToFile() throws IOException {
        // create the file writers
        FileWriter fw = new FileWriter(FILE_NAME, true);	
        BufferedWriter bw = new BufferedWriter(fw);
        
        try (PrintWriter outFile = new PrintWriter(bw)) {
            // get current date
            // process for the data found here: https://www.javatpoint.com/java-get-current-date
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            
            // print out
            outFile.println(dtf.format(now) + " - " + score);
            
            outFile.close(); //closes the PrintWriter object
            fw.close();
            
        }
        
    }
    
    /**
     * Displays the end of game screen
     * @param filename the file name to read from later on
     */
    public void displayEndScreen(String filename) {
        WinFrame endFrame = new WinFrame(filename, this.score);
        endFrame.placeComponents();
        
        FinalTesting.menu.game.dispose();
    }
    
   
    /**
     * Listener for keyboard input
     */
    private class KeyboardListener implements KeyListener {
        // booleans for controlling the camera movement
        boolean camMovingLeft = false;
        boolean camMovingRight = false;
        boolean camMovingForward = false;
        boolean camMovingBackward = false;
        
        // booleans for controlling the player movement
        boolean movingLeft = false;
        boolean movingRight = false;
        boolean movingUp = false;
        boolean movingDown = false;
        
        @Override
        public void keyTyped(KeyEvent e) {
            
        }
        
        @Override
        public void keyPressed(KeyEvent e) {
             
            // move the character block
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    movingLeft = true;
                    
                    
                    break;
                case KeyEvent.VK_RIGHT:
                    movingRight = true;
                    
                    
                    break;
                case KeyEvent.VK_UP:
                    movingUp = true;
                    
                    break;
                case KeyEvent.VK_DOWN:
                    movingDown = true;
                    
                    break;
                    
            }
            
            
            // diagonal movments and linear
            if (movingLeft && movingUp) {
                if (characterBlock.origin.y() < BACK_LIMIT + (characterBlock.size() / 2) - characterBlock.speed()) {
                    characterBlock.move(0, characterBlock.speed(), 0);
                }
                
                if (characterBlock.origin.x() > LEFT_LIMIT) {
                    characterBlock.move(-1 * characterBlock.speed(), 0, 0);
                }
            } else if (movingRight && movingUp) {
                if (characterBlock.origin.y() < BACK_LIMIT + (characterBlock.size() / 2) - characterBlock.speed()) {
                    characterBlock.move(0, characterBlock.speed(), 0);
                }
                
                if (characterBlock.origin.x() < RIGHT_LIMIT - characterBlock.size() - characterBlock.speed()) {
                    characterBlock.move(characterBlock.speed(), 0, 0);
                }
            } else if (movingLeft && movingDown) {
                if (characterBlock.origin.x() > LEFT_LIMIT) {
                    characterBlock.move(-1 * characterBlock.speed(), 0, 0);
                }
                
                if (characterBlock.origin.y() > FRONT_LIMIT) {
                    characterBlock.move(0, -1 * characterBlock.speed(), 0);
                }
            } else if (movingRight && movingDown) {
                if (characterBlock.origin.x() < RIGHT_LIMIT - characterBlock.size() - characterBlock.speed()) {
                    characterBlock.move(characterBlock.speed(), 0, 0);
                }
                
                if (characterBlock.origin.y() > FRONT_LIMIT) {
                    characterBlock.move(0, -1 * characterBlock.speed(), 0);
                }
                
            } else if (movingUp) {
                if (characterBlock.origin.y() < BACK_LIMIT + (characterBlock.size() / 2) - characterBlock.speed()) {
                    characterBlock.move(0, characterBlock.speed(), 0);
                }
            } else if (movingDown) {
               if (characterBlock.origin.y() > FRONT_LIMIT) {
                    characterBlock.move(0, -1 * characterBlock.speed(), 0);
                }
            } else if (movingLeft) {
                if (characterBlock.origin.x() > LEFT_LIMIT) {
                    characterBlock.move(-1 * characterBlock.speed(), 0, 0);
                }
            } else if (movingRight) {
                if (characterBlock.origin.x() < RIGHT_LIMIT - characterBlock.size() - characterBlock.speed()) {
                    characterBlock.move(characterBlock.speed(), 0, 0);
                }
            }
            
          
            
            
            /***********************************************************************************/
            // if you want to be able to move the camera around, change this to "true"
            boolean mrKedwellWantsToMoveTheCamera = false; 
            // you can then use WASD, Q and SPACE to move the camera around
            // note, movement is designed for when the camera is facing forward, not down.
            //      therefore SPACE and Q will appear to move you backward and forward 
            /***********************************************************************************/
          
            if (mrKedwellWantsToMoveTheCamera) {
                // move the camera
                switch (e.getKeyChar()) {
                    case 'w':
                        camMovingForward = true;
                        break;
                    case 'a':
                        camMovingLeft = true;
                        break;
                    case 's':
                        camMovingBackward = true;
                        break;
                    case 'd':
                        camMovingRight = true;
                        break;

                    // REMOVE LATER ***********
                    case 'q':
                        theScene.cam.move(0, 0, -0.05);
                        break;
                    case ' ':
                        theScene.cam.move(0, 0, 0.05);
                        break;
                }
             
            }
            
            // diagonal movments and linear
            if (camMovingLeft && camMovingForward) {
                theScene.cam.move(-0.05, 0, 0);
                theScene.cam.move(0, 0.05, 0);
            } else if (camMovingRight && camMovingForward) {
                theScene.cam.move(0.05, 0, 0);
                theScene.cam.move(0, 0.05, 0);
            } else if (camMovingLeft && camMovingBackward) {
                theScene.cam.move(-0.05, 0, 0);
                theScene.cam.move(0, -0.05, 0);
            } else if (camMovingRight && camMovingBackward) {
                theScene.cam.move(0.05, 0, 0);
                theScene.cam.move(0, -0.05, 0);
            } else if (camMovingForward) {
                theScene.cam.move(0, 0.05, 0);
            } else if (camMovingBackward) {
                theScene.cam.move(0, -0.05, 0);
            } else if (camMovingLeft) {
                theScene.cam.move(-0.05, 0, 0);
            } else if (camMovingRight) {
                theScene.cam.move(0.05, 0, 0);
            }
            
            repaint();
            
        }
        
        @Override
        public void keyReleased(KeyEvent e) {
            // when you release a key, set it so you aren't move in that direction anymore
            
            switch (e.getKeyChar()) {
                case 'w':
                    camMovingForward = false;
                    break;
                case 'a':
                    camMovingLeft = false;
                    break;
                case 's':
                    camMovingBackward = false;
                    break;
                case 'd':
                    camMovingRight = false;
                    break;
            }
            
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    movingUp = false;
                    break;
                case KeyEvent.VK_LEFT:
                    movingLeft = false;
                    break;
                case KeyEvent.VK_DOWN:
                    movingDown = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    movingRight = false;
                    break;
            }
        }
        
    } 
}
