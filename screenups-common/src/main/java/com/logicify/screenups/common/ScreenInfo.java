package com.logicify.screenups.common;

import java.awt.*;

/**
 * Created by LOGICIFY\corvis on 10/20/14.
 */
public class ScreenInfo {
    private String screenName;
    private GraphicsDevice device;
    private Rectangle bounds;

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public GraphicsDevice getDevice() {
        return device;
    }

    public void setDevice(GraphicsDevice device) {
        this.device = device;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public Dimension getSize() {
        return bounds != null ? bounds.getSize() : null;
    }

    public Point getPosition() {
        return bounds != null ? bounds.getLocation() : null;

    }
}
