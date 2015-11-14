package com.amdudda;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by amdudda on 11/11/15.
 */
public class Axe extends Kibble {
    // AMD: variables to help store the image associated with Kibble
    private BufferedImage img;
    private boolean validImage;
    private static String imageLocation = "./data/axe.jpg";
    private static Color fallbackColor;
    // it's theoretically possible you might have multiple axes with different visibility flags,
    // though that should never happen in this version of the game.
    private boolean visible;

    public Axe(Snake s) {
        super(s);
        moveKibble(s);
        this.fallbackColor = Color.RED;
        this.visible = false;
        // need this code so that it draws the correct image:
        try {
            this.img = ImageIO.read(new File(imageLocation));
            this.validImage = true;
        } catch (IOException e) {
            // System.out.println("Mouse not found!");
            // draw the generic kibble instead.
            this.validImage = false;
        }
    }

    public boolean isVisible() { return this.visible; }

    public void setVisible(boolean tf) { this.visible = tf; }


    public void drawImage(Graphics q) {
        // AMD: let's draw a mouse for the snake to eat, instead of green kibble.
        int sqSz = SnakeGame.getSquareSize();
        int x = this.kibbleX * sqSz;
        int y = this.kibbleY * sqSz;
        /*
        taken from:
        http://docs.oracle.com/javase/tutorial/2d/images/loadimage.html
        http://docs.oracle.com/javase/tutorial/2d/images/drawimage.html

        image scaling hints from:
        http://stackoverflow.com/questions/8284048/resizing-an-image-in-swing
        */

        if (validImage) {
            q.drawImage(this.img.getScaledInstance(sqSz - 2, sqSz - 2, Image.SCALE_FAST), x + 1, y + 1, null);
        } else {
            this.draw(q);
        }
    }

    public Color getFallBackColor() {
        return this.fallbackColor;
    }
}
