package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Frame extends JFrame{
    private BufferedImage bufferedImage;
    public Frame(BufferedImage bufferedImage){
        super("2048_test");
        this.bufferedImage=bufferedImage;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                setLocation(500,200);
                setSize(500,500);
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                setVisible(true);
            }
        });
    }
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D graphics2D=(Graphics2D) g;
        graphics2D.drawImage(bufferedImage,null,0,0);
    }
    public void repaint(BufferedImage bufferedImage){
        this.bufferedImage=bufferedImage;
        repaint();
    }
}
