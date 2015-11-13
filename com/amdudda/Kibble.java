package com.amdudda;

/*
	@student A.M. Dudda
* */

import java.awt.*;
import java.util.Random;

public abstract class Kibble {

	/** Identifies a random square to display a kibble
	 * Any square is ok, so long as it doesn't have any snake segments in it. 
	 * 
	 */
	
	protected int kibbleX; //This is the square number (not pixel)
	protected int kibbleY;  //This is the square number (not pixel)

	// kibble objects must have a way to draw an image and a way to report their fallback color for when the image is missing
    public abstract Color getFallBackColor();
    public abstract void drawImage(Graphics q);


	public Kibble(Snake s){
		//Kibble needs to know where the snake is, so it does not create a kibble in the snake
		//Pick a random location for kibble, check if it is in the snake
		//If in snake, try again
		
		moveKibble(s);
		/*this.imageLocation = "./data/mouse.jpg";
		this.fallbackColor = Color.GREEN;
		// AMD: set up a mouse image to use for kibble
		try {
			this.img = ImageIO.read(new File(imageLocation));
			this.validImage = true;
		} catch (IOException e) {
			// System.out.println("Mouse not found!");
			// draw the generic kibble instead.
			this.validImage = false;
		}*/
	}
	
	protected void moveKibble(Snake s){
		
		Random rng = new Random();
		boolean kibbleInSnake = true;
		// AMD: need to stop building kibble if the game has been won.
		while (kibbleInSnake && SnakeGame.getGameStage() != SnakeGame.GAME_WON) {
			//Generate random kibble location
			kibbleX = rng.nextInt(SnakeGame.getxSquares());
			kibbleY = rng.nextInt(SnakeGame.getySquares());
			kibbleInSnake = s.isSnakeSegment(kibbleX, kibbleY);
        }
		
		
	}

	public int getKibbleX() {
		return kibbleX;
	}

	public int getKibbleY() {
		return kibbleY;
	}

	public void draw(Graphics q) {
		// AMD: draws the kibble
		//Draw the kibble in green
		q.setColor(this.getFallBackColor());
		int sqSz = SnakeGame.getSquareSize();
		int x = this.kibbleX * sqSz;
		int y = this.kibbleY * sqSz;
		// AMD: offsets by 1 & 2 allow kibble to occur inside the grid squares.
		q.fillRect(x + 1, y + 1, sqSz - 2, sqSz - 2);
	}

}
