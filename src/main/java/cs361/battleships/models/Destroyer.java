package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, property="@Destroyer")
public class Destroyer extends Ship {
    public Destroyer() {
        super();
        this.kind = "DESTROYER";
        this.hitsTilSunk= 2;
        this.size = 3;
        this.submerged = 0;
    }

    @Override
    public void place(char col, int row, boolean isVertical) {
        if (isVertical) {
            occupiedSquares.add(new Square(row, col));
            cqSquare = new CaptainsQuarters(row+1,col, hitsTilSunk);
            occupiedSquares.add(cqSquare);   //set the captain quarter square
            occupiedSquares.add(new Square(row+2, col));
        } else {
            occupiedSquares.add(new Square(row, (col)));
            cqSquare = new CaptainsQuarters(row,(char) (col + 1), hitsTilSunk);
            occupiedSquares.add(cqSquare);  //set the captain quarter square
            occupiedSquares.add(new Square(row, (char) (col + 2)));
        }
        System.out.println("DESTROYER: " + occupiedSquares);
    }
}