package com.amdudda;

import javax.swing.*;
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
    private JCheckBox WarpWallsCheckBox;
    private JCheckBox MazeWallCheckBox;

    public OptionsPanel() {
        super("Set game options");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        // turn off the timer so that the snake doesn't get weird speed effects.
        SnakeGame.timer.cancel();

        gameSpeedText.setText("" + SnakeGame.clockInterval);
        squareSizeText.setText("" + SnakeGame.squareSize);

        /* DONE: fix bug
        Something here breaks the game - it works fine if I set no options,
        but just opening the options screen & closing it causes the game to
        end abruptly after ~10 clock ticks.
        Timer also gets really wonky if I reset settings.
        */
        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGame.clockInterval = Integer.parseInt(gameSpeedText.getText());
                SnakeGame.squareSize = Integer.parseInt(squareSizeText.getText());
                if (WarpWallsCheckBox.isSelected()) SnakeGame.hasWarpWalls = true;
                else SnakeGame.hasWarpWalls = false;
                if (MazeWallCheckBox.isSelected()) SnakeGame.hasMazeWalls = true;
                else SnakeGame.hasMazeWalls = false;
                // reset snake so the new settings take effect.
                SnakeGame.snake.reset();
                // and get rid of the screen
                closeWindow();
            }
        });
    }

    public void closeWindow() {
        // snagged from stack overflow: http://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        SnakeGame.setGameStage(SnakeGame.BEFORE_GAME);
    }
}
