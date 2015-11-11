package com.amdudda;

/*
	@student A.M. Dudda
* */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Kibble {

	/** Identifies a random square to display a kibble
	 * Any square is ok, so long as it doesn't have any snake segments in it. 
	 * 
	 */
	
	protected int kibbleX; //This is the square number (not pixel)
	protected int kibbleY;  //This is the square number (not pixel)
	// AMD: variables to help store the image associated with Kibble
	protected BufferedImage img;
	protected boolean validImage;
	protected String imageLocation;
	
	public Kibble(Snake s){
		//Kibble needs to know where the snake is, so it does not create a kibble in the snake
		//Pick a random location for kibble, check if it is in the snake
		//If in snake, try again
		
		moveKibble(s);
		this.imageLocation = "./data/mouse.jpg";
		// AMD: set up a mouse image to use for kibble
		try {
			this.img = ImageIO.read(new File(imageLocation));
			this.validImage = true;
		} catch (IOException e) {
			// System.out.println("Mouse not found!");
			// draw the generic kibble instead.
			this.validImage = false;
		}
	}
	
	protected void moveKibble(Snake s){
		
		Random rng = new Random();
		boolean kibbleInSnake = true;
		// AMD: need to stop building kibble if the game has been won.
		while (kibbleInSnake && SnakeGame.getGameStage() != SnakeGame.GAME_WON) {
			//Generate random kibble location
			kibbleX = rng.nextInt(SnakeGame.xSquares);
			kibbleY = rng.nextInt(SnakeGame.ySquares);
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
		q.setColor(Color.GREEN);

		int x = this.kibbleX * SnakeGame.squareSize;
		int y = this.kibbleY * SnakeGame.squareSize;
		// AMD: offsets by 1 & 2 allow kibble to occur inside the grid squares.
		q.fillRect(x + 1, y + 1, SnakeGame.squareSize - 2, SnakeGame.squareSize - 2);
	}

    public void drawImage(Graphics q) {
        // AMD: let's draw a mouse for the snake to eat, instead of green kibble.
        int x = this.kibbleX * SnakeGame.squareSize;
        int y = this.kibbleY * SnakeGame.squareSize;
        /*
        taken from:
        http://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
        http://docs.oracle.com/javase/tutorial/2d/images/drawimage.html

        image scaling hints from:
        http://stackoverflow.com/questions/8284048/resizing-an-image-in-swing
        */

		if (validImage) {
			q.drawImage(this.img.getScaledInstance(SnakeGame.squareSize - 2, SnakeGame.squareSize - 2, Image.SCALE_FAST), x + 1, y + 1, null);
		} else {
			this.draw(q);
		}
    }
}
