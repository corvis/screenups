package com.logicify.screenups.common.xuggleimpl.desktop;

import com.logicify.screenups.common.Frame;
import com.logicify.screenups.common.FrameProcessor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LOGICIFY\corvis on 10/20/14.
 */
public class DrawCursorFrameProcessor implements FrameProcessor {

    private BufferedImage cursorImage = null;
    private BufferedImage leftClickIcon = null;
    private boolean highlightCursor = true;

    /**
     * Performs post-processing of the given frame.
     *
     * @param frame
     */
    @Override
    public void processFrame(Frame frame) {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point location = pointerInfo.getLocation();
        Graphics2D graphics = frame.getImageData().createGraphics();
        try {
            if (highlightCursor && cursorImage != null) {
                drawCursorHighlight(location, graphics);
            }
            if (cursorImage != null) {
                graphics.drawImage(getCursorImage(), location.x, location.y, null);
            }

        } finally {
            if (graphics != null) {
                graphics.dispose();
            }
        }
    }

    protected void drawCursorHighlight(Point mouseLocation, Graphics2D graphics) {
        int cursorSize = Math.max(getCursorImage().getWidth(), getCursorImage().getHeight());
        graphics.setColor(new Color(255,255,0, 128));
        graphics.fillOval(mouseLocation.x - cursorSize + getCursorImage().getWidth() / 2,
                          mouseLocation.y - getCursorImage().getHeight() / 2,
                          2 * cursorSize, 2 * cursorSize);
    }

    public BufferedImage getCursorImage() {
        return cursorImage;
    }
    public BufferedImage getLeftClickIcon() {
        return leftClickIcon;
    }

    public void setCursorImage(BufferedImage image) {
        this.cursorImage = image;
    }
    public void setCursorImage(File image) throws IOException {
        setCursorImage(ImageIO.read(image));
    }

    public void setCursorImage(InputStream image) throws IOException {
        setCursorImage(ImageIO.read(image));
    }

    public void setLeftClickIcon(BufferedImage image) { this.leftClickIcon = image; }

    public void setLeftClickIcon(File image) throws IOException { setLeftClickIcon(ImageIO.read(image)); }

    public void setLeftClickIcon(InputStream image) throws IOException { setLeftClickIcon(ImageIO.read(image)); }

    public boolean isHighlightCursor() {
        return highlightCursor;
    }

    public void setHighlightCursor(boolean highlightCursor) {
        this.highlightCursor = highlightCursor;
    }
}
