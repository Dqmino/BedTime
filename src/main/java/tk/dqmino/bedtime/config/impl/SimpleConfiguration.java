package tk.dqmino.bedtime.config.impl;

import tk.dqmino.bedtime.config.Configuration;

public class SimpleConfiguration implements Configuration {

    private int x = 0;
    private int y = 0;
    private double size = 2.1;
    private boolean enabled = true;

    @Override
    public boolean isModEnabled() {
        return enabled;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public double getSize() {
        return size;
    }

    @Override
    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public void setEnable(boolean bool) {
        this.enabled = bool;
    }

}
