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
    // because game is timer-driven, these can't be static, or all maze walls will be
    // drawn at whatever squareSize is set to when the program loads DrawSnakeGamePanel.
    private int xLines = SnakeGame.xSquares + 1;
    private int yLines = SnakeGame.ySquares + 1;
    private int linelength = SnakeGame.squareSize;

    // Constructor
    public MazeWall() {
        Random position = new Random();
        this.gridX = position.nextInt(xLines);
        this.gridY = position.nextInt(yLines);
        int pickHorV = position.nextInt(2);
        if (pickHorV == 0) { this.v_or_h = 'v'; }
        else { this.v_or_h = 'h'; }
    }

    // getters for attributes
    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }

    public char getV_or_h() {
        return v_or_h;
    }
    // end getters

    @Override
    public String toString() {
        // for now, just a debugging tool to output the values
        return "(" + this.gridX + ", " + this.gridY + ", " + this.v_or_h + ")";
    }

    // and a method to draw the wall
    public void draw(Graphics g) {
        g.setColor(Color.blue);
        // gridX and gridY correspond to grid positions, not pixel positions.
        int xPos = this.gridX * linelength;
        int yPos = this.gridY * linelength;
        // two possibilities: a vertical wall and horizontal wall
        if (v_or_h == 'v') {
            // if vertical, x is 3px wide and y changes
            g.fillRect(xPos-1, yPos, 3, linelength);
        } else {
            // if horizontal, y is 3px wide and x changes
            g.fillRect(xPos, yPos-1, linelength, 3);
        }
    }
}
