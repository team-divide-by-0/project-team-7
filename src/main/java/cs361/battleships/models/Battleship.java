package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, property="@Battleship")
public class Battleship extends Ship {
    public Battleship() {
        super();
        this.kind = "BATTLESHIP";
        this.hitsTilSunk = 2;
        this.size = 4;
    }

    @Override
    public void place(char col, int row, boolean isVertical) {
        if (isVertical) {
            occupiedSquares.add(new Square(row, col));
            occupiedSquares.add(new Square(row+1, col));
            cqSquare = new CaptainsQuarters(row+2,col, hitsTilSunk);
            occupiedSquares.add(cqSquare);   //set the captain quarter square
            occupiedSquares.add(new Square(row+3, col));
        } else {
            occupiedSquares.add(new Square(row, (col)));
            occupiedSquares.add(new Square(row, (char) (col + 1)));
            cqSquare = new CaptainsQuarters(row, (char) (col + 2), hitsTilSunk);
            occupiedSquares.add(cqSquare);  //set the captain quarter square
            occupiedSquares.add(new Square(row, (char) (col + 3)));
        }
        System.out.println("DESTROYER: " + occupiedSquares);
    }
}