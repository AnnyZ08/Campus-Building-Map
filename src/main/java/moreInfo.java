import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;


public class moreInfo implements ActionListener {
    private static JFrame frame;
    private static JPanel panel;
    private static JLabel name0;
    private static JLabel name1;
    private static JLabel name2;
    private static JLabel name3;
    private static JLabel name4;
    private static JLabel name5;
    private static JLabel name6;
    private static JLabel name7;
    private static JButton okbutton;


    public moreInfo() {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        // frame initialize
        frame = new JFrame();
        panel = new JPanel();
        panel.setBackground(Color.white);
        frame.setSize(480, 320);// setup frame size
        frame.setTitle("About us");
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.add(panel);
        panel.setLayout(null);

        name0 = new JLabel("GIS Map System (Version 1.0)");
        name0.setBounds(50, 20, 300, 25);
        name0.setFont(new Font("Verdana", Font.PLAIN, 20));

        name1 = new JLabel("Team member:");
        name1.setBounds(50, 50, 300, 25);
        name2 = new JLabel("Anny Zheng, Wenqian Li, Yi Xiao, Yuting Pan, Zhihao Yang");
        name2.setBounds(50, 70, 500, 25);
        name2.setFont(new Font("Verdana", Font.ITALIC, 12));

        name6 = new JLabel("Contact Info:");
        name6.setBounds(50, 100, 300, 25);
        name7 = new JLabel("ypan377@uwo.ca, yxiao442@uwo.ca");
        name7.setBounds(50, 120, 500, 25);
        name7.setFont(new Font("Verdana", Font.ITALIC, 12));

        name5 = new JLabel("Release Date: 2023.4 ");
        name5.setBounds(50, 150, 300, 25);
        name5.setFont(new Font("Verdana", Font.PLAIN, 10));

        name3 = new JLabel("Copyright © 2023 ");
        name3.setBounds(50, 170, 300, 25);
        name3.setFont(new Font("Verdana", Font.PLAIN, 10));

        name4 = new JLabel("CS2212 Winter Team 6, All right reserved");
        name4.setBounds(50, 190, 300, 25);
        name4.setFont(new Font("Verdana", Font.PLAIN, 10));


        okbutton = new JButton("OK");


        okbutton.setContentAreaFilled(false);
        okbutton.setForeground(Color.WHITE);
        okbutton.setFont(new Font("Verdana", Font.PLAIN, 12));
        okbutton.setBounds(200, 230, 80, 40);
        okbutton.setBackground(new Color(79, 38, 131));
        okbutton.addActionListener(this);
        okbutton.setVisible(true);
        okbutton.setOpaque(true);
        okbutton.setBorderPainted(false);
        okbutton.setFocusPainted(false);


        panel.add(name0);
        panel.add(name1);
        panel.add(name2);
        panel.add(name3);
        panel.add(name4);
        panel.add(name5);
        panel.add(name6);
        panel.add(name7);
        panel.add(okbutton);


        // read the logo file from the pictures folder
        try {
            BufferedImage pic0 = ImageIO.read(new File("pictures/duck_img.png"));
            int originalWidth = pic0.getWidth();
            int originalHeight = pic0.getHeight();
            int desiredWidth = 50;
            int desiredHeight = 50;
            double scaleX = (double) desiredWidth / originalWidth;
            double scaleY = (double) desiredHeight / originalHeight;
            double scale = Math.min(scaleX, scaleY);
            Image scaledImage = pic0.getScaledInstance((int) (originalWidth * scale), (int) (originalHeight * scale), Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);
            JLabel picture = new JLabel(icon);
            picture.setBounds(0, 0, 50, 50);
            panel.add(picture);
            final int[] dx = {3}; // movement in x direction
            final int[] dy = {7}; // movement in y direction
            final int[] x = {0};
            final int[] y = {0};
            x[0]=200;
            y[0]=100;
            Timer timer = new Timer(13, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    picture.setLocation(x[0], y[0]);
                    int maxX = panel.getWidth() - picture.getWidth();
                    int maxY = panel.getHeight() - picture.getHeight();
                    x[0] += dx[0];
                    y[0] += dy[0];
                    if (x[0] < 0 || x[0] > maxX) {
                        dx[0] *= -1;
                        x[0] += dx[0] * 2;
                    }
                    if (y[0] < 0 || y[0] > maxY) {
                        dy[0] *= -1;
                        y[0] += dy[0] * 2;
                    }

                }
            });
            timer.start();


            BufferedImage pic1 = ImageIO.read(new File("pictures/duck_img.png"));
            int originalWidth1 = pic1.getWidth();
            int originalHeight1 = pic1.getHeight();
            int desiredWidth1 = 80;
            int desiredHeight1 = 80;
            double scaleX1 = (double) desiredWidth1 / originalWidth1;
            double scaleY1 = (double) desiredHeight1 / originalHeight1;
            double scale1 = Math.min(scaleX1, scaleY1);
            Image scaledImage1 = pic1.getScaledInstance((int) (originalWidth1 * scale1), (int) (originalHeight1 * scale1), Image.SCALE_SMOOTH);
            ImageIcon icon1 = new ImageIcon(scaledImage1);
            JLabel picture1 = new JLabel(icon1);
            picture1.setBounds(0, 0, 80, 80);
            panel.add(picture1);
            final int[] dx1 = {13}; // movement in x direction
            final int[] dy1 = {11}; // movement in y direction
            final int[] x1 = {0};
            final int[] y1 = {0};
            x1[0]=7;
            y1[0]=1;

            Timer timer1 = new Timer(17, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    picture1.setLocation(x1[0], y1[0]);
                    int maxX = panel.getWidth() - picture1.getWidth();
                    int maxY = panel.getHeight() - picture1.getHeight();
                    x1[0] += dx1[0];
                    y1[0] += dy1[0];
                    if (x1[0] < 0 || x1[0] > maxX) {
                        dx1[0] *= -1;
                        x1[0] += dx1[0] * 2;
                    }
                    if (y1[0] < 0 || y1[0] > maxY) {
                        dy1[0] *= -1;
                        y1[0] += dy1[0] * 2;
                    }


                }
            });
            timer1.start();


        } catch (Exception e) {
        }
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == okbutton) {
            frame.dispose();
        }
    }
}

