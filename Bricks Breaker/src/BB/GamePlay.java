package BB;

import java.awt.Color;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;


// Adding JPanel for using graphic methods
// Also implement actions from the keyboard and movement of the board by implementing KeyListener and ActionListener
public class GamePlay extends JPanel implements KeyListener, ActionListener {

    private boolean play = false; // Boolean to determine if the game is currently being played
    private int score = 0; // Current score

    private int totalBricks = 21; // Total number of bricks in the game

    private Timer timer; // Timer to update the game
    private int delay = 9; // Delay for the timer


    private int playerX = 310; // X position of the paddle on the frame

    private int ballposX = 120; // X location of the ball
    private int ballposY = 350; // Y location of the ball

    //X and Y are going to be increments with which the program will know how to move the ball and which is the next location of the ball once the pedals hits
    private int ballXdir = -1; // X direction of the ball
    private int ballYdir = -2; // Y direction of the ball

    private MapGenerator map; // Instance of the map generator class

    // Constructor for initializing the game play
    public GamePlay() {
        map = new MapGenerator(3, 7); // Create a map with 3 rows and 7 columns
        addKeyListener(this); // Add KeyListener to the panel
        setFocusable(true); // Set the panel focusable
        addFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this); // Create a timer with the specified delay and ActionListener
        timer.start(); // Start the timer
    }


    private void addFocusTraversalKeysEnabled(boolean b) {
    }

    // Override the paint method to draw the game graphics
    public void paint(Graphics g) {
        // Draw the background
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(1, 1, 692, 592);

        // Draw the bricks
        map.draw((Graphics2D) g);

        // Draw the borders
        g.setColor(Color.cyan);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        // Draw the paddle
        g.setColor(Color.blue);
        g.fillRect(playerX, 550, 100, 8);

        // Draw the ball
        g.setColor(Color.magenta);
        g.fillOval(ballposX, ballposY, 20, 20);

        // Draw the score
        g.setColor(Color.RED);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 590, 30);

        // Display the "You Won" message if all bricks are destroyed
        if (totalBricks <= 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.yellow);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won, Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart!", 230, 350);
        }

        // Display the "Game Over" message if the ball falls below the paddle
        if (ballposY > 570) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.red);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + score, 190, 300);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart!", 230, 350);
        }
        g.dispose();
    }
    private void addFocusTraversalKeysEnable(boolean b) {
    }

    // Implement the actionPerformed method from the ActionListener interface
    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start(); // Start the timer
        if (play) {
            // Ball - paddle interaction
            if (new Rectangle(ballposX, ballposY, 20, 30).intersects(new Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            // Ball - brick interaction
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 80;
                        int brickY = i * map.brickHeight + 50;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
                        Rectangle brickRect = rect;

                        if (ballRect.intersects((brickRect))) {
                            map.setBrickValue(0, i, j); // Set the value of the brick to 0 to indicate destruction
                            totalBricks--;
                            score += 5;

                            if (ballposX + 19 <= brickRect.x || ballposY + 1 >= brickRect.x + brickRect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }
                        }
                    }
                }
            }
            // Move the ball
            ballposX += ballXdir;
            ballposY += ballYdir;

            // Reflect the ball if it hits the borders
            if (ballposX < 0) {
                ballXdir = -ballXdir;
            }
            if (ballposY < 0) {
                ballYdir = -ballYdir;
            }
            if (ballposX > 670) {
                ballXdir = -ballXdir;
            }
        }
        repaint(); // Repaint the panel
    }

    // Implement the KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Move the paddle to the right when the right arrow key is pressed
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }

        // Move the paddle to the left when the left arrow key is pressed
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        // Restart the game when the Enter key is pressed
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballposX = 120;
                ballposY = 350;
                ballXdir = -1;
                ballYdir = -2;
                score = 0;
                totalBricks = 21;
                map = new MapGenerator(3, 7);

                repaint();
            }
        }
    }
    // Move the paddle to the right
    public void moveRight() {
        play = true;
        playerX += 20;
    }

    // Move the paddle to the left
    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
