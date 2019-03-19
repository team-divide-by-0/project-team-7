package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.CLASS, property="@Battleship")
public class Battleship extends Ship {
    //Constructor for the Battleship
    public Battleship() {
        super();
        this.kind = "BATTLESHIP";
        this.hitsTilSunk = 2;
        this.size = 4;
        this.submerged = 0;
    }

    //This function adds a ship to the board by filling in the occupied squares array
    //with the squares of the new ship. It uses the isVertical boolean to determine
    //the orientation of the squares added
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
        System.out.println("BATTLESHIP: " + occupiedSquares);
    }
}