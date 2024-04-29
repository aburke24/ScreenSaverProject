package org.example;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Screen Saver");

        // get the size of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Insets insets = frame.getInsets();
        int frameWidth = screenSize.width ;
        int frameHeight = screenSize.height;

        frame.setPreferredSize(screenSize);
        // ----------------frame width------------------
        // -----left----------images----------right-----

        int sideWidth = (frameWidth - frameHeight)/2;

        // sides should fill empty space
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(Color.DARK_GRAY);
        rightPanel.setPreferredSize(new Dimension(sideWidth,frameHeight));
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.DARK_GRAY);
        leftPanel.setPreferredSize(new Dimension(sideWidth,frameHeight));


        ImagePanel imagePanel = new ImagePanel(frameHeight);

        // left - images - right
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(rightPanel,BorderLayout.EAST);
        frame.add(imagePanel,BorderLayout.CENTER);



        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
