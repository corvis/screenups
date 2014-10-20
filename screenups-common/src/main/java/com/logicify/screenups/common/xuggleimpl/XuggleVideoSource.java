package com.logicify.screenups.common.xuggleimpl;

import com.logicify.screenups.common.*;
import com.logicify.screenups.common.Frame;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.*;
import com.xuggle.xuggler.io.XugglerIO;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * VideoSource implementation which relies on Xuggler library to manage with video stream.
 *
 * Created by LOGICIFY\corvis on 7/12/14.
 */
public class XuggleVideoSource implements VideoSource {
    private AtomicBoolean running = new AtomicBoolean(false);
    private boolean initialized = false;
    private double frameRate = 30.0;
    private List<FrameProcessor> frameProcessors = new ArrayList<FrameProcessor>();
    private FrameSource frameSource;
    private Dimension outputResolution = new Dimension(800, 600);

    private OutputStream outputStream;
    private Thread workerThread;
    private IMediaWriter mWriter;
    private VideoSourceCallback beforeFrameRecordedCallback, afterFrameRecordedCallback;

    private class VideoSourceWorker implements Runnable {
        private long startTime = 0;

        @Override
        public void run() {
            startTime = System.nanoTime();
            while (isRunning()) {
                try {
                    step();
                    Thread.sleep((long) (1000 / getFrameRate()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }

        private void step() {
            Frame frame = getFrameSource().nextFrame();
            frame.setTime(System.nanoTime()-startTime);
            try {
                for (FrameProcessor processor : frameProcessors) {
                    processor.processFrame(frame);
                }
            } catch (Exception e) {
                // TODO: log
                e.printStackTrace();
            }
            if (beforeFrameRecordedCallback != null) {
                beforeFrameRecordedCallback.action(frame, XuggleVideoSource.this);
            }
            recordFrame(frame);
            if (afterFrameRecordedCallback != null) {
                afterFrameRecordedCallback.action(frame, XuggleVideoSource.this);
            }
        }

        private void recordFrame(Frame frame) {
            BufferedImage preparedImage = convertImageToType(frame.getImageData(), BufferedImage.TYPE_3BYTE_BGR);
            mWriter.encodeVideo(0, preparedImage, frame.getTime(), TimeUnit.NANOSECONDS);
            try {
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Convert a {@link BufferedImage} of any type, to {@link BufferedImage} of a
         * specified type. If the source image is the same type as the target type,
         * then original image is returned, otherwise new image of the correct type is
         * created and the content of the source image is copied into the new image.
         *
         * @param sourceImage
         *          the image to be converted
         * @param targetType
         *          the desired BufferedImage type
         *
         * @return a BufferedImage of the specifed target type.
         *
         * @see BufferedImage
         */
        private BufferedImage convertImageToType(BufferedImage sourceImage,
                                                  int targetType)
        {
            BufferedImage image;
            // if the source image is already the target type, return the source image
            if (sourceImage.getType() == targetType)
                image = sourceImage;
                // otherwise create a new image of the target type and draw the new
                // image
            else
            {
                image = new BufferedImage(getVideoSize().width,
                        getVideoSize().height, targetType);
                image.getGraphics().drawImage(sourceImage, 0, 0, getVideoSize().width, getVideoSize().height, null);
            }
            return image;
        }
    }

    private void init() {
        mWriter = ToolFactory.makeWriter(XugglerIO.map(outputStream));
        // manually set the container format (because it can't detect it by filename anymore)
        IContainerFormat containerFormat = IContainerFormat.make();
        containerFormat.setOutputFormat("flv", null, "application/flv");
        mWriter.getContainer().setFormat(containerFormat);
        // add the video stream
        mWriter.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, IRational.make(getFrameRate()), getVideoSize().width, getVideoSize().height);
    }

    private void dispose() {
        // done, so now let's wrap this up.
        mWriter.close();
    }

    /**
     * Returns the list of frame processors which should be applied to the frame.
     */
    @Override
    public List<FrameProcessor> getFrameProcessors() {
        return frameProcessors;
    }

    /**
     * Starts streaming process in separate thread and returns output stream.
     */
    @Override
    public synchronized OutputStream run() {
        if (!initialized) init();
        workerThread = new Thread(new VideoSourceWorker(), "WorkerThread");
        running.set(true);
        workerThread.start();
        return outputStream;
    }

    /**
     * Terminates currently running stream.
     */
    @Override
    public synchronized void stop() {
        running.set(false);
        try {
            if (workerThread != null) {
                Thread.sleep((long) (2*1000 / getFrameRate()));
                if (outputStream != null){
                    dispose();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns current output stream or NULL if it is unavailable.
     */
    @Override
    public OutputStream getStream() {
        return outputStream;
    }

    /**
     * Sets FrameSource.
     *
     * @param frameSource - FrameSource to be used.
     */
    @Override
    public void setFrameSource(FrameSource frameSource) {
        this.frameSource = frameSource;
    }

    public FrameSource getFrameSource() {
        return frameSource;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * Returns TRUE if streaming is in progress, FALSE otherwise.
     */
    @Override
    public boolean isRunning() {
        return running.get();
    }

    @Override
    public void setAfterFrameRecordedCallback(VideoSourceCallback callback) {
        afterFrameRecordedCallback = callback;
    }

    @Override
    public void setBeforeFrameRecordedCallback(VideoSourceCallback callback) {
        beforeFrameRecordedCallback = callback;
    }

    /**
     * Returns frame rate.
     *
     * @return
     */
    @Override
    public double getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(double frameRate) {
        this.frameRate = frameRate;
    }

    @Override
    public void setVideoSize(Dimension resolution) {
        this.outputResolution = resolution;
    }

    public Dimension getVideoSize() {
        return this.outputResolution;
    }
}
