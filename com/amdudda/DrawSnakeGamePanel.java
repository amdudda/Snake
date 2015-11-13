package com.amdudda;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

/**
 * This class responsible for displaying the graphics, so the snake, grid, kibble, instruction text and high game_score
 *
 * @author Clara
 * @student A.M. Dudda
 */
public class DrawSnakeGamePanel extends JPanel {

    private Snake snake;
    private Kibble kibble;
    private Score score;
    // AMD: added MazeWall, need arraylist to store multiple walls.  I don't care what order they're drawn in.
    // FINDBUGS: says this should be final.
    private final static ArrayList<MazeWall> gameWalls = new ArrayList<MazeWall>();

    DrawSnakeGamePanel(Snake s, Kibble k, Score sc) {
        this.snake = s;
        this.kibble = k;
        this.score = sc;
    }

    public Dimension getPreferredSize() {
        return new Dimension(SnakeGame.getxPixelMaxDimension(), SnakeGame.getyPixelMaxDimension());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        /* Where are we at in the game? 4 phases.. 
         * 1. Before game starts
         * 2. During game
         * 3. Game lost aka game over
         * 4. or, game won
         */

        // FINDBUGS: made this a local variable - it's the game's job to track game stage.
        int gameStage = SnakeGame.getGameStage();

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
            }  //FINDBUGS: switch case might fall through, let's go to beforegame behavior
            default: {
                displayInstructions(g);
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
        g.drawString("YOU WON SNAKE!!!", SnakeGame.getxPixelMaxDimension()/2 - 200, SnakeGame.getyPixelMaxDimension()/2 + 24);
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
        score.resetHaveNewHighScore();  // AMD: reset the high game_score flag to false
        displayGameGrid(g);
        // AMD: Kibble & snake should know how to draw themselves.
        snake.draw(g);
        //kibble.draw(g);
        kibble.drawImage(g);
    }

    private void displayGameGrid(Graphics g) {

        int maxX = SnakeGame.getxPixelMaxDimension();
        int maxY = SnakeGame.getyPixelMaxDimension();
        int squareSize = SnakeGame.getSquareSize();

        g.clearRect(0, 0, maxX, maxY);

        // AMD: changed color to light gray so the grid lines are background info instead of foreground.
        // Player's focus should be on snake, not on the grid it navigates.
        g.setColor(Color.LIGHT_GRAY);

        //Draw grid - horizontal lines
        for (int y = 0; y <= maxY; y += squareSize) {
            g.drawLine(0, y, maxX, y);
        }
        //Draw grid - vertical lines
        for (int x = 0; x <= maxX; x += squareSize) {
            g.drawLine(x, 0, x, maxY);
        }

        // draw our maze wall if the game is using this feature:
        if (SnakeGame.getHasMazeWalls()) {
            for (MazeWall mw : gameWalls) {
                mw.draw(g);
            }
        }

        // if enabled features are on, and the axe is set to visible, display the axe for possible collection
        if (SnakeGame.getEnableExtendedFeatures() && SnakeGame.game_axe.isVisible()) {
            //SnakeGame.game_axe.setVisible(true);  // TODO axe reappears until 6th kibble is eaten.  Should I let axe count as kibble eaten?
            SnakeGame.game_axe.drawImage(g);
        }
    }

    private void displayInstructions(Graphics g) {
        g.drawString("Press letter O to view and set game options.", 100, 200);
        g.drawString("Press letter Q to quit the game.", 100, 250);
        g.drawString("Press any other key to begin!", 100, 300);
        // AMD: Resequenced this information so users read options & quit instructions before the start game instructions.
    }

    public static ArrayList<MazeWall> getGameWalls() {
        //FINDBUGS: prompted by bug check
        return gameWalls;
    }
}

