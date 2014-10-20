package com.logicify.screenups.recorder.ui.activity;

import com.logicify.screenups.common.ui.Activity;
import com.logicify.screenups.common.ui.ActivityManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by LOGICIFY\corvis on 10/20/14.
 */
public class VdrActivity extends Activity {
    private JPanel rootPanel;
    private JButton button1;

    public VdrActivity(ActivityManager activityManager) {
        super(activityManager);
        add(rootPanel);
        button1.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                getActivityManager().openActivity(getActivityManager().getActivity(HomeActivity.class));
            }
        });
    }
}
