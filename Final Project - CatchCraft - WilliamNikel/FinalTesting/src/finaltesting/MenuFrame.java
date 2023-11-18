
package finaltesting;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 * This class will create and carry out the menu operations
 * @author william.nikel
 */
public class MenuFrame extends JFrame {

    public static GameFrame game;
    
    private JLabel lblMainTitle;
    private JLabel lblInstructions;
    private JLabel lblPlayNow;
    
    private JPanel mainPanel;
    
    public MenuFrame() {
        super("CatchCraft"); 
    }
    
    public void placeComponents() {
        // set up the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(0, 0, 300, 400);
        
        
        // create the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(106, 168, 79));
        
        // create the main title
        lblMainTitle = new JLabel("CatchCraft");
        lblMainTitle.setFont(new Font("Impact", Font.PLAIN, 40));
        lblMainTitle.setBounds(50, 30, 200, 50);
        
        
        // create the instructions
        ImageIcon imgInstruct = new ImageIcon(getClass().getResource("images/instructions.png"));
        lblInstructions = new JLabel(new ImageIcon(imgInstruct.getImage().getScaledInstance((int)(200 * 1.37), 200, Image.SCALE_SMOOTH)));
        lblInstructions.setBounds(10, 80, (int)(200 * 1.37), 200);

        // add the image to the play now button
        ImageIcon imgPlayNow = new ImageIcon(getClass().getResource("images/playNow.png"));
        lblPlayNow = new JLabel(new ImageIcon(imgPlayNow.getImage().getScaledInstance(200, 50, Image.SCALE_SMOOTH)));
        lblPlayNow.setBounds(50, 300, 200, 50);
        
        // add the mouse listener to the play button
        MListener clickListen = new MListener();
        lblPlayNow.addMouseListener(clickListen);
        
        // add to the frame
        mainPanel.add(lblMainTitle);
        mainPanel.add(lblInstructions);
        mainPanel.add(lblPlayNow);
        
        this.add(mainPanel);
        this.setVisible(true);
        this.setResizable(false);
        
    }
    
    /**
     * Listener for the play button
     */
    private class MListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            // for starting the game on the play now button
            game = new GameFrame();
            game.placeComponents();
            
            // close this frame
            dispose();
        }

        @Override
        public void mousePressed(MouseEvent e) {
            
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        
        }

        @Override
        public void mouseExited(MouseEvent e) {
            
        }
        
    }
    
}
