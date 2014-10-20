package com.logicify.screenups.common.ui;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

import java.awt.*;

/**
 * Created by LOGICIFY\corvis on 10/21/14.
 */
public class Effect {
    public static class MoveIn extends org.jdesktop.animation.transitions.Effect {
        private final Point startLocation = new Point();
        private PropertySetter ps;

        public MoveIn(int x, int y) {
            startLocation.x = x;
            startLocation.y = y;
        }

        /**
         * Initializes animation to vary the location during the transition.
         */
        @Override
        public void init(Animator animator, org.jdesktop.animation.transitions.Effect parentEffect) {
            org.jdesktop.animation.transitions.Effect targetEffect = (parentEffect == null) ? this : parentEffect;
            ps = new PropertySetter(targetEffect, "location",
                    startLocation, new Point(getEnd().getX(), getEnd().getY()));
            animator.addTarget(ps);
            super.init(animator, parentEffect);
        }

        @Override
        public void cleanup(Animator animator) {
            animator.removeTarget(ps);
        }
    }

    /**
     * A custom Effect to move a component from its start location out to a
     * specified end point.
     */
    public static class MoveOut extends org.jdesktop.animation.transitions.Effect {
        private final Point endLocation = new Point();
        private PropertySetter ps;

        public MoveOut(int x, int y) {
            endLocation.x = x;
            endLocation.y = y;
        }

        /**
         * Initializes animation to vary the location during the transition.
         */
        @Override
        public void init(Animator animator, org.jdesktop.animation.transitions.Effect parentEffect) {
            org.jdesktop.animation.transitions.Effect targetEffect = (parentEffect == null) ? this : parentEffect;
            ps = new PropertySetter(targetEffect, "location",
                    new Point(getStart().getX(), getStart().getY()), endLocation);
            animator.addTarget(ps);
            super.init(animator, parentEffect);
        }

        @Override
        public void cleanup(Animator animator) {
            animator.removeTarget(ps);
        }
    }
}
