package com.amdudda;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by amdudda on 11/11/15.
 */
public class Axe extends Kibble {

    private boolean visible;

    public Axe(Snake s) {
        super(s);
        moveKibble(s);
        this.imageLocation = "./data/axe.jpg";
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

}
