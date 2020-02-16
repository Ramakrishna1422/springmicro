package com.lifesight.service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pixel {
    private int sizeX = 0;
    private int sizeY = 0;
    private String name;

    public Pixel(int x, int y, String name) {
        this.setSizeX(x);
        this.setSizeY(y);
        this.setName(name);
    }

    public Pixel(String name) {
        this.setName(name);
    }

    public Pixel() {
        this("ZeroPixel");
    }
}
