package com.logicify.screenups.recorder.ui;

import com.logicify.screenups.common.ui.Activity;
import com.logicify.screenups.common.ui.ActivityManager;
import com.logicify.screenups.recorder.ui.activity.HomeActivity;
import com.logicify.screenups.recorder.ui.activity.VdrActivity;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;
import org.jdesktop.animation.transitions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by LOGICIFY\corvis on 10/20/14.
 */
public class MainForm extends JFrame {
    private JPanel rootPanel;
    private JPanel activityContainer;
    private JLabel logo;

    private ActivityManager activityManager;
    /**
     * Constructs a new frame that is initially invisible.
     * <p/>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     *
     * @throws java.awt.HeadlessException if GraphicsEnvironment.isHeadless()
     *                                    returns true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see java.awt.Component#setSize
     * @see java.awt.Component#setVisible
     * @see javax.swing.JComponent#getDefaultLocale
     */
    public MainForm() {
        super();
        setTitle("Screenups Recorder");
        add(rootPanel);
        activityContainer.setLayout(null);
        activityManager = new ActivityManager(activityContainer);
        logo.addMouseListener(new MouseAdapter(){
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                activityManager.openActivity(activityManager.getActivity(VdrActivity.class));
            }
        });
        pack();
        activityManager.openActivity(activityManager.getActivity(HomeActivity.class));
    }


}
