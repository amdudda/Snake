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
		
	public GameClock(Snake snake, Kibble kibble, Score score, DrawSnakeGamePanel gamePanel){
		this.snake = snake;
		this.kibble = kibble;
		this.score = score;
		this.gamePanel = gamePanel;
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
				if (snake.didEatKibble(kibble) == true) {		
					//tell kibble to update
					kibble.moveKibble(snake);
					Score.increaseScore();
				}
				break;
			}
			case SnakeGame.GAME_OVER: {
                /*// AMD: debugging -- this is going to game over even though hitwall and atetail are both false!
                System.out.println(SnakeGame.getGameStage());
                System.out.println("hitwall: " + snake.didHitWall() + "atetail: " + snake.didEatTail());*/
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
