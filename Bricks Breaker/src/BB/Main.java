package BB;

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        JFrame obj = new JFrame();
        GamePlay gamePlay = new GamePlay(); // Creates an instance of the game play class

        // Create frame for the game
        obj.setBounds(10, 10, 700, 600); // Set the position and size of the frame
        obj.setTitle("Brick Breaker"); // Set the title of the frame
        obj.setResizable(false); // Disable frame resizing
        // obj.setResizable(true);
        obj.setVisible(true); // Make the frame visible
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set the default close operation
        obj.add(gamePlay); // Add the game play object to the frame
    }
}
