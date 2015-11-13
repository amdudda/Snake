package com.amdudda;

/*
    @author clara -- code initially downloaded from
	@student A.M. Dudda  -- revised/added coded commented with AMD: prefix
* */

import java.util.Timer;
import javax.swing.*;


public class SnakeGame {

    // AMD: what if we make timer global?
    //FINDBUGS: make these protected rather than public?  or move them inside main method?
    private static Timer timer;
    // AMD: we want to set a final constant as a base dimension so we can multiply, then add 1 pixel, for board sizing.
    protected static final int INITIAL_GAME_SIZE = 500;
    // AMD: and a few other static final constants
    protected static final int NUM_MAZE_WALLS = 3; // Number of walls to build if maze walls enabled.
    protected static final int ADD_WALL_INTERVAL = 3; // Number of kibbles to eat between new maze walls
    protected static final int SHOW_AXE_INTERVAL = 5; // axe shows up every fifth kibble

    // made Not Final so user can adjust this.
    private static int xPixelMaxDimension = INITIAL_GAME_SIZE + 1;  //Pixels in window. 501 to have 50-pixel squares plus 1 to draw a border on last square
    private static int yPixelMaxDimension = INITIAL_GAME_SIZE + 1;

    private static int xSquares ;
    private static int ySquares ;
	private static int squareSize = 50;
    // AMD: Some additional variables that are set at the start of the game
	private static boolean hasWarpWalls = false; // AMD: variable to help implement warp walls.
	private static boolean hasMazeWalls = false;  // AMD: variable to help implement maze walls
	private static boolean enableExtendedFeatures = false; // turns on/off additional mazewalls and axe

	private static Snake snake ;

	private static Mouse kibble;
	// AMD: added Axe
	private static Axe game_axe;

	private static Score game_score;  // AMD: refactored this because I want to be clear that I'm referring to game score and not object's score variable

	protected static final int BEFORE_GAME = 1;
	protected static final int DURING_GAME = 2;
	protected static final int GAME_OVER = 3;
	protected static final int GAME_WON = 4;   //The values are not important. The important thing is to use the constants
	//instead of the values so you are clear what you are setting. Easy to forget what number is Game over vs. game won
	//Using constant names instead makes it easier to keep it straight. Refer to these variables 
	//using statements such as SnakeGame.GAME_OVER
	// AMD: added SET_OPTIONS to keep game from starting while options are being set
	protected static final int SET_OPTIONS = 5;

	private static int gameStage = BEFORE_GAME;  //use this to figure out what should be happening. 
	//Other classes like Snake and DrawSnakeGamePanel will need to query this, and change it's value

	private static long clockInterval = 500; //controls game speed
	//Every time the clock ticks, the snake moves
	//This is the time between clock ticks, in milliseconds
	//1000 milliseconds = 1  second.

	private static SnakeGameWindow snakeFrame;
	private static DrawSnakeGamePanel snakePanel;
	//Framework for this class adapted from the Java Swing Tutorial, FrameDemo and Custom Painting Demo. You should find them useful too.
	//http://docs.oracle.com/javase/tutorial/displayCode.html?code=http://docs.oracle.com/javase/tutorial/uiswing/examples/components/FrameDemoProject/src/components/FrameDemo.java
	//http://docs.oracle.com/javase/tutorial/uiswing/painting/step2.html

	// AMD: Moved main method here for readability.
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				initializeGame();
				snakeFrame = new SnakeGameWindow(SnakeGame.getSnake(), SnakeGame.getKibble(), SnakeGame.getGame_score()); // AMD: formerly == createAndShowGUI();
			}
		});
	}

	protected static void initializeGame() {
		//set up game_score, snake and first kibble
		xSquares = xPixelMaxDimension / squareSize;
		ySquares = yPixelMaxDimension / squareSize;

		snake = new Snake(xSquares, ySquares);
        kibble = new Mouse(snake);
		game_axe = new Axe(snake);
		game_score = new Score();

        SnakeGame.setGameStage(BEFORE_GAME);
	}

	protected static void newGame() {
		// AMD: restart the timer when we kick off a new game.  Also updated Gameclock to deal with Axe.
        timer = new Timer();
		GameClock clockTick = new GameClock(SnakeGame.getSnake(), SnakeGame.getKibble(), SnakeGame.getSnakePanel(), SnakeGame.getGame_axe());
		timer.scheduleAtFixedRate(clockTick, 0, SnakeGame.getClockInterval());
        /*AMD: this causes the game to refresh every clockInterval milliseconds so the snake
        * can be redrawn as game play progresses.
        * */
        // also seed a new set of mazeWalls - but only if mazewalls are turned on
        if (hasMazeWalls) {
            DrawSnakeGamePanel.getGameWalls().clear();
            for (int i = 0; i < NUM_MAZE_WALLS; i++) {
                DrawSnakeGamePanel.getGameWalls().add(new MazeWall());
            }
        }
        // AMD: and move the kibble so it's not in the same spot again.  TODO: write up bug report
        kibble.moveKibble(snake);
	}


	public static int getGameStage() {
		return gameStage;
	}

    public static void setGameStage(int gameStage) {
        SnakeGame.gameStage = gameStage;
    }

    //FINDBUGS: getters & setters for static globals
    public static void setxPixelMaxDimension(int xPixelMaxDimension) {
        SnakeGame.xPixelMaxDimension = xPixelMaxDimension;
    }

    public static void setyPixelMaxDimension(int yPixelMaxDimension) {
        SnakeGame.yPixelMaxDimension = yPixelMaxDimension;
    }

    public static int getxPixelMaxDimension() {
        return SnakeGame.xPixelMaxDimension;
    }

    public static int getyPixelMaxDimension() {
        return SnakeGame.yPixelMaxDimension;
    }

    public static void setxSquares(int xSquares) {
        SnakeGame.xSquares = xSquares;
    }

    public static void setySquares(int ySquares) {
        SnakeGame.ySquares = ySquares;
    }

	public static int getxSquares() { return SnakeGame.xSquares; }

	public static int getySquares() { return SnakeGame.ySquares; }

    public static void setSquareSize(int squareSize) {
        SnakeGame.squareSize = squareSize;
    }

    public static void setHasWarpWalls(boolean hasWarpWalls) {
        SnakeGame.hasWarpWalls = hasWarpWalls;
    }

    public static boolean getHasWarpWalls() { return SnakeGame.hasWarpWalls; }

    public static void setHasMazeWalls(boolean hasMazeWalls) {
        SnakeGame.hasMazeWalls = hasMazeWalls;
    }

    public static boolean getHasMazeWalls() { return SnakeGame.hasMazeWalls; }

    public static void setEnableExtendedFeatures(boolean enableExtendedFeatures) {
        SnakeGame.enableExtendedFeatures = enableExtendedFeatures;
    }

    public static boolean getEnableExtendedFeatures() { return SnakeGame.enableExtendedFeatures; }

    public static void setClockInterval(int clockInt) {
        SnakeGame.clockInterval = clockInt;
    }

    public static void setSnakePanel(DrawSnakeGamePanel panelToUse){
        SnakeGame.snakePanel = panelToUse;
    }
    public static DrawSnakeGamePanel getSnakePanel(){
        return SnakeGame.snakePanel;
    }

    public static SnakeGameWindow getSnakeFrame() {
        return SnakeGame.snakeFrame;
    }

    public static void setSnakeFrame(SnakeGameWindow snakeFrame) {
        SnakeGame.snakeFrame = snakeFrame;
    }

    public static long getClockInterval() {
        return SnakeGame.clockInterval;
    }

    public static void setClockInterval(long clockInterval) {
        SnakeGame.clockInterval = clockInterval;
    }

    public static Timer getTimer() {
        return SnakeGame.timer;
    }

    public static void setTimer(Timer t) {
        SnakeGame.timer = t;
    }

    public static int getSquareSize() {
        return SnakeGame.squareSize;
    }

    public static Snake getSnake() {
        return SnakeGame.snake;
    }

    public static void setSnake(Snake snake) {
        SnakeGame.snake = snake;
    }

    public static Kibble getKibble() {
        return SnakeGame.kibble;
    }

    public static void setKibble(Mouse kibble) {
        SnakeGame.kibble = kibble;
    }

    public static Axe getGame_axe() {
        return SnakeGame.game_axe;
    }

    public static void setGame_axe(Axe game_axe) {
        SnakeGame.game_axe = game_axe;
    }

    public static Score getGame_score() {
        return SnakeGame.game_score;
    }

    public static void setGame_score(Score game_score) {
        SnakeGame.game_score = game_score;
    }

    //FINDBUGS: end of getters & setters for global variables


	public static boolean gameEnded() {
		/*if (gameStage == GAME_OVER || gameStage == GAME_WON){
			return true;
		}
		return false;*/
        // another pointless if-then statement
        return gameStage == GAME_OVER || gameStage == GAME_WON;
	}

    protected static void createAndShowGUI() {
        // DONE: this is technically a separate object; a container for the game.
        //Create and set up the window.
        //snakeFrame = new SnakeGameWindow(snake, kibble, game_score);
		/*snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        snakeFrame.setSize(xPixelMaxDimension, yPixelMaxDimension);
		snakeFrame.setUndecorated(false); // AMD: Show title bar so game can be moved around screen //hide title bar
        snakeFrame.setTitle("Snake Game: feed the snake and avoid the walls!");
		snakeFrame.setVisible(true);

		snakeFrame.setResizable(false);

		snakePanel = new DrawSnakeGamePanel(snake, kibble, game_score);
		snakePanel.setFocusable(true);
		snakePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents

		snakeFrame.add(snakePanel);
		snakePanel.addKeyListener(new GameControls(snake));

		setGameStage(BEFORE_GAME);

		snakeFrame.setVisible(true);*/
    }
}
