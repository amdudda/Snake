package com.amdudda;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JPanel;

/**
 * This class responsible for displaying the graphics, so the snake, grid, kibble, instruction text and high score
 *
 * @author Clara
 * @student A.M. Dudda
 */
public class DrawSnakeGamePanel extends JPanel {

    private static int gameStage = SnakeGame.BEFORE_GAME;  //use this to figure out what to paint

    private Snake snake;
    private Kibble kibble;
    private Score score;
    // AMD: can I draw a mazewall now?
    protected static MazeWall mw1;

    DrawSnakeGamePanel(Snake s, Kibble k, Score sc) {
        this.snake = s;
        this.kibble = k;
        this.score = sc;
    }

    public Dimension getPreferredSize() {
        return new Dimension(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        /* Where are we at in the game? 4 phases.. 
         * 1. Before game starts
         * 2. During game
         * 3. Game lost aka game over
         * 4. or, game won
         */

        gameStage = SnakeGame.getGameStage();

        switch (gameStage) {
            case SnakeGame.BEFORE_GAME:
            case SnakeGame.SET_OPTIONS: {
                displayInstructions(g);
                break;
            }
            case SnakeGame.DURING_GAME: {
                displayGame(g);
                break;
            }
            case SnakeGame.GAME_OVER: {
                displayGameOver(g);
                break;
            }
            case SnakeGame.GAME_WON: {
                displayGameWon(g);
                break;
            }
        }


    }

    private void displayGameWon(Graphics g) {
        // TODO Replace this with something really special!
        // AMD: Looked up how to play with fonts - looked at several sites, but these two started
        // the path to comprehension: http://www.coderanch.com/t/446177/GUI/java/change-font-JPanel
        // and http://stackoverflow.com/questions/15260484/java-swing-how-to-change-the-font-size-on-a-jpanels-titledborder
        g.clearRect(100, 100, 350, 350);
        Font fontname = g.getFont();
        g.setFont(new Font(fontname.getName(),Font.BOLD,36));
        Color oldcolor = g.getColor();
        // let's pick a random color: https://docs.oracle.com/javase/7/docs/api/java/awt/Color.html
        Random rng = new Random();
        int r = rng.nextInt(255);
        int v = rng.nextInt(255);
        int b = rng.nextInt(255);
        Color farbe = new Color(r,v,b);
        g.setColor(farbe);
        g.drawString("YOU WON SNAKE!!!", SnakeGame.xPixelMaxDimension/2 - 200, SnakeGame.yPixelMaxDimension/2 + 24);
        g.setFont(fontname);
        g.setColor(oldcolor);
    }

    private void displayGameOver(Graphics g) {

        g.clearRect(100, 100, 350, 350);
        g.drawString("GAME OVER", 150, 150);

        String textScore = score.getStringScore();
        String textHighScore = score.getStringHighScore();
        String newHighScore = score.newHighScore();

        g.drawString("SCORE = " + textScore, 150, 250);
        Color oldcolor = g.getColor();
        g.drawString("HIGH SCORE = " + textHighScore, 150, 300);
        // AMD: moved new High Score announcement so it's actually readable.
        g.setColor(Color.RED);
        g.drawString(newHighScore, 150, 325);
        g.setColor(oldcolor);

        g.drawString("press a key to play again", 150, 350);
        g.drawString("Press q to quit the game", 150, 400);

    }

    private void displayGame(Graphics g) {
        score.resetHaveNewHighScore();  // AMD: reset the high score flag to false
        displayGameGrid(g);
        displaySnake(g);
        displayKibble(g);
    }

    private void displayGameGrid(Graphics g) {

        int maxX = SnakeGame.xPixelMaxDimension;
        int maxY = SnakeGame.yPixelMaxDimension;
        int squareSize = SnakeGame.squareSize;

        g.clearRect(0, 0, maxX, maxY);

        g.setColor(Color.RED);

        //Draw grid - horizontal lines
        for (int y = 0; y <= maxY; y += squareSize) {
            g.drawLine(0, y, maxX, y);
        }
        //Draw grid - vertical lines
        for (int x = 0; x <= maxX; x += squareSize) {
            g.drawLine(x, 0, x, maxY);
        }

        // draw our maze wall if the game is using this feature:
        if (SnakeGame.hasMazeWalls) { mw1.draw(g); }
    }

    private void displayKibble(Graphics g) {

        //Draw the kibble in green
        g.setColor(Color.GREEN);

        int x = kibble.getKibbleX() * SnakeGame.squareSize;
        int y = kibble.getKibbleY() * SnakeGame.squareSize;

        g.fillRect(x + 1, y + 1, SnakeGame.squareSize - 2, SnakeGame.squareSize - 2);

    }

    private void displaySnake(Graphics g) {

        LinkedList<Point> coordinates = snake.segmentsToDraw();

        //Draw head in grey
        g.setColor(Color.LIGHT_GRAY);
        Point head = coordinates.pop();
        g.fillRect((int) head.getX(), (int) head.getY(), SnakeGame.squareSize, SnakeGame.squareSize);

        //Draw rest of snake in black
        g.setColor(Color.BLACK);
        for (Point p : coordinates) {
            g.fillRect((int) p.getX(), (int) p.getY(), SnakeGame.squareSize, SnakeGame.squareSize);
        }
    }

    private void displayInstructions(Graphics g) {
        g.drawString("Press any key to begin!", 100, 200);
        g.drawString("Press o to view and set game options", 100, 250);
        g.drawString("Press q to quit the game", 100, 300);
    }


}

