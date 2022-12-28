package boad_view;

import ArrWork.Figure;
import ArrWork.Node;
import ArrWork.Tree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class board {

    Tree tree;
    JFrame game_frame;

    int computerWon= 0; // 0 - game in process 1 - comp won 2 - user won

    public void setTree(Tree tree) {
        this.tree = tree;
    }

    public void setGame_frame(JFrame game_frame) {
        this.game_frame = game_frame;
    }

    public void setup_board(){


        int rabbit_start_x = 6;
        int rabbit_start_y =7;


        ArrayList<Figure> rabbits_options = new ArrayList<>();
        rabbits_options.add(new Figure(rabbit_start_x,rabbit_start_y,true));

        Tree tree_m = new Tree(new Node(rabbits_options,0));

        ArrayList<ArrayList<Figure>> prev_wolves = new ArrayList<>();
        ArrayList<Figure> start_wolves = new ArrayList<>();
        for(int l =0; l<4;l++)
        {
            start_wolves.add(new Figure(l*2+1,0,false));
        }
        prev_wolves.add(start_wolves);
        tree_m.getRoot().addChild(new Node(start_wolves,1));
        tree_m.make_tree(tree_m.getRoot().getChildren().get(0),prev_wolves,rabbits_options,1);
        setTree(tree_m);




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
            w.setBorderPainted( false );
            w.setFocusPainted( false );
            c.gridx = iter*2+1;
            c.gridwidth = 1;
            c.gridy = 0;
            pane.add(w,c);
            iter++;
        }

        ArrayList<ArrayList<JButton>> board_buttons = make_board(rabbit_button,pane, c,rabbit_pos,rabbit_movements,wolves);




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
        setGame_frame(f);

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
    ,ArrayList<JButton>wolves_buttons)
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
                        int checker =  move_rabbit(rabbit, finalI, finalJ,panel, c, board_buttons,rabbit_pos,rabbit_movement);
                        ArrayList<Figure> last_wolves  = new ArrayList<>();
                        if(checker ==1) {
//                            move_wolves_easy(rabbit_movement, wolves_buttons, panel, c, board_buttons);
                            move_wolves_medium(rabbit_movement, wolves_buttons, panel, c, board_buttons);

                        }


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

    int move_rabbit(JButton rabbit, int move_x, int move_y, JPanel panel, GridBagConstraints c, ArrayList<ArrayList<JButton>> board_buttons, ArrayList<ArrayList<Integer>>rabbit_pos,
                    ArrayList<Figure>rabbit_movements)
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
            if(move_y == 0) {
                JOptionPane.showMessageDialog(game_frame, "You won!");
                computerWon = 2;
                game_frame.dispose();
            }
            return 1;

        }
        return 0;

    }

    void setupBoardButtons(ArrayList<ArrayList<JButton>> board_buttons, JPanel panel, GridBagConstraints c)
    {
        for(int i =0; i<8;i++)
        {
            for(int j =0; j<8;j++)
            {
                c.gridx=i;
                c.gridy=j;
                panel.add(board_buttons.get(i).get(j),c);
            }
        }
    }

    void move_wolves_easy( ArrayList<Figure>rabbit_movements, ArrayList<JButton> wolf_buttons, JPanel panel, GridBagConstraints c, ArrayList<ArrayList<JButton>> board_button)
    {
        ArrayList<Figure> move_w  = tree.possible_options_for_wolves(rabbit_movements.size()*2-1,rabbit_movements.get(rabbit_movements.size()-1),tree.getRoot(),rabbit_movements,1);

        System.out.println(move_w.size());
        for(Figure f: move_w)
            System.out.println("W: " +f.getX()+" "+ f.getY());
        for(JButton w: wolf_buttons)
        {
            panel.remove(w);
        }
        for(ArrayList<JButton> board: board_button)
        {
            for(JButton b: board)
                panel.remove(b);
        }
        setupBoardButtons(board_button, panel,c);
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
        System.out.println("Wolves moved");
        if(rabbit_movements.size()==3)
        {
            rabbit_movements.remove(0);
            rabbit_movements.remove(0);
            Figure new_rabbit = new Figure(rabbit_movements.get(0).getX(),rabbit_movements.get(0).getY(),true);
            ArrayList<Figure> rab = new ArrayList<>();
            rab.add(new_rabbit);

            Tree tree_m = new Tree(new Node(rab,0));
            tree_m.getRoot().addChild(new Node(move_w,1));
            ArrayList<ArrayList<Figure>> previous_wolf = new ArrayList<>();
            previous_wolf.add(move_w);
            tree_m.make_tree(tree_m.getRoot().getChildren().get(0),previous_wolf,rab,1);
            setTree(tree_m);
        }


    }

    void move_wolves_medium( ArrayList<Figure>rabbit_movements, ArrayList<JButton> wolf_buttons, JPanel panel, GridBagConstraints c, ArrayList<ArrayList<JButton>> board_button)
    {
        ArrayList<Figure> move_w  = tree.alpha_beta_pruning(rabbit_movements.size()*2-1,rabbit_movements.get(rabbit_movements.size()-1),tree.getRoot(),rabbit_movements,1);
        ArrayList<Figure> move_w_plus_rab = new ArrayList<>(move_w);
        move_w_plus_rab.add(rabbit_movements.get(rabbit_movements.size()-1));
        while(!Figure.check_for_duplicates(move_w_plus_rab,null))
        {
            move_w  = tree.alpha_beta_pruning(rabbit_movements.size()*2-1,rabbit_movements.get(rabbit_movements.size()-1),tree.getRoot(),rabbit_movements,1);
            move_w_plus_rab = new ArrayList<>(move_w);
            move_w_plus_rab.add(rabbit_movements.get(rabbit_movements.size()-1));
        }

        System.out.println(move_w.size());
        for(Figure f: move_w)
            System.out.println("W: " +f.getX()+" "+ f.getY());
        for(JButton w: wolf_buttons)
        {
            panel.remove(w);
        }
        for(ArrayList<JButton> board: board_button)
        {
            for(JButton b: board)
                panel.remove(b);
        }
        setupBoardButtons(board_button, panel,c);
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
        System.out.println("Wolves moved");
        if(rabbit_movements.size()==3)
        {
            rabbit_movements.remove(0);
            rabbit_movements.remove(0);
            Figure new_rabbit = new Figure(rabbit_movements.get(0).getX(),rabbit_movements.get(0).getY(),true);
            ArrayList<Figure> rab = new ArrayList<>();
            rab.add(new_rabbit);

            Tree tree_m = new Tree(new Node(rab,0));
            tree_m.getRoot().addChild(new Node(move_w,1));
            ArrayList<ArrayList<Figure>> previous_wolf = new ArrayList<>();
            previous_wolf.add(move_w);
            tree_m.make_tree(tree_m.getRoot().getChildren().get(0),previous_wolf,rab,1);
            setTree(tree_m);
        }


    }

    void check_if_comp_won(int rabbit_x, int rabbit_y, ArrayList<Figure> wolves)
    {
        // check if all are surrounding
        int checker_1 = 0;
        for(Figure f:wolves)
        {
            if((f.getX()==rabbit_x+1 &&f.getY()==rabbit_y+1) ||(f.getX()==rabbit_x-1 &&f.getY()==rabbit_y-1)||(f.getX()==rabbit_x+1 &&f.getY()==rabbit_y-1)||(f.getX()==rabbit_x-1 &&f.getY()==rabbit_y+1))
                checker_1++;
        }
        if(checker_1==4)
        {
            computerWon=1;
            JOptionPane.showMessageDialog(game_frame, "Computer won");
            game_frame.dispose();
        }
        else{
            // check if rabbit has no moves and not in 0




        }

    }

    void move_wolves_hard( ArrayList<Figure>rabbit_movements, ArrayList<JButton> wolf_buttons, JPanel panel, GridBagConstraints c, ArrayList<ArrayList<JButton>> board_button)
    {
        ArrayList<Figure> move_w  = tree.alpha_beta_pruning(rabbit_movements.size()*2-1,rabbit_movements.get(rabbit_movements.size()-1),tree.getRoot(),rabbit_movements,1);

        System.out.println(move_w.size());
        for(Figure f: move_w)
            System.out.println("W: " +f.getX()+" "+ f.getY());
        for(JButton w: wolf_buttons)
        {
            panel.remove(w);
        }
        for(ArrayList<JButton> board: board_button)
        {
            for(JButton b: board)
                panel.remove(b);
        }
        setupBoardButtons(board_button, panel,c);
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
        System.out.println("Wolves moved");
        if(rabbit_movements.size()==3)
        {
            rabbit_movements.remove(0);
            rabbit_movements.remove(0);
            Figure new_rabbit = new Figure(rabbit_movements.get(0).getX(),rabbit_movements.get(0).getY(),true);
            ArrayList<Figure> rab = new ArrayList<>();
            rab.add(new_rabbit);

            Tree tree_m = new Tree(new Node(rab,0));
            tree_m.getRoot().addChild(new Node(move_w,1));
            ArrayList<ArrayList<Figure>> previous_wolf = new ArrayList<>();
            previous_wolf.add(move_w);
            tree_m.make_tree(tree_m.getRoot().getChildren().get(0),previous_wolf,rab,1);
            setTree(tree_m);
        }


    }
}
