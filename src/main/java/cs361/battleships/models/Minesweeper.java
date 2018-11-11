package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Minesweeper extends Ship {
    public Minesweeper() {
        super();
        //System.out.println("IN MINESWEEPER CONSTRUCTOR");
        //System.out.println("IN CONSTRUCTOR");
        //this.cqSquare = new CaptainsQuarters(-1, 'z', -1);
        this.kind = "MINESWEEPER";
        this.hitsTilSunk = 1;
        this.size = 2;
        //this.occupiedSquares = new ArrayList<>();

        //this.sq = new Square(2, 'b');
    }
/*
    public CaptainsQuarters getcqSquare(){
        return cqSquare;
    }
    @Override
    public List<Square> getOccupiedSquares() {
        return occupiedSquares;
    }

    public void setOccupiedSquares(List<Square> os){occupiedSquares=os;}

    @Override
    public void print(){
        super.print();
        System.out.println("I'm in the minesweeper function");
    }
    @Override
    public void place(char col, int row, boolean isVertical) {
        //System.out.println("IN MINESWEEPER");
        //cqSquare = new CaptainsQuarters(row, col, captainHitNum);
        cqSquare.setColumn(col);
        cqSquare.setRow(row);
        cqSquare.setHitsTilSunk(captainHitNum);
        System.out.println("cq in child:"+cqSquare.getColumn());
        occupiedSquares.add(cqSquare);   //set the captains quarter square
        if (isVertical) {    //set the non-captains quarter square
            occupiedSquares.add(new Square(row+1, col));
        } else {
            occupiedSquares.add(new Square(row, (char) (col + 1)));
        }
        System.out.println(occupiedSquares);
    }

    @Override
    public Result attack(int x, char y){
        var attackedLocation = new Square(x, y);
        var square = getOccupiedSquares().stream().filter(s -> s.equals(attackedLocation)).findFirst();
        System.out.println(square.getClass());
        System.out.println(occupiedSquares);
        if (!square.isPresent()) {
            System.out.println("FIRST IF");
            return new Result(attackedLocation);
        }
        var attackedSquare = square.get();
        //If Attacked Square is a Captain's Quarters call attack Caps Quarters
        //else
        System.out.println(cqSquare);
        if(attackedSquare.getRow() == cqSquare.getRow() && attackedSquare.getColumn() == cqSquare.getColumn()){
            System.out.println("I AM CAPTAINS QUARTERS");
        }
        if (attackedSquare.isHit()) {
            System.out.println("SECOND IF");
            var result = new Result(attackedLocation);
            result.setResult(AtackStatus.INVALID);
            return result;
        }
        attackedSquare.hit();
        var result = new Result(attackedLocation);
        result.setShip(this);
        if (isSunk()) {
            result.setResult(AtackStatus.SUNK);
        } else {
            result.setResult(AtackStatus.HIT);
        }
        return result;
    }

*/
}