package com.daxin.idea.note.plugin;

import com.daxin.XMLUtils;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class MainWindow extends JFrame implements KeyListener {

    public void setIdeaDir(String ideaDir) {
        this.ideaDir = ideaDir;
    }

    private String ideaDir;

    private String key;
    private JTextArea txt;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow frame = new MainWindow(null);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void exit() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 这个是关键
    }

    /**
     * Create the frame.
     */
    public MainWindow(AnActionEvent e) {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                MainWindow.this.dispose();
            }
        });
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


        setBounds(100, 100, 657, 499);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        Font font = new Font("Monospaced", 0, 20);
        JTextArea textArea = new JTextArea();
        txt = textArea;
        txt.addKeyListener(this);
        textArea.setFont(font);
        textArea.setSize(480, 450);
        textArea.setBounds(14, 13, 611, 365);
        contentPane.add(textArea);
        if (e != null) {
            try {
                if (XMLUtils.readXmlToMap(CTRLM.getProjectIdeaPath(e)) != null) {
                    String keyConent = CTRLM.getJavaPackageNameAndClassNameAndMEthodName(e);
                    String val = XMLUtils.readXmlToMap(CTRLM.getProjectIdeaPath(e)).get(keyConent);
                    if (val != null) {
                        textArea.setText(new String(val.trim().getBytes("GB2312")));
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        JButton button = new JButton("确定");


        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String content = textArea.getText();

                try {
                    XMLUtils.WriteXml(ideaDir, key, content);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                MainWindow.this.dispose();


            }
        });
        button.setBounds(260, 414, 113, 27);
        contentPane.add(button);
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            this.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
