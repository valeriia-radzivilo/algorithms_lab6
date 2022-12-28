package ArrWork;

import java.util.ArrayList;

public class Node {
    private final ArrayList<Figure> figure;
    private final int depth;
    private final ArrayList<Node> children ;
    int value;

    boolean visited = false;

    public Node(ArrayList<Figure> figure, int depth){
        this.figure = figure;
        this.children = new ArrayList<>();
        this.depth = depth;

    }



    public int getValue ()
    {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
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