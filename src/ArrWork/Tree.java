package ArrWork;
import java.util.ArrayList;

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
        if(depth<8) {
            ArrayList<Figure> new_rabbits = new ArrayList<>();
            for (Figure prev_rabbit : prev_rabbits) {
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
            }
            depth += 1;
            for (Figure f : new_rabbits) {
                ArrayList<Figure> rabbit = new ArrayList<>();
                rabbit.add(f);
                current_node.addChild(new Node(rabbit, depth));
            }
            depth+=1;
            for (int k =0;k<current_node.getChildren().size();k++) {
                Node children_node = current_node.getChildren().get(k);
                ArrayList<ArrayList<Figure>> new_wolves = new ArrayList<>();
                for (ArrayList<Figure> old_wolves : previous_wolves) {
                    for(int l =0; l< old_wolves.size();l++)
                    {
                        if(old_wolves.get(l).getX()+1<8 && old_wolves.get(l).getY()+1<8) {
                            ArrayList<Figure> new_old_wolves = new ArrayList<>(old_wolves);
                            new_old_wolves.set(l,new Figure(old_wolves.get(l).getX()+1,old_wolves.get(l).getY()+1,false));
                            new_wolves.add(new_old_wolves);
                        }
                        if(old_wolves.get(l).getX()-1>0 && old_wolves.get(l).getY()+1<8) {
                            ArrayList<Figure> new_old_wolves = new ArrayList<>(old_wolves);
                            new_old_wolves.set(l,new Figure(old_wolves.get(l).getX()-1,old_wolves.get(l).getY()+1,false));
                            new_wolves.add(new_old_wolves);
                        }
                    }
                }
                for(ArrayList<Figure> newie : new_wolves)
                {
                    Node new_node = new Node(newie,depth);
                    children_node.addChild(new_node);
                    make_tree(new_node,new_wolves,new_rabbits,depth);
                }

            }
        }



    }



    public ArrayList<Figure> possible_options_for_wolves(int depth, Figure last_rabbit, Node current_node)
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
        else if(current_node.getDepth()<depth-2)
            return possible_options_for_wolves(depth,last_rabbit,current_node.getChildren().get(0));
        return answer;
    }
}