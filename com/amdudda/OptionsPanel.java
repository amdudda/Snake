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
    private JButton SubmitButton;
    private JCheckBox WarpWallsCheckBox;
    private JCheckBox MazeWallCheckBox;
    private JRadioButton slowRadioButton;
    private JRadioButton mediumRadioButton;
    private JRadioButton fastRadioButton;
    private JRadioButton hyperspeedRadioButton;
    private JRadioButton a10x10RadioButton;
    private JRadioButton a20x20RadioButton;
    private JRadioButton a50x50RadioButton;
    private JRadioButton ScrnSzSmallradioButton;
    private JRadioButton ScrnSzMediumradioButton;
    private JRadioButton ScrnSzLargeradioButton;

    /*
    got buttongroup usage info from two sites:
    http://docs.oracle.com/javase/tutorial/uiswing/components/button.html#radiobutton
    https://docs.oracle.com/javase/8/docs/api/javax/swing/ButtonGroup.html#ButtonGroup--
    */
    private ButtonGroup GameSpeedButtonGroup = new ButtonGroup();
    private ButtonGroup SquareSizeButtonGroup = new ButtonGroup();
    private ButtonGroup ScrnSzButtonGroup = new ButtonGroup();


    public OptionsPanel() {
        super("Set game options");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        // turn off the timer so that the snake doesn't get weird speed effects.
        SnakeGame.timer.cancel();

        // Establish our button groups and set default options
        GameSpeedButtonGroup.add(slowRadioButton);
        GameSpeedButtonGroup.add(mediumRadioButton);
        GameSpeedButtonGroup.add(fastRadioButton);
        GameSpeedButtonGroup.add(hyperspeedRadioButton);
        GameSpeedButtonGroup.setSelected(mediumRadioButton.getModel(), true);

        SquareSizeButtonGroup.add(a10x10RadioButton);
        SquareSizeButtonGroup.add(a20x20RadioButton);
        SquareSizeButtonGroup.add(a50x50RadioButton);
        SquareSizeButtonGroup.setSelected(a10x10RadioButton.getModel(), true);

        ScrnSzButtonGroup.add(ScrnSzSmallradioButton);
        ScrnSzButtonGroup.add(ScrnSzMediumradioButton);
        ScrnSzButtonGroup.add(ScrnSzLargeradioButton);
        ScrnSzButtonGroup.setSelected(ScrnSzSmallradioButton.getModel(),true);


        /* DONE: fix bug
        Something here breaks the game - it works fine if I set no options,
        but just opening the options screen & closing it causes the game to
        end abruptly after ~10 clock ticks.
        Timer also gets really wonky if I reset settings.
        */
        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGameVariables();
                // reset snake so the new settings take effect.
                SnakeGame.snake.refactor();
                // and get rid of the screen
                closeWindow();
            }
        });

    }

    public void resetGameVariables() {
        // resets game variables after options have been updated
        int game_speed = Integer.parseInt(GameSpeedButtonGroup.getSelection().getActionCommand());
        double sizeRatio = Double.parseDouble(ScrnSzButtonGroup.getSelection().getActionCommand());
        int sqSize = (int) (Integer.parseInt(SquareSizeButtonGroup.getSelection().getActionCommand()) * sizeRatio);
        int ScreenSize = (int) (SnakeGame.xPixelMaxDimension * sizeRatio); // for this game, x = y
        SnakeGame.clockInterval = game_speed;
        SnakeGame.squareSize = sqSize;
        SnakeGame.xPixelMaxDimension = ScreenSize;
        SnakeGame.yPixelMaxDimension = ScreenSize;
        SnakeGame.xSquares = ScreenSize / sqSize;
        SnakeGame.ySquares = ScreenSize / sqSize;
        if (WarpWallsCheckBox.isSelected()) SnakeGame.hasWarpWalls = true;
        else SnakeGame.hasWarpWalls = false;
        if (MazeWallCheckBox.isSelected()) {
            SnakeGame.hasMazeWalls = true;
            //DrawSnakeGamePanel.mw1 = new MazeWall();
        }
        else SnakeGame.hasMazeWalls = false;
        // and don't forget to resize the game screen, too!  Done here to take advantage of local variables.
        SnakeGame.snakeFrame.setSize(ScreenSize, ScreenSize);
        System.out.println(ScreenSize + "/" + sqSize + " = " + SnakeGame.xSquares);
    }

    public void closeWindow() {
        // snagged from stack overflow: http://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        SnakeGame.setGameStage(SnakeGame.BEFORE_GAME);
    }
}
