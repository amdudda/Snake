package com.amdudda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by amdudda on 11/1/2015.
 */
public class OptionsPanel extends JFrame {
    private JPanel rootPanel;
    private JTextField gameSpeedText;
    private JTextField squareSizeText;
    private JButton SubmitButton;

    public OptionsPanel() {
        super("Set game options");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);


        gameSpeedText.setText("" + SnakeGame.clockInterval);
        squareSizeText.setText("" + SnakeGame.squareSize);

        /* TODO: fix bug
        Something here breaks the game - it works fine if I set no options,
        but just opening the options screen & closing it causes the game to
        end abruptly after ~10 clock ticks.
        */
        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGame.clockInterval = Integer.parseInt(gameSpeedText.getText());
                SnakeGame.squareSize = Integer.parseInt(squareSizeText.getText());
                // reinitialize snake so the new settings take effect.
                SnakeGame.initializeGame();
                /*int xSquares = SnakeGame.xPixelMaxDimension / SnakeGame.squareSize;
                int ySquares = SnakeGame.yPixelMaxDimension / SnakeGame.squareSize;
                SnakeGame.snake = new Snake(xSquares, ySquares, SnakeGame.squareSize);
                SnakeGame.setGameStage(SnakeGame.BEFORE_GAME);*/
                // and get rid of the screen & restart timer
                //dispose();
                closeWindow();
            }
        });
    }

    public void closeWindow() {
        // snagged from stack overflow: http://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }
}
