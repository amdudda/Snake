package com.amdudda;

/**
 * Keeps track of, and display the user's score
 *
 * @student A.M. Dudda
 */


public class Score {

    protected static int score;
    protected static int highScore = 0;
    protected static int increment;
    private static boolean haveNewHighScore = false;

    public Score() {
        score = 0;
        increment = 1;  //how many points for eating a kibble
        //Possible TODO get more points for eating kibbles, the longer the snake gets?
    }

    public static void resetScore() {
        score = 0;
    }

    public static void increaseScore() {

        score = score + increment;

    }

    public int getScore() {
        return score;
    }

    //Checks if current score is greater than the current high score.
    //updates high score and returns true if so.

    public boolean gameOver() {

        if (score > highScore) {
            highScore = score;
            return true;
        }
        return false;
    }

    //These methods are useful for displaying score at the end of the game

    public String getStringScore() {
        return Integer.toString(score);
    }

    public String newHighScore() {
        // AMD: need to keep track of the existence of a new high score so it can be announced in the game display.
        if (score > highScore) {
            highScore = score;
            haveNewHighScore = true;
        }
        if (haveNewHighScore) {
            return "New High Score!!";
        } else {
            return "";
        }
    }

    public String getStringHighScore() {
        return Integer.toString(highScore);
    }

    public void resetHaveNewHighScore() {
        haveNewHighScore = false;
    }
}

