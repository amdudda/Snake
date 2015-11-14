package com.amdudda;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by amdudda on 11/13/15.
 */
public class Mouse extends Kibble {
    // so we can have different default attributes for Mouse kibble and Axe kibble.
    // AMD: variables to help store the image associated with Kibble
    private BufferedImage img;
    private boolean validImage;
    private static String imageLocation = "./data/mouse2.jpg";
    private static Color fallbackColor = Color.GREEN;

    public Mouse(Snake s) {
        super(s);
        moveKibble(s);
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
