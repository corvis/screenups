package com.logicify.screenups.common;

import java.awt.image.BufferedImage;

/**
 * Created by LOGICIFY\corvis on 7/12/14.
 */
public class Frame {
    private BufferedImage imageData;
    private long time;

    public BufferedImage getImageData() {
        return imageData;
    }

    public void setImageData(BufferedImage imageData) {
        this.imageData = imageData;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
