package com.logicify.screenups.common.xuggleimpl.desktop;

import com.logicify.screenups.common.Frame;
import com.logicify.screenups.common.FrameSource;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by LOGICIFY\corvis on 7/12/14.
 */
public class DesktopScreenFrameSource implements FrameSource {

    private Rectangle captureArea;
    private Robot robot;

    public DesktopScreenFrameSource() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        captureArea = new Rectangle(0, 0, screenSize.width, screenSize.height);
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException("Unable to screen capture module", e);
        }
    }

    /**
     * Generates and returns next frame.
     *
     * @return
     */
    @Override
    public Frame nextFrame() {
        BufferedImage image = robot.createScreenCapture(captureArea);
        Frame frame = new Frame();
        frame.setImageData(image);
        return frame;
    }

    public Rectangle getCaptureArea() {
        return captureArea;
    }

    public void setCaptureArea(Rectangle captureArea) {
        this.captureArea = captureArea;
    }
}
