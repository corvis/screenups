package com.logicify.screenups.recorder;

import com.bulenkov.darcula.DarculaLaf;
import com.logicify.screenups.recorder.ui.MainForm;

import javax.swing.*;
import java.awt.*;

/**
 * Created by LOGICIFY\corvis on 10/20/14.
 */
public class EntryPoint {

    public static void main(String[] args) {
        // temporary workaround for problem with Nimbus classname
        UIManager.installLookAndFeel("Darcula", "com.bulenkov.darcula.DarculaLaf");
        try {
            UIManager.setLookAndFeel("com.bulenkov.darcula.DarculaLaf");
            UIManager.put("swing.boldMetal", Boolean.FALSE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainForm mainForm = new MainForm();
                mainForm.setVisible(true);
                mainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            }
        });
    }
}
