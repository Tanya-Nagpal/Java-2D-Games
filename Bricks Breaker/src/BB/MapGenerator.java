package BB;

import java.awt.*;

public class MapGenerator {
    public int map[][]; // Multi-dimensional matrix
    public int brickWidth;
    public int brickHeight;

    // Constructor to create a 2D array and initialize the bricks
    public MapGenerator(int row, int col) {
        map = new int[row][col];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = 1; // Set brick value to 1 to indicate a brick is present
            }
        }
        brickWidth = 540 / col;
        brickHeight = 150 / row;
    }

    // Method to draw the bricks on the screen
    public void draw(Graphics2D g) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    g.setColor(Color.black);
                    g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.pink);
                    g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                }
            }
        }
    }

    // Method to set the value of a brick in the map
    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }

}

