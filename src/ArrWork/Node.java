package ArrWork;

import java.util.ArrayList;

public class Node {
    private final ArrayList<Figure> figure;
    private final int depth;
    private final ArrayList<Node> children ;
    int amount_of_conflicts;

    boolean visited = false;

    public Node(ArrayList<Figure> figure, int depth){
        this.figure = figure;
        this.children = new ArrayList<>();
        this.depth = depth;
        this.amount_of_conflicts = 0;
    }

    public int getConflicts ()
    {
        return this.amount_of_conflicts;
    }
    public void setVisited(boolean val)
    {
        visited = val;
    }
    public ArrayList<Figure> get_figure(){
        return figure;
    }



    public void addChild(Node child){
        children.add(child);
    }
    public ArrayList<Node> getChildren() {
        return children;
    }

    public int getDepth() {
        return depth;
    }
}