package com.amdudda;

/*
    @student A.M. Dudda
* */

import java.awt.Point;
import java.util.LinkedList;

public class Snake {

    final int DIRECTION_UP = 0;
    final int DIRECTION_DOWN = 1;
    final int DIRECTION_LEFT = 2;
    final int DIRECTION_RIGHT = 3;  //These are completely arbitrary numbers.

    private boolean hitWall = false;
    private boolean ateTail = false;
    //private boolean warpWalls = false; // AMD: variable to help implement warp walls.
    //private boolean hasMazeWalls = true;  // AMD: variable to help implement maze walls

    protected int snakeSquares[][];  //represents all of the squares on the screen
    //NOT pixels!
    //A 0 means there is no part of the snake in this square
    //A non-zero number means part of the snake is in the square
    //The head of the snake is 1, rest of segments are numbered in order

    private int currentHeading;  //Direction snake is going in, ot direction user is telling snake to go
    private int lastHeading;    //Last confirmed movement of snake. See moveSnake method

    private int snakeSize;   //size of snake - how many segments?

    private int growthIncrement = 2; //how many squares the snake grows after it eats a kibble

    private int justAteMustGrowThisMuch = 0;

    private int maxX, maxY, squareSize;
    private int snakeHeadX, snakeHeadY; //store coordinates of head - first segment

    public Snake(int maxX, int maxY, int squareSize) {

        this.maxX = maxX;
        this.maxY = maxY;
        this.squareSize = squareSize;
        snakeSquares = new int[this.maxX][this.maxY];
        fillSnakeSquaresWithZeros();
        createStartSnake();
        // AMD: for debugging endgame
        // createDebugSnake();
    }

    protected void createStartSnake() {
        //snake starts as 3 horizontal squares in the center of the screen, moving left
        int screenXCenter = maxX / 2;  //Cast just in case we have an odd number
        int screenYCenter = maxY / 2;  //Cast just in case we have an odd number

        snakeSquares[screenXCenter][screenYCenter] = 1;
        snakeSquares[screenXCenter + 1][screenYCenter] = 2;
        snakeSquares[screenXCenter + 2][screenYCenter] = 3;

        snakeHeadX = screenXCenter;
        snakeHeadY = screenYCenter;

        snakeSize = 3;

        currentHeading = DIRECTION_LEFT;
        lastHeading = DIRECTION_LEFT;

        justAteMustGrowThisMuch = 0;
    }

    private void fillSnakeSquaresWithZeros() {
        for (int x = 0; x < this.maxX; x++) {
            for (int y = 0; y < this.maxY; y++) {
                this.snakeSquares[x][y] = 0;
            }
        }
    }

    public LinkedList<Point> segmentsToDraw() {
        //Return a list of the actual x and y coordinates of the top left of each snake segment
        //Useful for the Panel class to draw the snake
        LinkedList<Point> segmentCoordinates = new LinkedList<Point>();
        for (int segment = 1; segment <= snakeSize; segment++) {
            //search array for each segment number
            for (int x = 0; x < maxX; x++) {
                for (int y = 0; y < maxY; y++) {
                    if (snakeSquares[x][y] == segment) {
                        //make a Point for this segment's coordinates and add to list
                        Point p = new Point(x * SnakeGame.squareSize, y * SnakeGame.squareSize);
                        segmentCoordinates.add(p);
                    }
                }
            }
        }
        return segmentCoordinates;

    }

    public void snakeUp() {
        if (currentHeading == DIRECTION_UP || currentHeading == DIRECTION_DOWN) {
            return;
        }
        currentHeading = DIRECTION_UP;
    }

    public void snakeDown() {
        if (currentHeading == DIRECTION_DOWN || currentHeading == DIRECTION_UP) {
            return;
        }
        currentHeading = DIRECTION_DOWN;
    }

    public void snakeLeft() {
        if (currentHeading == DIRECTION_LEFT || currentHeading == DIRECTION_RIGHT) {
            return;
        }
        currentHeading = DIRECTION_LEFT;
    }

    public void snakeRight() {
        if (currentHeading == DIRECTION_RIGHT || currentHeading == DIRECTION_LEFT) {
            return;
        }
        currentHeading = DIRECTION_RIGHT;
    }

//	public void	eatKibble(){
//		//record how much snake needs to grow after eating food
//		justAteMustGrowThisMuch += growthIncrement;
//	}

    protected void moveSnake() {
        //Called every clock tick

        //Must check that the direction snake is being sent in is not contrary to current heading
        //So if current heading is down, and snake is being sent up, then should ignore.
        //Without this code, if the snake is heading up, and the user presses left then down quickly, the snake will back into itself.
        if (currentHeading == DIRECTION_DOWN && lastHeading == DIRECTION_UP) {
            currentHeading = DIRECTION_UP; //keep going the same way
        }
        if (currentHeading == DIRECTION_UP && lastHeading == DIRECTION_DOWN) {
            currentHeading = DIRECTION_DOWN; //keep going the same way
        }
        if (currentHeading == DIRECTION_LEFT && lastHeading == DIRECTION_RIGHT) {
            currentHeading = DIRECTION_RIGHT; //keep going the same way
        }
        if (currentHeading == DIRECTION_RIGHT && lastHeading == DIRECTION_LEFT) {
            currentHeading = DIRECTION_LEFT; //keep going the same way
        }

        //Did you hit the wall, snake?
        //Or eat your tail? Don't move.
        // AMD: or has the snake hit a maze wall?
        if (this.didHitWall() || this.didEatTail() || this.didHitMazeWall()) {
            SnakeGame.setGameStage(SnakeGame.GAME_OVER);
            return;
        }

        //Use snakeSquares array, and current heading, to move snake

        //Put a 1 in new snake head square
        //increase all other snake segments by 1
        //set tail to 0 if snake did not just eat
        //Otherwise leave tail as is until snake has grown the correct amount

        //Find the head of the snake - snakeHeadX and snakeHeadY

        //Increase all snake segments by 1
        //All non-zero elements of array represent a snake segment

        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                if (snakeSquares[x][y] != 0) {
                    snakeSquares[x][y]++;
                }
            }
        }

        //now identify where to add new snake head
        if (currentHeading == DIRECTION_UP) {
            //Subtract 1 from Y coordinate so head is one square up
            snakeHeadY--;
        }
        if (currentHeading == DIRECTION_DOWN) {
            //Add 1 to Y coordinate so head is 1 square down
            snakeHeadY++;
        }
        if (currentHeading == DIRECTION_LEFT) {
            //Subtract 1 from X coordinate so head is 1 square to the left
            snakeHeadX--;
        }
        if (currentHeading == DIRECTION_RIGHT) {
            //Add 1 to X coordinate so head is 1 square to the right
            snakeHeadX++;
        }

        //Does this make snake hit the wall?
        if (snakeHeadX >= maxX || snakeHeadX < 0 || snakeHeadY >= maxY || snakeHeadY < 0) {
            hitWall = true;
            if (this.didHitWall()) {  // AMD: but only end the game if the warpwalls are turned off
                SnakeGame.setGameStage(SnakeGame.GAME_OVER);
            } else {
                //AMD: otherwise, move the snake's head to the other side of the board:
                hitWall = false;
                // AMD: Need to adjust coordinates based on which wall the snake hit:
                if (snakeHeadX >= maxX || snakeHeadX < 0) {
                    // AMD: if it hit the side walls, adjust the X coordinate
                    snakeHeadX = maxX - Math.abs(snakeHeadX);
                }
                else {
                    // AMD: otherwise, it hit the top/bottom wall and we adjust the Y coordinate
                    snakeHeadY = maxY - Math.abs(snakeHeadY);
                }
                // AMD: Add the new head
                snakeSquares[snakeHeadX][snakeHeadY] = 1;
            }
            return;
        }

        //Does this make the snake eat its tail?

        if (snakeSquares[snakeHeadX][snakeHeadY] != 0) {
            ateTail = true;
            SnakeGame.setGameStage(SnakeGame.GAME_OVER);
            return;
        }

        //Otherwise, game is still on. Add new head
        snakeSquares[snakeHeadX][snakeHeadY] = 1;

        //If snake did not just eat, then remove tail segment
        //to keep snake the same length.
        //find highest number, which should now be the same as snakeSize+1, and set to 0

        if (justAteMustGrowThisMuch == 0) {
            for (int x = 0; x < maxX; x++) {
                for (int y = 0; y < maxY; y++) {
                    if (snakeSquares[x][y] == snakeSize + 1) {
                        snakeSquares[x][y] = 0;
                    }
                }
            }
        } else {
            //Snake has just eaten. leave tail as is.  Decrease justAte... variable by 1.
            justAteMustGrowThisMuch--;
            snakeSize++;
        }

        lastHeading = currentHeading; //Update last confirmed heading

        // AMD: also check to be sure the player hasn't won the game
        if (wonGame()) {
            SnakeGame.setGameStage(SnakeGame.GAME_WON);
        }
    }

    protected boolean didHitWall() {
        // AMD: Adding warpWalls means that if warpWalls are on, the snake should never hit the wall.
        return hitWall && !SnakeGame.hasWarpWalls;

    }

    protected boolean didEatTail() {
        return ateTail;
    }

    public boolean isSnakeSegment(int kibbleX, int kibbleY) {
        /*if (snakeSquares[kibbleX][kibbleY] == 0) {
            return false;
        }
        return true;*/
        // AMD: just return the evaluation, the if statement is redundant:
        return snakeSquares[kibbleX][kibbleY] == 0;
    }

    public boolean didEatKibble(Kibble kibble) {
        //Is this kibble in the snake? It should be in the same square as the snake's head
        if (kibble.getKibbleX() == snakeHeadX && kibble.getKibbleY() == snakeHeadY) {
            justAteMustGrowThisMuch += growthIncrement;
            return true;
        }
        return false;
    }

    public String toString() {
        String textsnake = "";
        //This looks the wrong way around. Actually need to do it this way or snake is drawn flipped 90 degrees.
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < maxX; x++) {
                textsnake = textsnake + snakeSquares[x][y];
            }
            textsnake += "\n";
        }
        return textsnake;
    }

    public boolean wonGame() {

        //If all of the squares have snake segments in, the snake has eaten so much kibble
        //that it has filled the screen. Win!
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                if (snakeSquares[x][y] == 0) {
                    //there is still empty space on the screen, so haven't won
                    return false;
                }
            }
        }
        //But if we get here, the snake has filled the screen. win!
        SnakeGame.setGameStage(SnakeGame.GAME_WON);

        return true;
    }

    public void reset() {
        hitWall = false;
        ateTail = false;
        fillSnakeSquaresWithZeros();
        createStartSnake();

    }

    public boolean isGameOver() {
        if (this.didHitWall() || ateTail) {
            SnakeGame.setGameStage(SnakeGame.GAME_OVER);
            return true;

        }
        return false;
    }

    public boolean didHitMazeWall() {
        // AMD: are we even using mazewalls?
        if (!SnakeGame.hasMazeWalls) { return false; }
        // AMD: has the snake hit the maze wall?
        boolean didHit = false;
        MazeWall mw = DrawSnakeGamePanel.mw1;
        // AMD: our decision depends partly on the snake's direction & partly on the line's orientation
        switch (currentHeading) {
            case DIRECTION_UP: {
                if (mw.getV_or_h() == 'h' && mw.getGridX() == this.snakeHeadX && mw.getGridY() == this.snakeHeadY) {
                    didHit = true;
                }
                break;
            }
            case DIRECTION_DOWN: {
                if (mw.getV_or_h() == 'h' && mw.getGridX() == this.snakeHeadX && mw.getGridY() == this.snakeHeadY + 1) {
                    didHit = true;
                }
                break;
            }
            case DIRECTION_LEFT: {
                if (mw.getV_or_h() == 'v' && mw.getGridX() == this.snakeHeadX && mw.getGridY() == this.snakeHeadY) {
                    didHit = true;
                }
                break;
            }
            case DIRECTION_RIGHT: {
                if (mw.getV_or_h() == 'v' && mw.getGridX() == this.snakeHeadX + 1 && mw.getGridY() == this.snakeHeadY) {
                    didHit = true;
                }
                break;
            }
        }
        return didHit;
    }

    protected void createDebugSnake() {
		/*
        AMD: adapted from createStartSnake()
        creates a large snake to debug endgame
		*/

        // our snake size is going to leave 5 empty squares for the snake to eat
        //snakeSize = (maxX * maxY) - 5;
        int freespaces = 5;
        snakeSize = maxX - freespaces;
        int segmentnum = 0;

        int cur_x_square = freespaces;
        int cur_y_square = 0;
        snakeHeadX = cur_x_square;
        snakeHeadY = cur_y_square;


        // build first row:
        for (cur_x_square = freespaces; cur_x_square < snakeSize + freespaces; cur_x_square++) {
            //System.out.println(segmentnum + ": " + cur_x_square + "," + cur_y_square);
            snakeSquares[cur_x_square][cur_y_square] = ++segmentnum;
        }
        //fill in body of snake
        while (cur_y_square < maxY - 1) {
            cur_y_square++;
            if (cur_y_square % 2 == 0) {
                for (cur_x_square = maxX - 1; cur_x_square > 0; cur_x_square--) {
                    //System.out.println(segmentnum + ": " + cur_x_square + "," + cur_y_square);
                    snakeSquares[cur_x_square][cur_y_square] = ++segmentnum;
                }
            } else {
                for (cur_x_square = 1; cur_x_square < maxX; cur_x_square++) {
                    //System.out.println(segmentnum + ": " + cur_x_square + "," + cur_y_square);
                    snakeSquares[cur_x_square][cur_y_square] = ++segmentnum;
                }
            }
        }

        //fill in tail so it can be chased by the player as they eat the last few
        // kibbles that appear
        for (cur_y_square = maxY - 1; cur_y_square > 0; cur_y_square--) {
            //System.out.println(segmentnum + ": " + cur_x_square + "," + cur_y_square);
            snakeSquares[0][cur_y_square] = ++segmentnum;
        }

        snakeSize = segmentnum;

        currentHeading = DIRECTION_LEFT;
        lastHeading = DIRECTION_LEFT;

        justAteMustGrowThisMuch = 0;
    }


}


