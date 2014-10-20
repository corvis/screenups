package com.logicify.screenups.common.ui;

import org.jdesktop.animation.transitions.EffectsManager;
import org.jdesktop.animation.transitions.ScreenTransition;
import org.jdesktop.animation.transitions.TransitionTarget;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LOGICIFY\corvis on 10/20/14.
 */
public class ActivityManager {

    private Map<Class, Activity> activityCache = new HashMap<Class, Activity>();
    private JComponent activityContainer;
    /*private static ActivityManager instance;

    public static synchronized ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }*/

    public ActivityManager(JComponent activityContainer) {
        this.activityContainer = activityContainer;
    }

    /**
     * Returns activity instance by given Activity class.
     * If activity is already in cache - chached instance will be returned.
     * Otherwise - a new one will be instantiated and put in cash.
     * @param activityClass
     * @return
     */
    public Activity getActivity(Class<? extends Activity> activityClass) {
        Activity activity = null;
        if (activityCache.containsKey(activityClass)) {
            activity = activityCache.get(activityClass);
        } else {
            activity = createActivity(activityClass);
        }
        return activity;
    }

    /**
     * Creates a new instance of activity.
     * @param activityClass
     * @return
     */
    public Activity createActivity(Class<? extends Activity> activityClass) {
        try {
            Activity activity = activityClass.getConstructor(ActivityManager.class).newInstance(this);
            return activity;
        } catch (Exception e) {
            // TODO: Log
            return null;
        }
    }

    public void openActivity(final Activity activity, boolean animate) {
        EffectsManager.setEffect(activity, new Effect.MoveIn(activityContainer.getWidth(), 0), EffectsManager.TransitionType.APPEARING);
        EffectsManager.setEffect(activity, new Effect.MoveOut(-1*activityContainer.getWidth(), 0), EffectsManager.TransitionType.DISAPPEARING);
        if (animate) {
            ScreenTransition screenTransition = new ScreenTransition(activityContainer, new TransitionTarget() {
                @Override
                public void setupNextScreen() {
                    activityContainer.removeAll();
                    activity.setBounds(0, 0, activityContainer.getWidth(), activityContainer.getHeight());
                    activityContainer.add(activity);
                    activity.setVisible(true);
                    activityContainer.validate();
                }
            }, 300);
            screenTransition.start();
        } else {
            activityContainer.removeAll();
            activityContainer.add(activity);
            activity.setBounds(0,0, activityContainer.getWidth(), activityContainer.getHeight());
            activityContainer.validate();
        }
    }

    public void openActivity(final Activity activity) {
        openActivity(activity, true);
    }
}
