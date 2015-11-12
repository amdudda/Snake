package com.amdudda;

/**
 * Keeps track of, and display the user's game_score
 *
 * @student A.M. Dudda
 */


public class Score {

    protected static int score = 0;
    protected static int highScore = 0;
    protected static int increment = 1;
    private static boolean haveNewHighScore = false;

    public Score() {
        /*FINDBUGS: these should be "package protected"
        score = 0;
        increment = 1;  //how many points for eating a kibble*/
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

    //Checks if current game_score is greater than the current high game_score.
    //updates high game_score and returns true if so.

    public boolean gameOver() {

        if (score > highScore) {
            highScore = score;
            return true;
        }
        return false;
    }

    //These methods are useful for displaying game_score at the end of the game

    public String getStringScore() {
        return Integer.toString(score);
    }

    public String newHighScore() {
        // AMD: need to keep track of the existence of a new high game_score so it can be announced in the game display.
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

