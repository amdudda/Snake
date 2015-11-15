package com.amdudda;

import java.util.TimerTask;

/*
	@student A.M. Dudda
* */

public class GameClock extends TimerTask {

	Snake snake;
	Kibble kibble;
	//FINDBUGS: GameClock doesn't care at all about score -- Score score;
	DrawSnakeGamePanel gamePanel;
	Axe axe;
		
	public GameClock(Snake snake, Kibble kibble, DrawSnakeGamePanel gamePanel, Axe game_axe){
		this.snake = snake;
		this.kibble = kibble;
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
                    // AMD: and turn off the axe because it only lasts until a kibble is eaten
                    axe.setVisible(false);
				}
				if (snake.didEatAxe(axe)) {
					// AMD: tell axe to hide itself & move - snake takes care of shrinking itself
					axe.setVisible(false);
					axe.moveKibble(snake);
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
		//FINDBUGS: another default case!
			default: break;
		}
				
		gamePanel.repaint();		//In every circumstance, must update screen

	}

}
