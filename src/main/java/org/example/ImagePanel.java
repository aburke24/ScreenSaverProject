package org.example;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImagePanel extends JPanel implements ActionListener {
    private List<JLabel> imageLabels;
    private List<JLabel> originalLabels;
    private Timer timer;
    private int colorCount = 0;
    private int frameHeight;

    public ImagePanel(int frameHeight) {
        // could make variables to take in for thw rows and columns - 3X3 for use case
        setLayout(new GridLayout(3, 3));
        setPreferredSize(new Dimension(frameHeight, frameHeight));
        setBackground(Color.DARK_GRAY);
        this.frameHeight = frameHeight;
        loadImages();
        startTimer();
    }

    private void loadImages() {

        // imageLabels holds the changes
        imageLabels = new ArrayList<>();
        // originalLables holds the originals
        originalLabels = new ArrayList<>();

        String imagePath = "src/album images";

        File imageFolder = new File(imagePath);

        if (imageFolder.isDirectory()) {
            File[] imageFiles = imageFolder.listFiles();
            List<String> imagePaths = new ArrayList<>();
            // loop through all the files in the folder
            for (File file : imageFiles) {
                if (file.isFile()) {
                    // add the file to the List of image paths
                    imagePaths.add(file.getAbsolutePath());
                }
            }
            // make sure there are 9 images - aka row x col
            // might add more than 9 photos if a larger grid is wanted
            if (imagePaths.size() >= 9) {
                // randomizes the images -- not NEEDED
                Collections.shuffle(imagePaths);

                // the image formatting and saving into lists
                // add each image to the imagePanel
                for (int i = 0; i < 9; i++) {
                    try {
                        ImageIcon icon = new ImageIcon(ImageIO.read(new File(imagePaths.get(i))));
                        JLabel imageLabel = new JLabel(icon);
                        imageLabel.setBorder(BorderFactory.createEmptyBorder());
                        // make images square
                        imageLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(frameHeight/3, frameHeight/3, java.awt.Image.SCALE_SMOOTH)));
                        add(imageLabel);

                        imageLabels.add(imageLabel);

                        JLabel copy = new JLabel(icon);
                        // make images square for copy
                        copy.setIcon(new ImageIcon(icon.getImage().getScaledInstance(frameHeight/3, frameHeight/3, java.awt.Image.SCALE_SMOOTH)));

                        originalLabels.add(copy);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.err.println("Error: Not enough images in the folder. Found " + imageFiles.length);
            }
        } else {
            System.err.println("Error: No directory found");
        }
    }

    private void startTimer() {
        timer = new Timer(3000, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            changeGrayScaleImage(colorCount % imageLabels.size());
            colorCount++;
        }
    }

    private void changeGrayScaleImage(int index) {

        // need to reset the previous one that was changed
        if (index > 0) {
            ImageIcon prevIcon = (ImageIcon) originalLabels.get(index - 1).getIcon();
            imageLabels.get(index - 1).setIcon(prevIcon);
            // System.out.println("Restoring "+ (index-1));
        }

        // if the first one is changing make sure the last image is original
        if (index == 0) {
            ImageIcon prevIcon = (ImageIcon) originalLabels.get(originalLabels.size() - 1).getIcon();
            imageLabels.get(originalLabels.size() - 1).setIcon(prevIcon);
            // System.out.println("Restoring "+ (originalLabels.size() - 1));
        }

        // change the image to Gray
        ImageIcon originalIcon = (ImageIcon) imageLabels.get(index).getIcon();
        Image originalImage = originalIcon.getImage();

        // where the change is happening
        BufferedImage bufferedImage = new BufferedImage(originalImage.getWidth(null), originalImage.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);

        bufferedImage.getGraphics().drawImage(originalImage, 0, 0, null);

        // update the imageLabels with the change
        ImageIcon grayIcon = new ImageIcon(bufferedImage);
        imageLabels.get(index).setIcon(grayIcon);
    }
}
