package com.logicify.screenups.recorder.ui.activity;

import com.logicify.screenups.common.ui.Activity;
import com.logicify.screenups.common.ui.ActivityManager;

import javax.swing.*;

/**
 * Created by LOGICIFY\corvis on 10/20/14.
 */
public class HomeActivity extends Activity {
    private JLabel menuStartNewVDR;
    private JPanel rootPane;

    public HomeActivity(ActivityManager activityManager) {
        super(activityManager);
        add(rootPane);
    }
}
