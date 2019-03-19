package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, property="@Minesweeper")
public class Minesweeper extends Ship {
    //Constructor for the Minesweeper
    public Minesweeper() {
        super();
        this.kind = "MINESWEEPER";
        this.hitsTilSunk = 1;
        this.size = 2;
        this.submerged = 0;
        this.cqSquare = new CaptainsQuarters();
        this.occupiedSquares = new ArrayList<>();
    }


    //This function adds a ship to the board by filling in the occupied squares array
    //with the squares of the new ship. It uses the isVertical boolean to determine
    //the orientation of the squares added
    @Override
    public void place(char col, int row, boolean isVertical) {
        cqSquare.setColumn(col);
        cqSquare.setRow(row);
        cqSquare.setHitsTilSunk(hitsTilSunk);
        occupiedSquares.add(cqSquare);   //set the captains quarter square
        if (isVertical) {    //set the non-captains quarter square
            occupiedSquares.add(new Square(row+1, col));
        } else {
            occupiedSquares.add(new Square(row, (char) (col+1)));
        }
        System.out.println("MINESWEEPER: " + occupiedSquares);
    }
}