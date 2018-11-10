package cs361.battleships.models;

public class Battleship extends Ship {
    public Battleship() {
        super();
        //this.cqSquare = new CaptainsQuarters(-1, 'z', -1);
        this.kind = "BATTLESHIP";
        this.hitsTilSunk = 2;
        this.size = 4;
    }
/*
    @Override
    public void place(char col, int row, boolean isVertical) {
        // System.out.println("BATTLESHIP");
        if (isVertical) {
            occupiedSquares.add(new Square(row, col));
            occupiedSquares.add(new Square(row+1, col));
            cqSquare = new CaptainsQuarters(row+2,col, captainHitNum);
            occupiedSquares.add(cqSquare);   //set the captain quarter square
            occupiedSquares.add(new Square(row+3, col));
        } else {
            occupiedSquares.add(new Square(row, (col)));
            occupiedSquares.add(new Square(row, (char) (col + 1)));
            cqSquare = new CaptainsQuarters(row, (char) (col + 2), captainHitNum);
            occupiedSquares.add(cqSquare);  //set the captain quarter square
            occupiedSquares.add(new Square(row, (char) (col + 3)));
        }
    }*/
}