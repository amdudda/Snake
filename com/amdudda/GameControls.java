package com.amdudda;

/*
	@student A.M. Dudda
* */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameControls implements KeyListener{

	private Snake snake;
    // AMD: global variable to track current gamestage to gatekeep response to keyTyped event.
	private int gStage;

	GameControls(Snake s){
		this.snake = s;
	}

	public void keyPressed(KeyEvent ev) {
        // AMD: Update value of gStage at every keyPressed event.
        gStage = SnakeGame.getGameStage();
		//keyPressed events are for catching events like function keys, enter, arrow keys
		//We want to listen for arrow keys to move snake
		//Has to id if user pressed arrow key, and if so, send info to Snake object

		//is game running? No? tell the game to draw grid, start, etc.
		
		//Get the component which generated this event
		//Hopefully, a DrawSnakeGamePanel object.
		//It would be a good idea to catch a ClassCastException here. 
		
		DrawSnakeGamePanel panel = (DrawSnakeGamePanel)ev.getComponent();

		if (SnakeGame.getGameStage() == SnakeGame.BEFORE_GAME){
			//Start the game
			SnakeGame.setGameStage(SnakeGame.DURING_GAME);
			SnakeGame.newGame();
			panel.repaint();
			return;
		}
		
		if (SnakeGame.getGameStage() == SnakeGame.GAME_OVER){
            // AMD: turn off the timer when the game is over
            SnakeGame.timer.cancel();
			snake.reset();
			Score.resetScore();
			
			//Need to start the timer and start the game again
			SnakeGame.newGame();
			SnakeGame.setGameStage(SnakeGame.DURING_GAME);
			panel.repaint();
			return;
		}


		if (ev.getKeyCode() == KeyEvent.VK_DOWN) {
			//System.out.println("snake down");
			snake.snakeDown();
		}
		if (ev.getKeyCode() == KeyEvent.VK_UP) {
			//System.out.println("snake up");
			snake.snakeUp();
		}
		if (ev.getKeyCode() == KeyEvent.VK_LEFT) {
			//System.out.println("snake left");
			snake.snakeLeft();
		}
		if (ev.getKeyCode() == KeyEvent.VK_RIGHT) {
			//System.out.println("snake right");
			snake.snakeRight();
		}

	}


	@Override
	public void keyReleased(KeyEvent ev) {
		//We don't care about keyReleased events, but are required to implement this method anyway.		
	}


	@Override
	public void keyTyped(KeyEvent ev) {
		//keyTyped events are for user typing letters on the keyboard, anything that makes a character display on the screen
		char keyPressed = ev.getKeyChar();
		char q = 'q';
		char capitalQ = 'Q';  // make commands case-insensitive.
		char o = 'o';
		char capitalO = 'O';
		// AMD: prevent game from quitting at keypress q during gameplay
		if( (keyPressed == q || keyPressed == capitalQ) && gStage != SnakeGame.DURING_GAME){
			System.exit(0);    //quit if user presses the q key.
			//FINDBUGS: flags this, but honestly, in this case, this is exactly what we want.
		}
		//  AMD: Let user customize game settings before starting play.
		else if ( (keyPressed == o || keyPressed == capitalO) && gStage == SnakeGame.BEFORE_GAME) {
			// If 'o' is typed, present the options menu.
            SnakeGame.setGameStage(SnakeGame.SET_OPTIONS);
            OptionsPanel opts = new OptionsPanel();
		}
	}

}
