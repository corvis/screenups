package com.logicify.screenups.common.utils;

import com.logicify.screenups.common.ScreenInfo;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LOGICIFY\corvis on 10/20/14.
 */
public class ScreenUtils {

    /**
     * @return Returns information about each know screen.
     */
    public static List<ScreenInfo> getAllScreens() {
        GraphicsEnvironment localGE = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ArrayList<ScreenInfo> result = new ArrayList<ScreenInfo>();
        for (GraphicsDevice gd : localGE.getScreenDevices()) {
            ScreenInfo screenInfo = new ScreenInfo();
            screenInfo.setScreenName(gd.getIDstring());
            screenInfo.setDevice(gd);
            screenInfo.setBounds(gd.getDefaultConfiguration().getBounds());
            result.add(screenInfo);
        }
        return result;
    }

    /**
     * Returns bounding rect which includes all existing screens
     * @return
     */
    public static Rectangle getUnitedBoundingRect() {
        List<ScreenInfo> screens = getAllScreens();
        Rectangle2D result = new Rectangle2D.Double();
        for (ScreenInfo screenInfo : screens) {
            result = result.createUnion(screenInfo.getBounds());
        }
        return result.getBounds();
    }
}
