package cs361.battleships.models;

public class Destroyer extends Ship {
    public Destroyer() {
        super();
        //this.cqSquare = new CaptainsQuarters(-1, 'z', -1);
        this.kind = "DESTROYER";
        this.hitsTilSunk= 2;
        this.size = 3;
    }
/*
    @Override
    public void place(char col, int row, boolean isVertical) {
        // System.out.println("DESTROYER");
        if (isVertical) {
            occupiedSquares.add(new Square(row, col));
            cqSquare = new CaptainsQuarters(row+1,col, captainHitNum);
            occupiedSquares.add(cqSquare);   //set the captain quarter square
            occupiedSquares.add(new Square(row+2, col));
        } else {
            occupiedSquares.add(new Square(row, (col)));
            cqSquare = new CaptainsQuarters(row,(char) (col + 1), captainHitNum);
            occupiedSquares.add(cqSquare);  //set the captain quarter square
            occupiedSquares.add(new Square(row, (char) (col + 2)));
        }
    }*/
}