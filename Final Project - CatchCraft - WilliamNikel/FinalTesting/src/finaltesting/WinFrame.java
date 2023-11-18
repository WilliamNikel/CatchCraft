
package finaltesting;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;

/**
 * Creates the frame shown to the user when they complete the game
 * @author william.nikel
 */
public class WinFrame extends JFrame {
    // declaring components
    JLabel lblGoodJob;
    JLabel lblScore;
    JLabel lblAllScores;
    JLabel lblAllTitle;
    
    JPanel mainPanel;
    
    private final String FILE_NAME;
    private final int SCORE;
    /**
     * Constructor
     */
    public WinFrame(String file, int score) {
        super("CatchCraft");
        
        this.SCORE = score;
        this.FILE_NAME = file;
        
        this.placeComponents();
    }
    
    /**
     * Places the Swing components on the JFrame, as well as sets up the frame
     */
    public void placeComponents() {
        this.setBounds(0, 0, 300, 300);
        
        // create a new font to be used on the title
        Font titleFont = new Font("Impact", Font.PLAIN, 15);
        
        // create the components
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(145, 113, 64));
        
        lblGoodJob = new JLabel("Good job! You completed the game!");
        lblGoodJob.setBounds(30, 20, 300, 20);
        lblGoodJob.setFont(titleFont);
        
        lblScore = new JLabel("You got a score of " + this.SCORE);
        lblScore.setBounds(70, 50, 200, 20);
        
        lblAllTitle = new JLabel("Previous 5 scores:");
        lblAllTitle.setBounds(70, 85, 200, 20);
        lblAllTitle.setFont(titleFont);
        
        lblAllScores = new JLabel(readScoresFromFile(this.FILE_NAME));
        lblAllScores.setBounds(65, 95, 200, 100);
        
        // add them
        mainPanel.add(lblGoodJob);
        mainPanel.add(lblScore);
        mainPanel.add(lblAllTitle);
        mainPanel.add(lblAllScores);
        
        this.add(mainPanel);
        
        // housekeeping
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        
    }
    
    /**
     * Reads the scores from the file and returns the 5 most recent, including the one you just got
     * @param filename the file being read
     * @return a String that can be inserted into a JLabel as multiple lines
     */
    private String readScoresFromFile(String filename) {
        // the string to be returned. In HTML format so that the JLabel can hold multiple lines.
        // process discovered here: https://stackoverflow.com/questions/685521/multiline-text-in-jlabel
        String outString = "<html>";
        
        // stores all the scores in the file
        ArrayList<String> scoresArray = new ArrayList<>();
        
        String curScore; // the current line of the file being read
        
        Scanner fileScan = null;
        boolean found = true;  

        try {
            //websites.inp is just a text file, you can open it and see the contents
            fileScan = new Scanner(new File(filename));  
        } catch (FileNotFoundException exception) { //this block will run if can't find the file
            System.out.println("The input file was not found.");

            //if file not found, assign variable to false
            found = false;
        }

        if (found == true) { // will run as long as file exists and the exception did not run
            // Read and process each line of the file
            while (fileScan.hasNext()) { //checks to see if another line exists to read
                curScore = fileScan.nextLine();	// read next line into our string variable
                
                scoresArray.add(curScore + "<br/");
                
            }
        }
        
        // loop backwards through all the scores and 5 to the outstring
        
        if (scoresArray.size() > 5) {
            for (int i = scoresArray.size() - 1; i >= scoresArray.size() - 5; i--) {
                outString += scoresArray.get(i);
            } 
        } else {
            for (int i = scoresArray.size() - 1; i >= 0; i--) {
                outString += scoresArray.get(i);
            } 
        }
        
        
        
        return outString + "</html>";
    }
}
