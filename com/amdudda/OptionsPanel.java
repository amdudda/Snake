package com.amdudda;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by amdudda on 11/1/2015.
 */
public class OptionsPanel extends JFrame {
    private JPanel rootPanel;
    private JTextField gameSpeedText;
    private JTextField gridSizeText;
    private JButton SubmitButton;

    public OptionsPanel() {
        super("Set game options");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        int gridSquares = SnakeGame.xPixelMaxDimension/SnakeGame.squareSize;
        gameSpeedText.setText("" + SnakeGame.clockInterval);
        gridSizeText.setText("" + gridSquares);

        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SnakeGame.clockInterval = Integer.parseInt(gameSpeedText.getText());
                int newsize = Integer.parseInt(gridSizeText.getText());
                newsize = (newsize*SnakeGame.squareSize) + 1;
                SnakeGame.xPixelMaxDimension = newsize;
                SnakeGame.yPixelMaxDimension = newsize;
                // don't forget to set game stage back to "before game"
                SnakeGame.setGameStage(SnakeGame.BEFORE_GAME);
                dispose();
            }
        });
    }

}
