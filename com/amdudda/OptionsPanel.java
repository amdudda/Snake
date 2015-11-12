package com.amdudda;

import javax.swing.*;
import java.awt.event.*;

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
    private JCheckBox extendedFeaturesCheckBox;
    private JComboBox snakeColorComboBox;
    private static final String[] COLOR_OPTIONS = {"Pink head and red body","Cyan head and blue body","Mint head with green body",
            "Pale yellow head with yellow body","Gray head with black body"};

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

        setupForm();

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
                // Update snake color & refactor snake so the new settings take effect.
                SnakeGame.snake.setSnakeColor(snakeColorComboBox.getSelectedIndex());
                SnakeGame.snake.refactor();
                // and get rid of the screen
                closeWindow();
            }
        });

        MazeWallCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // turn on/ off extended options check box
                OptionsPanel.this.extendedFeaturesCheckBox.setEnabled(OptionsPanel.this.MazeWallCheckBox.isSelected());
            }
        });

    }

    private void setupForm() {
        // Establish our button groups and set default options
        OptionsPanel.this.GameSpeedButtonGroup.add(slowRadioButton);
        OptionsPanel.this.GameSpeedButtonGroup.add(mediumRadioButton);
        OptionsPanel.this.GameSpeedButtonGroup.add(fastRadioButton);
        OptionsPanel.this.GameSpeedButtonGroup.add(hyperspeedRadioButton);
        OptionsPanel.this.GameSpeedButtonGroup.setSelected(mediumRadioButton.getModel(), true);

        OptionsPanel.this.SquareSizeButtonGroup.add(a10x10RadioButton);
        OptionsPanel.this.SquareSizeButtonGroup.add(a20x20RadioButton);
        OptionsPanel.this.SquareSizeButtonGroup.add(a50x50RadioButton);
        OptionsPanel.this.SquareSizeButtonGroup.setSelected(a10x10RadioButton.getModel(), true);

        OptionsPanel.this.ScrnSzButtonGroup.add(ScrnSzSmallradioButton);
        OptionsPanel.this.ScrnSzButtonGroup.add(ScrnSzMediumradioButton);
        OptionsPanel.this.ScrnSzButtonGroup.add(ScrnSzLargeradioButton);
        OptionsPanel.this.ScrnSzButtonGroup.setSelected(ScrnSzSmallradioButton.getModel(),true);

        // disable the extended features tickbox
        OptionsPanel.this.extendedFeaturesCheckBox.setEnabled(false);

        // populate the values for snake color drop box
        for (int j = 0; j < COLOR_OPTIONS.length; j++) {
            OptionsPanel.this.snakeColorComboBox.addItem(COLOR_OPTIONS[j]);
        }
    }

    public void resetGameVariables() {
        // resets game variables after options have been updated
        int game_speed = Integer.parseInt(OptionsPanel.this.GameSpeedButtonGroup.getSelection().getActionCommand());
        double sizeRatio = Double.parseDouble(OptionsPanel.this.ScrnSzButtonGroup.getSelection().getActionCommand());
        int sqSize = (int) (Integer.parseInt(OptionsPanel.this.SquareSizeButtonGroup.getSelection().getActionCommand()) * sizeRatio);
        int ScreenSize = (int) (SnakeGame.INITIAL_GAME_SIZE * sizeRatio) + 1; // for this game, x = y
        SnakeGame.clockInterval = game_speed;
        SnakeGame.squareSize = sqSize;
        SnakeGame.xPixelMaxDimension = ScreenSize;
        SnakeGame.yPixelMaxDimension = ScreenSize;
        SnakeGame.xSquares = ScreenSize / sqSize;
        SnakeGame.ySquares = ScreenSize / sqSize;
        SnakeGame.hasWarpWalls = OptionsPanel.this.WarpWallsCheckBox.isSelected();
        if (OptionsPanel.this.MazeWallCheckBox.isSelected()) {
            SnakeGame.hasMazeWalls = true;
            SnakeGame.enableExtendedFeatures = OptionsPanel.this.extendedFeaturesCheckBox.isSelected();
            //DrawSnakeGamePanel.mw1 = new MazeWall();
        }
        else {
            SnakeGame.hasMazeWalls = false;
            // being careful - extended features turned on IIF mazewalls are enabled
            SnakeGame.enableExtendedFeatures = false;
        }
        // and don't forget to resize the game screen, too!  Done here to take advantage of local variables.
        SnakeGame.snakeFrame.setSize(ScreenSize, ScreenSize);

    }

    public void closeWindow() {
        // snagged from stack overflow: http://stackoverflow.com/questions/1234912/how-to-programmatically-close-a-jframe
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        SnakeGame.setGameStage(SnakeGame.BEFORE_GAME);
    }


}
