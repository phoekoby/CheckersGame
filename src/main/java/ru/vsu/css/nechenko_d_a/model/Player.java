package ru.vsu.css.nechenko_d_a.model;

import java.awt.*;

public class Player {
    private String name;
    private Color color;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
