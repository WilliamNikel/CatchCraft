
package finaltesting;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

/**
 * Creates the frame that contains the actual game
 * @author william.nikel
 */
public class GameFrame extends JFrame {
    
    // declare components to be added to the frame
    private JPanel mainPanel;
    private ImagePanel gamePanel;
    
    private JLabel lblTime;
    private JLabel lblScore;
    
    private final int TOTAL_TIME = 45; // the length of the game in seconds
    
    /**
     * Constructor
     */
    public GameFrame() {
        super("catchCraft");
    }
    
    /**
     * Adds the components to the frame
     */
    public void placeComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, 500, 550);
        
        
        // create components
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(145, 113, 64));
        
        gamePanel = new ImagePanel(TOTAL_TIME); // create the game section
        gamePanel.setBounds(0, 60, 500, 400);
        
        lblScore = new JLabel("Score: 0");
        lblScore.setBounds(10, 20, 100, 50);
        lblScore.setFont(new Font("Impact", Font.PLAIN, 20));
        
        lblTime = new JLabel("Time remaining: " + TOTAL_TIME + " seconds");
        lblTime.setBounds(250, 20, 250, 50);
        lblTime.setFont(new Font("Impact", Font.PLAIN, 20));
        
        mainPanel.add(lblScore);
        mainPanel.add(lblTime);
        mainPanel.add(gamePanel);
        
        // make the frame visible
        this.setResizable(false);
        this.add(mainPanel);
        this.setVisible(true);
        
        
    }
    
    /**
     * Updates the score label
     * @param scr the new score
     */
    public void updateScore(int scr) {
        lblScore.setText("Score: " + scr);
    }
    
    /**
     * Updates the timer label
     * @param curTime the new time remaining
     */
    public void updateTimer(int curTime) {
        lblTime.setText("Time remaining: " + curTime + " seconds");
    }
}
