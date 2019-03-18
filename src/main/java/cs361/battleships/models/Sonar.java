package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class Sonar {
    @JsonProperty private List<Square> squares;
    @JsonProperty private int usages;

    public List<Square> getSquares() {
        return squares;
    }

    public Sonar(){
        squares = new ArrayList<Square>(); usages = 0;
    }

    public int getUsages() { return usages; }
    public void setUsages(int usages) { this.usages = usages; }

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
        }


        return squares;
    }
}
