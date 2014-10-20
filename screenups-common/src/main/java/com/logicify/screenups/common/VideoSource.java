package com.logicify.screenups.common;

import java.awt.*;
import java.io.OutputStream;
import java.util.List;

/**
 * This class represents generator of
 *
 * Created by LOGICIFY\corvis on 7/12/14.
 */
public interface VideoSource {

    /**
     * Returns the list of frame processors which should be applied to the frame.
     * @return
     */
    List<FrameProcessor> getFrameProcessors();

    /**
     * Starts streaming process in separate thread and returns output stream.
     * @return
     */
    OutputStream run();

    /**
     * Terminates currently running stream.
     */
    void stop();

    /**
     * Returns current output stream or NULL if it is unavailable.
     * @return
     */
    OutputStream getStream();

    /**
     * Sets FrameSource.
     * @param frameSource
     */
    void setFrameSource(FrameSource frameSource);

    /**
     * Returns frame rate.
     *
     * @return
     */
    public double getFrameRate();

    public void setVideoSize(Dimension resolution);

    /**
     * Returns TRUE if streaming is in progress, FALSE otherwise.
     * @return
     */
    boolean isRunning();

    public void setAfterFrameRecordedCallback(VideoSourceCallback callback);

    public void setBeforeFrameRecordedCallback(VideoSourceCallback callback);
}
