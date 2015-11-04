package com.amdudda;

/**
 * Created by amdudda on 11/4/2015.
 */
import java.awt.*;
import java.util.Random;

public class MazeWall {
    /*
    Constructs maze walls that obstruct the snake's path.
    Three obvious attributes: x & y upper-left coordinate plus whether it's vertically or
    horizontally drawn
     */
    private int gridX;
    private int gridY;
    private char v_or_h;  // char that is either 'v' or 'h'
    // plus some attributes derived from game settings
    private static int xLines = SnakeGame.xSquares + 1;
    private static int yLines = SnakeGame.ySquares + 1;
    private static int linelength = SnakeGame.squareSize;

    // Constructor
    public MazeWall() {
        Random position = new Random();
        this.gridX = position.nextInt(xLines);
        this.gridY = position.nextInt(yLines);
        int pickHorV = position.nextInt(2);
        if (pickHorV == 0) { this.v_or_h = 'v'; }
        else { this.v_or_h = 'h'; }
    }

    @Override
    public String toString() {
        // for now, just a debugging tool to output the values
        return "(" + this.gridX + ", " + this.gridY + ", " + this.v_or_h + ")";
    }

    // and a method to draw the wall
    public void draw(Graphics g) {
        // two possibilities: a vertical wall and horizontal wall
        if (v_or_h == 'v') {
            // if vertical, x is 3px wide and y changes
            g.fillRect(this.gridX, this.gridY, 3, linelength);
        } else {
            // if horizontal, y is 3px wide and x changes
            g.fillRect(this.gridX, this.gridY, linelength, 3);
        }
    }
}
