package com.amdudda;

/**
 * Created by amdudda on 11/11/15.
 */
public class Axe extends Kibble {

    private boolean visible;

    public Axe(Snake s) {
        super(s);
        moveKibble(s);
        this.imageLocation = "./data/axe.jpg";  // TODO: not actually showing axe, showing mouse instead.
        this.visible = false;
    }

    public boolean isVisible() { return this.visible; }

    public void setVisible(boolean tf) { this.visible = tf; }

}
