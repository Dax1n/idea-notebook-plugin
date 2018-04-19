package com.daxin.idea.note.plugin;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.components.ApplicationComponent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Created by liuguangxin on 2018/4/17.
 */
public class NoteComponent implements ApplicationComponent {

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }

    @NotNull
    @Override
    public String getComponentName() {
        return "CodeNoteBook";
    }

    public void codeNoteBook(String key, String ideaDir,AnActionEvent e) {


        MainWindow mainWindow = new MainWindow(e);
        mainWindow.setKey(key);
        mainWindow.setIdeaDir(ideaDir);
        mainWindow.setSize(660, 500);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int) (toolkit.getScreenSize().getWidth() - mainWindow.getWidth()) / 2;
        int y = (int) (toolkit.getScreenSize().getHeight() - mainWindow.getHeight() - 50) / 2;
        mainWindow.setLocation(x, y);
        mainWindow.setVisible(true);


    }
}
