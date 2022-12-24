package boad_view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class board {

    public static void setup_board() throws IOException {
        GridBagConstraints c = new GridBagConstraints();
        JFrame f=new JFrame("Chess Game");

        ArrayList<JButton> buttons = new ArrayList<>();
        for(int i =0; i<8;i++) {
            for(int j =0;j<8;j++) {
                JButton b = new JButton();
                b.setSize(50,50);
                b.setLocation(i*50,j*50);
                int finalJ = j;
                int finalI = i;
                if((finalJ+finalI) % 2 ==0)
                    b.setBackground(Color.white);
                else
                    b.setBackground(Color.black);
                b.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Button clicked " + " X: " + finalI + " Y:" + finalJ );
                    }
                });
                buttons.add(b);
            }
        }
        for(JButton b:buttons)
            f.add(b);
        JPanel jp = new JPanel();
        JLabel displayField = new JLabel();
        ImageIcon icon = new ImageIcon();
        try{
            icon = new ImageIcon("C:\\Users\\leraz\\IdeaProjects\\algorithms_lab6\\rabbit.png");
            displayField.setIcon(icon);
            jp.add(displayField);
            f.add(jp);

        } catch (Exception e)
        {
            System.out.println("Image cannot be found");
        }
















        f.setSize(600,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setVisible(true);
//        f.pack();
    }
}
