import ArrWork.Figure;
import boad_view.*;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        System.out.println("Hello world!");
        board board = new board();
        board.setup_board();

    }

    @Test
    void testDuplicates()
    {
        ArrayList<Figure>wolves = new ArrayList<>();
        Figure wolf = new Figure(6,7,false);
        wolves.add(wolf);
        wolf = new Figure(6,7,false);
        wolves.add(wolf);
        wolf = new Figure(5,0,false);
        wolves.add(wolf);
        wolf = new Figure(7,0,false);
        wolves.add(wolf);
        System.out.println(Figure.check_for_duplicates(wolves,null));
    }
}