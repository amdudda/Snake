package com.amdudda;

import javax.swing.*;

/**
 * Created by amdudda on 11/12/2015.
 */
public class SnakeGameWindow extends JFrame {
    // this is the window that is inside the entire game
    JFrame snakeWindow;

    // constructor
    public SnakeGameWindow(Snake snake, Kibble kibble, Score game_score) {
        snakeWindow = new JFrame();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(SnakeGame.xPixelMaxDimension, SnakeGame.yPixelMaxDimension);
        this.setUndecorated(false); // AMD: Show title bar so game can be moved around screen //hide title bar
        this.setTitle("Snake Game: feed the snake and avoid the walls!");
        this.setVisible(true);
        this.setResizable(false);

        SnakeGame.snakePanel = new DrawSnakeGamePanel(snake, kibble, game_score);
        SnakeGame.snakePanel.setFocusable(true);
        SnakeGame.snakePanel.requestFocusInWindow(); //required to give this component the focus so it can generate KeyEvents

        this.add(SnakeGame.snakePanel);
        SnakeGame.snakePanel.addKeyListener(new GameControls(snake));

        SnakeGame.setGameStage(SnakeGame.BEFORE_GAME);

        this.setVisible(true);
        this.resize(); // need to make sure grid squares are all fully visible.
    }

    public void resize() {
        /*
        AMD: trying to get draggable window that doesn't hide bottom row(s) of board
        https://home.java.net/node/650887
        http://stackoverflow.com/questions/12803963/how-can-i-get-the-height-of-the-title-bar-of-a-jinternalframe
        harrumph, that's annoying:
        http://www.coderanch.com/t/333985/GUI/java/getInsets-Frames
        */
        int titlebarheight = SnakeGameWindow.this.getInsets().top + SnakeGameWindow.this.getInsets().bottom;
        int borders = SnakeGameWindow.this.getInsets().left + SnakeGameWindow.this.getInsets().right;

        SnakeGameWindow.this.setSize(SnakeGame.xPixelMaxDimension + borders, SnakeGame.yPixelMaxDimension+titlebarheight);
    }
}
