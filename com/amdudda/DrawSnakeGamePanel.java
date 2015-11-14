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
        // AMD: Looked up how to play with fonts - looked at several sites, but these two started
        // the path to comprehension: http://www.coderanch.com/t/446177/GUI/java/change-font-JPanel
        // and http://stackoverflow.com/questions/15260484/java-swing-how-to-change-the-font-size-on-a-jpanels-titledborder

        // AMD: clear the entire screen, not just a portion of the board
        g.clearRect(0, 0, SnakeGame.getxPixelMaxDimension(), SnakeGame.getyPixelMaxDimension());
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
        g.drawString("YOU WON SNAKE!!!", getLeftMargin("YOU WON SNAKE!!!",g), SnakeGame.getyPixelMaxDimension()/2 + 24);
        g.setFont(fontname);
        g.setColor(oldcolor);
    }

    private void displayGameOver(Graphics g) {
        /* AMD: Alignment should probably be based on overall board size, not on absolute values,
        but font rendering is workstation dependent and so we can't just assume a given font-to-pixel ratio
        */
        String playAgainInstructions ="Press any other key to play again";
        String quitInstructions = "Press q to quit the game";
        int lm = Math.min(getLeftMargin(playAgainInstructions, g), getLeftMargin(quitInstructions,g));  // AMD: not sure which string is actually longer in pixels, so use the smaller margin.
        int tm = getTopMargin(250);  // AMD: the number is taken from how far down the text rows spread

        // AMD: clear the entire screen, not just a portion of the board
        g.clearRect(0, 0, SnakeGame.getxPixelMaxDimension(), SnakeGame.getyPixelMaxDimension());
        // AMD: let's make the game over text bigger, too
        Font f = g.getFont();
        int fSize = g.getFont().getSize();
        g.setFont(new Font(f.getName(),Font.BOLD,18));
        g.drawString("GAME OVER", lm, tm);
        g.setFont(f);

        String textScore = score.getStringScore();
        String textHighScore = score.getStringHighScore();
        String newHighScore = score.newHighScore();

        g.drawString("SCORE = " + textScore, lm, tm+100);
        Color oldcolor = g.getColor();
        g.drawString("HIGH SCORE = " + textHighScore, lm, tm+150);
        // AMD: moved new High Score announcement so it's actually readable.
        g.setColor(Color.RED);
        g.drawString(newHighScore, lm, tm+175);
        g.setColor(oldcolor);

        g.drawString(quitInstructions, lm, tm+200);
        g.drawString(playAgainInstructions, lm, tm+250);

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
        if (SnakeGame.getEnableExtendedFeatures() && SnakeGame.getGame_axe().isVisible()) {
            SnakeGame.getGame_axe().drawImage(g);
        }
    }

    private void displayInstructions(Graphics g) {
        int lm = getLeftMargin("Press letter O to view and set game options.",g);
        int tm = getTopMargin(100);

        g.drawString("Press letter O to view and set game options.", lm, tm);
        g.drawString("Press letter Q to quit the game.", lm, tm + 50);
        g.drawString("Press any other key to begin!", lm, tm + 100);
        // AMD: Resequenced this information so users read options & quit instructions before the start game instructions.
    }

    public static ArrayList<MazeWall> getGameWalls() {
        //FINDBUGS: prompted by bug check
        return gameWalls;
    }

    private int getLeftMargin(String stringToCenter, Graphics q) {
        // AMD: This returns the left pixel position that will center a given string on the screen.

        /*AMD: hey, I found this, which is pretty slick!
        http://stackoverflow.com/questions/258486/calculate-the-display-width-of-a-string-in-java*/
        int width = q.getFontMetrics().stringWidth(stringToCenter);

        // we want to calculate our pixel length for the string
        // and subtract that from our window's x dimension
        int textPixels = SnakeGame.getxPixelMaxDimension() - (width);
        // then return half of that
        return textPixels / 2;

        /* AMD: Oracle documentation is also pretty useful for understanding what's going on:
        http://docs.oracle.com/javase/7/docs/api/java/awt/Font.html#Font%28java.lang.String,%20int,%20int%29
         */
    }

    private int getTopMargin(int textBlockHeight) {
        // AMD: Returns a number that positions the top line so text is centered vertically.
        // This fudges by not taking into account the height of the last line of text.
        // magic number???  how does the program know the height of text to use?
        // For now, just feed it a number and let it do math.
        return (SnakeGame.getyPixelMaxDimension() - textBlockHeight) / 2;
    }
}

