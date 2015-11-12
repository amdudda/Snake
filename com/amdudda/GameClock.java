package com.amdudda;

import java.util.TimerTask;

/*
	@student A.M. Dudda
* */

public class GameClock extends TimerTask {

	Snake snake;
	Kibble kibble;
	Score score;
	DrawSnakeGamePanel gamePanel;
	Axe axe;
		
	public GameClock(Snake snake, Kibble kibble, Score score, DrawSnakeGamePanel gamePanel, Axe game_axe){
		this.snake = snake;
		this.kibble = kibble;
		this.score = score;
		this.gamePanel = gamePanel;
		this.axe = game_axe;
	}
	
	@Override
	public void run() {
		// This method will be called every clock tick
						
		int stage = SnakeGame.getGameStage();

		switch (stage) {
			case SnakeGame.BEFORE_GAME:
			case SnakeGame.SET_OPTIONS: {
				//don't do anything, waiting for user to press a key to start
				break;
			}
			case SnakeGame.DURING_GAME: {
				// AMD: play the game
				snake.moveSnake();
				if (snake.didEatKibble(kibble)) {
					//tell kibble to update
					kibble.moveKibble(snake);
					Score.increaseScore();
				}
				if (snake.didEatAxe(axe)) {
					// tell axe to hide itself, move, and shrink the snake
					axe.setVisible(false);
					axe.moveKibble(snake);
					// TODO: shrink the snake!
				}
				break;
			}
			case SnakeGame.GAME_OVER: {
                this.cancel();		//Stop the Timer
				break;	
			}
			case SnakeGame.GAME_WON: {
				this.cancel();   //stop timer
				break;
			}
		
		}
				
		gamePanel.repaint();		//In every circumstance, must update screen

	}

}
