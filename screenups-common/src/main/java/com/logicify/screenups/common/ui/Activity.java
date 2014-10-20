package com.logicify.screenups.common.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by LOGICIFY\corvis on 10/20/14.
 */
public abstract class Activity extends JComponent {
    private ActivityManager activityManager;

    protected Activity(ActivityManager activityManager) {
        super();
        setLayout(new BorderLayout());
        this.activityManager = activityManager;
        this.setVisible(false);
    }

    public ActivityManager getActivityManager() {
        return activityManager;
    }
}
