package snaketest;

import java.awt.GridLayout;
import javax.swing.JFrame;
import snaketest.graphics.Screen;

public class Frame extends JFrame {
    
    private String _windowTitle;
    
    public Frame()
    {
        _windowTitle = "The Snake";
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(_windowTitle);
        setResizable(false);
        init();
    }
    
    private void init() {
        setLayout(new GridLayout(1,1,0,0));
        Screen s = new Screen();
        add(s);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public static void main(String[] args)
    {
        new Frame();
    }


}
