package com.logicify.screenups.shproto;

import com.logicify.screenups.common.xuggleimpl.XuggleVideoSource;
import com.logicify.screenups.common.xuggleimpl.desktop.DesktopScreenFrameSource;
import com.logicify.screenups.common.xuggleimpl.desktop.DrawCursorFrameProcessor;

import java.awt.*;
import java.io.*;

/**
 * Created by LOGICIFY\corvis on 7/12/14.
 */
public class EntryPoint {

    public static void main(String[] args) {
        try {
            File outputFile = new File("test.flv");
            if (!outputFile.exists()) {
                outputFile.createNewFile();
            }
            //ByteArrayOutputStream outputStream = new ByteArrayOutputStream(10*1024*1024);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            DesktopScreenFrameSource frameSource = new DesktopScreenFrameSource();
            XuggleVideoSource xuggleVideoSource = new XuggleVideoSource();
            xuggleVideoSource.setFrameRate(30);
            DrawCursorFrameProcessor drawCursorFrameProcessor = new DrawCursorFrameProcessor();
            drawCursorFrameProcessor.setCursorImage(EntryPoint.class.getResourceAsStream("/cursor.png"));
            drawCursorFrameProcessor.setLeftClickIcon(EntryPoint.class.getResourceAsStream("/asterisk-red.png"));
            xuggleVideoSource.getFrameProcessors().add(drawCursorFrameProcessor);
            xuggleVideoSource.setOutputStream(outputStream);
            Dimension resolution = frameSource.getCaptureArea().getSize();
            float coef = 1;
            resolution.width *= coef;
            resolution.height *= coef;
            xuggleVideoSource.setVideoSize(resolution);
            xuggleVideoSource.setFrameSource(frameSource);
            xuggleVideoSource.run();
            Thread.sleep(10*1000);
            xuggleVideoSource.stop();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
