package com.amdudda;

/**
 * Keeps track of, and display the user's game_score
 *
 * @student A.M. Dudda
 */


public class Score {

    // AMD: these aren't ever directly accessed outside of this object, so let's make them private.
    // (Prompted by FINDBUGS results.)
    private static int score = 0;
    private static int highScore = 0;
    private static int increment = 1;
    private static boolean haveNewHighScore = false;

    public Score() {
        /*FINDBUGS: these should be "package protected" -- In this version, there's only one Score object per game, so these can be static variables.
        score = 0;
        increment = 1;  //how many points for eating a kibble*/
        //Possible TODO get more points for eating kibbles, the longer the snake gets?
    }

    public static void setScore(int newscore) { Score.score = newscore; }

    public static void setHighScore(int hi_score) { Score.highScore = hi_score; }

    public static void setHaveNewHighScore(boolean tf) { Score.haveNewHighScore = tf; }

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

    /*  FINDBUGS: This isn't even used, why am I trying to fix this?
    public static boolean gameOver() {
        int localscore = Score.score;
        if (localscore > Score.highScore) {
            setHighScore(localscore);
            return true;
        }
        return false;
    }*/

    //These methods are useful for displaying game_score at the end of the game

    public String getStringScore() {
        return Integer.toString(score);
    }

    public static String newHighScore() {
        // AMD: need to keep track of the existence of a new high game_score so it can be announced in the game display.
        if (score > highScore) {
            setHighScore(Score.score);
            setHaveNewHighScore(true);
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

    //FINDBUGS: Made this method static
    public static void resetHaveNewHighScore() {
        haveNewHighScore = false;
    }
}

