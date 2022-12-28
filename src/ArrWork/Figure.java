package ArrWork;

import java.util.ArrayList;

public class Figure {
    int x;
    int y;
    boolean isRabbit;


    public Figure(int x, int y, boolean isRabbit)
    {
        this.x = x;
        this.y = y;
        this.isRabbit = isRabbit;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public ArrayList<Integer> getCoords()
    {
        ArrayList<Integer> x_y = new ArrayList<>();
        x_y.add(x);
        x_y.add(y);
        return x_y;
    }

    public static boolean check_for_duplicates(ArrayList<Figure> wolves, Figure needed_to_add)
    {
        for(int i =0;i<wolves.size();i++)
        {
            for(int j =0; j< wolves.size();j++)
            {
                if(i!=j && wolves.get(i).getX()==wolves.get(j).getX()&& wolves.get(i).getY()==wolves.get(j).getY())
                    return false;
            }
        }

        return true;
    }
}
