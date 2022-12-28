package ArrWork;
import java.util.ArrayList;
import java.util.Random;

public class Tree{
    private final Node root;
    public Node getRoot(){
        return root;
    }
    public Tree(Node root){
        this.root = root;
    }

    public void make_tree(Node current_node, ArrayList<ArrayList<Figure>> previous_wolves, ArrayList<Figure> prev_rabbits, int depth)
    {

        if(depth<4) {

            depth += 1;
            for (Figure prev_rabbit : prev_rabbits) {
                ArrayList<Figure> new_rabbits = new ArrayList<>();
                int rabbit_x = prev_rabbit.x;
                int rabbit_y = prev_rabbit.y;
                if (rabbit_x + 1 < 8 && rabbit_y + 1 < 8)
                    new_rabbits.add(new Figure(rabbit_x + 1, rabbit_y + 1, true));
                if (rabbit_x - 1 >= 0 && rabbit_y - 1 >= 0)
                    new_rabbits.add(new Figure(rabbit_x - 1, rabbit_y - 1, true));
                if (rabbit_x - 1 >= 0 && rabbit_y + 1 < 8)
                    new_rabbits.add(new Figure(rabbit_x - 1, rabbit_y + 1, true));
                if (rabbit_x + 1 < 8 && rabbit_y - 1 >= 0)
                    new_rabbits.add(new Figure(rabbit_x + 1, rabbit_y - 1, true));

                for(Figure f: new_rabbits) {

                    ArrayList<Figure> f_list = new ArrayList<>();
                    f_list.add(f);
                    Node children_node = new Node(f_list, depth);
                    current_node.addChild(children_node);


                    for (ArrayList<Figure> old_wolves : previous_wolves) {
                        ArrayList<ArrayList<Figure>> new_wolves = new ArrayList<>();
                        for(int l =0; l< old_wolves.size();l++)
                        {
                            if(old_wolves.get(l).getX()+1<8 && old_wolves.get(l).getY()+1<8 && old_wolves.get(l).getX()!=f.getX()
                                    &&old_wolves.get(l).getY()!=f.getY()
                                    ) {
                                ArrayList<Figure> new_old_wolves = new ArrayList<>(old_wolves);
                                if(Figure.check_for_duplicates(new_old_wolves, f)) {
                                    new_old_wolves.set(l, new Figure(old_wolves.get(l).getX() + 1, old_wolves.get(l).getY() + 1, false));
                                    new_wolves.add(new_old_wolves);
                                }

                            }
                            if(old_wolves.get(l).getX()-1>0 && old_wolves.get(l).getY()+1<8 && old_wolves.get(l).getX()!=f.getX()
                                    &&old_wolves.get(l).getY()!=f.getY() ) {
                                ArrayList<Figure> new_old_wolves = new ArrayList<>(old_wolves);
                                if(Figure.check_for_duplicates(new_old_wolves, f)) {
                                    new_old_wolves.set(l, new Figure(old_wolves.get(l).getX() - 1, old_wolves.get(l).getY() + 1, false));
                                    new_wolves.add(new_old_wolves);
                                }
                            }
                        }
                        if(new_wolves.size()!=0) {
                            for (ArrayList<Figure> newie : new_wolves) {
                                if(Figure.check_for_duplicates(newie, null)) {
                                    Node new_node = new Node(newie, depth + 1);
                                    int val = 0;
                                    for (Figure wolf_f : newie) {
                                        if (wolf_f.y != 0)
                                            val += 10;
                                        val += Math.abs(wolf_f.y - f.y) + Math.abs(wolf_f.x - f.x);
                                    }
                                    new_node.setValue(val);
                                    children_node.addChild(new_node);
                                    ArrayList<ArrayList<Figure>> newie_arr_list = new ArrayList<>();
                                    newie_arr_list.add(newie);

                                    make_tree(new_node, newie_arr_list, f_list, depth + 1);
                                }
                            }
                        }

                    }

                }



            }






            }



    }



    public ArrayList<Figure> possible_options_for_wolves(int depth, Figure last_rabbit, Node current_node, ArrayList<Figure>rabbit_movements, int iter_movements)
    {
        ArrayList<Figure> answer = new ArrayList<>();
        if(current_node.getDepth()==depth-2 ) {
            ArrayList<Figure> last_rabbit_list = new ArrayList<>();
            last_rabbit_list.add(last_rabbit);
            ArrayList<Node> cur_node_children = current_node.getChildren();
            for(Node find: cur_node_children)
            {
                if(find.get_figure().get(0).getX()==last_rabbit_list.get(0).getX() && find.get_figure().get(0).getY()==last_rabbit_list.get(0).getY())
                {
                    answer = new ArrayList<>( find.getChildren().get(0).get_figure());
                    return answer;
                }
            }
        }
        else if(current_node.getDepth()<depth-2) {
            if(current_node.getDepth()%2!=0) {
                for (Node child : current_node.getChildren()) {
                    if(child.get_figure().get(0).getX()==rabbit_movements.get(iter_movements).getX() && child.get_figure().get(0).getY()==rabbit_movements.get(iter_movements).getY())
                    {
                        iter_movements+=1;
                        return possible_options_for_wolves(depth, last_rabbit, child,rabbit_movements,iter_movements);
                    }
                }

            }
            else
                return possible_options_for_wolves(depth, last_rabbit, current_node.getChildren().get(0),rabbit_movements,iter_movements);
        }
        return answer;
    }

    public ArrayList<Figure> alpha_beta_pruning(int depth, Figure last_rabbit, Node current_node, ArrayList<Figure>rabbit_movements, int iter_movements)
    {
        ArrayList<Figure> answer = new ArrayList<>();
        if(current_node.getDepth()==depth-2 ) {
            ArrayList<Figure> last_rabbit_list = new ArrayList<>();
            last_rabbit_list.add(last_rabbit);
            ArrayList<Node> cur_node_children = current_node.getChildren();
            for(Node find: cur_node_children)
            {
                if(find.get_figure().get(0).getX()==last_rabbit_list.get(0).getX() && find.get_figure().get(0).getY()==last_rabbit_list.get(0).getY())
                {
                    int max = 0;
                    Node answ = null;
                    for(Node n: find.getChildren())
                    {
                        if(n.getValue()>max)
                        {
                            max = n.getValue();
                            answ = n;
                        }
                    }
                    assert answ != null;
                    return answ.get_figure();
                }
            }
        }
        else if(current_node.getDepth()<depth-2) {
            if(current_node.getDepth()%2!=0) {
                for (Node child : current_node.getChildren()) {
                    if(child.get_figure().get(0).getX()==rabbit_movements.get(iter_movements).getX() && child.get_figure().get(0).getY()==rabbit_movements.get(iter_movements).getY())
                    {
                        iter_movements+=1;
                        return alpha_beta_pruning(depth, last_rabbit, child,rabbit_movements,iter_movements);
                    }
                }

            }
            else
                return alpha_beta_pruning(depth, last_rabbit, current_node.getChildren().get(0),rabbit_movements,iter_movements);
        }
        return answer;
    }

    public ArrayList<Figure> possible_options_for_wolves_med(int depth, Figure last_rabbit, Node current_node, ArrayList<Figure>rabbit_movements, int iter_movements)
    {
        ArrayList<Figure> answer = new ArrayList<>();
        if(current_node.getDepth()==depth-2 ) {
            ArrayList<Figure> last_rabbit_list = new ArrayList<>();
            last_rabbit_list.add(last_rabbit);
            ArrayList<Node> cur_node_children = current_node.getChildren();
            for(Node find: cur_node_children)
            {
                if(find.get_figure().get(0).getX()==last_rabbit_list.get(0).getX() && find.get_figure().get(0).getY()==last_rabbit_list.get(0).getY())
                {
                    Random random = new Random();
                    int pos = random.nextInt(find.getChildren().size()-1);
                    answer = new ArrayList<>( find.getChildren().get(pos).get_figure());
                    return answer;
                }
            }
        }
        else if(current_node.getDepth()<depth-2) {
            if(current_node.getDepth()%2!=0) {
                for (Node child : current_node.getChildren()) {
                    if(child.get_figure().get(0).getX()==rabbit_movements.get(iter_movements).getX() && child.get_figure().get(0).getY()==rabbit_movements.get(iter_movements).getY())
                    {
                        iter_movements+=1;
                        return possible_options_for_wolves(depth, last_rabbit, child,rabbit_movements,iter_movements);
                    }
                }

            }
            else
                return possible_options_for_wolves(depth, last_rabbit, current_node.getChildren().get(0),rabbit_movements,iter_movements);
        }
        return answer;
    }



}


