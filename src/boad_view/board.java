package boad_view;

import ArrWork.Figure;
import ArrWork.Node;
import ArrWork.Tree;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class board {

    public void setup_board() throws IOException {


        int rabbit_start_x = 6;
        int rabbit_start_y =7;


        ArrayList<Figure> rabbits_options = new ArrayList<>();
        rabbits_options.add(new Figure(rabbit_start_x,rabbit_start_y,true));

        Tree tree = new Tree(new Node(rabbits_options,0));

        ArrayList<ArrayList<Figure>> prev_wolves = new ArrayList<>();
        ArrayList<Figure> start_wolves = new ArrayList<>();
        for(int l =0; l<4;l++)
        {
            start_wolves.add(new Figure(l*2+1,0,false));
        }
        prev_wolves.add(start_wolves);
        tree.getRoot().addChild(new Node(start_wolves,1));
        tree.make_tree(tree.getRoot().getChildren().get(0),prev_wolves,rabbits_options,1);




        JFrame f=new JFrame("Chess Game");
        JPanel pane = new JPanel(new GridBagLayout()); //Create a pane to house all content, and give it a GridBagLayout
        f.setContentPane(pane);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.insets.right = 0;
        c.insets.left = 0;
        c.weightx = 1;
        c.weighty = 1;

        JButton rabbit_button = rabbit_but();
        c.gridx = rabbit_start_x;
        c.gridwidth = 1;
        c.gridy = rabbit_start_y;
        pane.add(rabbit_button, c);


        ArrayList<ArrayList<Integer>> rabbit_pos = new ArrayList<>();
        ArrayList<Integer> cur_rabbit_pos = new ArrayList<>();
        cur_rabbit_pos.add(rabbit_start_x);
        cur_rabbit_pos.add(rabbit_start_y);
        rabbit_pos.add(cur_rabbit_pos);
        ArrayList<Figure> rabbit_movements = new ArrayList<>();
        rabbit_movements.add(new Figure(rabbit_start_x,rabbit_start_y,true));








        ArrayList<JButton> wolves = add_wolves();
        int iter =0;
        for(JButton w: wolves) {
            c.gridx = iter*2+1;
            c.gridwidth = 1;
            c.gridy = 0;
            pane.add(w,c);
            iter++;
        }

        ArrayList<ArrayList<JButton>> board_buttons = make_board(rabbit_button,pane, c,rabbit_pos,rabbit_movements,tree,wolves);




        int i=0;
        int j =0;
        for(ArrayList<JButton> b:board_buttons) {
            j=0;
            for (JButton but : b ){
                c.gridx=i;
                c.gridwidth = 1;
                c.gridy=j;
                pane.add(but,c);
                j++;
            }

            i++;
        }












        f.setDefaultCloseOperation(EXIT_ON_CLOSE );
        f.pack();
        f.setLocationRelativeTo( null );
        f.setVisible(true);
        f.setSize(700,700);

        f.setResizable(false);

    }


    ArrayList<JButton> add_wolves()
    {
        ArrayList<JButton> wolves = new ArrayList<>();
        for(int i =0; i<4;i++) {
            Icon icon = new ImageIcon("C:\\Users\\leraz\\IdeaProjects\\algorithms_lab6\\src\\wolf.png");
            Image img = ((ImageIcon) icon).getImage();
            Image newimg = img.getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH ) ;
            icon = new ImageIcon( newimg );
            JButton button7 = new JButton(icon);
            button7.setBackground(Color.red);
            button7.setPreferredSize(new Dimension(40, 40));
            wolves.add(button7);
        }
        return wolves;
    }

    JButton rabbit_but()
    {
        Icon icon = new ImageIcon("C:\\Users\\leraz\\IdeaProjects\\algorithms_lab6\\src\\rabbit.png");
        Image img = ((ImageIcon) icon).getImage() ;
        Image newimg = img.getScaledInstance( 40, 40,  java.awt.Image.SCALE_SMOOTH ) ;
        icon = new ImageIcon( newimg );
        JButton button7 = new JButton(icon);
        button7.setBackground(Color.green);
        button7.setPreferredSize(new Dimension(40, 40));

        return button7;
    }

    ArrayList<ArrayList<JButton>> make_board(JButton rabbit, JPanel panel, GridBagConstraints c, ArrayList<ArrayList<Integer>>rabbit_pos, ArrayList<Figure>rabbit_movement
    ,Tree tree,ArrayList<JButton>wolves_buttons)
    {
        ArrayList<ArrayList<JButton>>board_buttons = new ArrayList<>();
        for(int i =0; i<8;i++) {
            ArrayList<JButton> butts = new ArrayList<>();
            for(int j =0;j<8;j++) {

                JButton b = new JButton();
                b.setPreferredSize(new Dimension(40, 40));
                int finalJ = j;
                int finalI = i;
                if((finalJ+finalI) % 2 ==0)
                    b.setBackground(Color.white);
                else
                    b.setBackground(Color.black);
                b.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Button clicked " + " X: " + finalI + " Y:" + finalJ );
                        move_rabbit(rabbit, finalI, finalJ,panel, c, board_buttons,rabbit_pos,rabbit_movement);
                        move_wolves(tree,rabbit_movement,wolves_buttons, panel,c,board_buttons);
//                        paint_board(board_buttons);

                    }
                });
                butts.add(b);
            }
            board_buttons.add(butts);
        }
        return board_buttons;
    }

    void paint_board(ArrayList<ArrayList<JButton>> board)
    {
        for(int i =0; i<8;i++)
        {
            for(int j =0;j <8;j++)
            {
                JButton b = board.get(i).get(j);
                if((j+i) % 2 ==0)
                    b.setBackground(Color.white);
                else
                    b.setBackground(Color.black);
            }
        }
    }

    void move_rabbit(JButton rabbit, int move_x, int move_y, JPanel panel, GridBagConstraints c, ArrayList<ArrayList<JButton>> board_buttons, ArrayList<ArrayList<Integer>>rabbit_pos, ArrayList<Figure>rabbit_movements)
    {

        int rabbit_x = rabbit_pos.get(rabbit_pos.size()-1).get(0);
        int rabbit_y = rabbit_pos.get(rabbit_pos.size()-1).get(1);
        System.out.println("Rabbit: "+rabbit_x+ " "+rabbit_y);
        System.out.println("Where: "+move_x+ " "+move_y);

        if((move_x == rabbit_x-1 && move_y == rabbit_y-1) ||(move_x == rabbit_x+1 && move_y == rabbit_y+1)
        || (move_x == rabbit_x-1 && move_y == rabbit_y+1)||(move_x == rabbit_x+1 && move_y == rabbit_y-1))
        {
                System.out.println("Moving");

                panel.remove(rabbit);
                panel.remove(board_buttons.get(move_x).get(move_y));

                c.gridx = move_x;
                c.gridwidth = 1;
                c.gridy = move_y;
                panel.repaint();
                panel.revalidate();
                panel.add(rabbit,c);
                rabbit_movements.add(new Figure(move_x,move_y,true));

                if(rabbit_pos.size()>1) {
                    c.gridx = rabbit_x;
                    c.gridwidth = 1;
                    c.gridy = rabbit_y;
                    JButton old_place = board_buttons.get(rabbit_x).get(rabbit_y);
                    if(rabbit_x+rabbit_y%2==0)
                        old_place.setBackground(Color.white);
                    else
                        old_place.setBackground(Color.black);
                    panel.add(old_place, c);
                    System.out.println("Added board button to "+ rabbit_x+" "+rabbit_y);
                }


            ArrayList<Integer> new_position = new ArrayList<>();
            new_position.add(move_x);
            new_position.add(move_y);
            rabbit_pos.add(new_position);
            System.out.println("Rabbit moved");

        }

    }

    void move_wolves(Tree tree, ArrayList<Figure>rabbit_movements, ArrayList<JButton> wolf_buttons, JPanel panel, GridBagConstraints c, ArrayList<ArrayList<JButton>> board_button)
    {
        ArrayList<Figure> move_w = new ArrayList<>();
        move_w = tree.possible_options_for_wolves(rabbit_movements.size()*2-1,rabbit_movements.get(rabbit_movements.size()-1),tree.getRoot());
        System.out.println(move_w.size());
        for(Figure f: move_w)
            System.out.println("W: " +f.getX()+" "+ f.getY());
        for(JButton w: wolf_buttons)
        {
            panel.remove(w);
        }
        System.out.println("Wolves deleted");
        panel.repaint();
        panel.revalidate();
        for(int i =0; i<move_w.size();i++)
        {
            c.gridx = move_w.get(i).getX();
            c.gridy = move_w.get(i).getY();

            panel.remove(board_button.get(move_w.get(i).getX()).get(move_w.get(i).getY()));
            panel.repaint();
            panel.revalidate();
            panel.add(wolf_buttons.get(i), c);

        }

    }
}
