package cs361.battleships.models;

import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

public class SonarTest {
    @Test
    public void testSonarWithGrid() {
        Sonar test = new Sonar();
        int r = (int)2 + 1;
        var x = 5;
        assertTrue(test.getSquares().add(new Square(x, (char)r)));
    }

    @Test
    public void testGetSquares() {
       Sonar test = new Sonar();
       test.getAllSquares(1, 'A');
       assertEquals(13,test.getSquares().size());
    }

    @Test
    public void getAllSquares() {
    }


    //Justin's Tests
    @Test
    public void testSonarPlacement() {
        Sonar mySonar = new Sonar();
        assertTrue(mySonar.getSquares().add(new Square(4, 'D')));
    }
    @Test
    public void testCornerSquare() {
        Sonar mySonar = new Sonar();
        assertTrue(mySonar.getSquares().add(new Square(0,'A')));
    }
    @Test
    public void testSonarSquares() {
        Sonar mySonar = new Sonar();
        //mySonar.getSquares().add(new Square(4, 'D'));
        List<Square> mySquares = mySonar.getAllSquares(4, 'D');
        assertTrue(mySquares.size() == 13);
    }


/*

@JsonProperty private List<Square> squares;
    public List<Square> getAllSquares(int x, char y){

        for(int i = 0; i < 3; i++) {
            //adds the 5 horizontal squares
            int r = (int)y + i;
            int l = (int)y - i;
            squares.add(new Square(x, (char)r));
            if(i != 0) {
                squares.add(new Square(x, (char) l));
            }
            if(i != 0){
                //add all the 5 vertical squares
                squares.add(new Square(x+i, y));
                squares.add(new Square(x-i, y));
            }
            //add the outer 4 squares to complete the list of squares
            if(i == 1){
                squares.add(new Square(x+i, (char)r));
                squares.add(new Square(x-i, (char)r));
                squares.add(new Square(x+i, (char)l));
                squares.add(new Square(x-i, (char)l));
            }
        }*/

}
