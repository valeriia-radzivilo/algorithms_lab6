package ArrWork;

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

    void setX(int new_x)
    {
        this.x = new_x;
    }
    void setY(int new_y)
    {
        this.y = new_y;
    }
}
