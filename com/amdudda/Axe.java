package com.amdudda;

/**
 * Created by amdudda on 11/11/15.
 */
public class Axe extends Kibble {

    public Axe(Snake s) {
        super(s);
        moveKibble(s);
        this.imageLocation = "./data/axe.jpg";
    }
}
