package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, property="@Ship")
public class Submarine extends Ship{

    //Constructor for the Submarine
    public Submarine() {
        super();
        this.kind = "SUBMARINE";
        this.hitsTilSunk= 2;
        this.size = 5;
        this.submerged = 1;
    }

    //This function adds a ship to the board by filling in the occupied squares array
    //with the squares of the new ship. It uses the isVertical boolean to determine
    //the orientation of the squares added
    @Override
    public void place(char col, int row, boolean isVertical) {
        if (isVertical) {
            occupiedSquares.add(new Square(row, col));
            occupiedSquares.add(new Square(row+1, col));
            occupiedSquares.add(new Square(row+2, col));
            occupiedSquares.add(new Square(row+2, (char) (col+1)));
            cqSquare = new CaptainsQuarters(row+3,col, hitsTilSunk);
            occupiedSquares.add(cqSquare);   //set the captain quarter square
        } else {
            occupiedSquares.add(new Square(row, (col)));
            occupiedSquares.add(new Square(row, (char) (col + 1)));
            occupiedSquares.add(new Square(row, (char) (col + 2)));
            occupiedSquares.add(new Square(row-1, (char) (col + 2)));
            cqSquare = new CaptainsQuarters(row,(char) (col + 3), hitsTilSunk);
            occupiedSquares.add(cqSquare);  //set the captain quarter square
        }
        System.out.println("SUBMARINE: " + occupiedSquares);
    }
}
