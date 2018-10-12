package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private List<Result> attacks;
	private List<Ship> ships;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {

		attacks = new ArrayList<>();
		ships = new ArrayList<>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
	    ship = new Ship(ship.getKind());
        //get the length of the ship to check all possible values and to use in loops
        int length = 0;
        if(ship.getKind().equals("MINESWEEPER")){
            length = 2;
        } else if(ship.getKind().equals("DESTROYER")){
            length = 3;
        } else if(ship.getKind().equals("BATTLESHIP")){
            length = 4;
        } else {
            return false;
        }

        //set an attempt square and store all the ships in a variable to compare
        Square attempt = new Square(x,y);
        List<Ship> all = this.getShips();
        for(Ship s : all)
        {
            if (s.getOccupiedSquares().contains(attempt)) {
                return false;
            }
            if(isVertical) {
                //check all the squares above
                for (int i = 1; i < length; i++) {
                    attempt.setRow(x-1);
                    if (s.getOccupiedSquares().contains(attempt)){
                        return false;
                    }
                }
            } else {
                //check all the columns to the right
                for(int i = 1; i < length; i++){
                    int newCol = y;
                    newCol = newCol+i;
                    attempt.setColumn((char)newCol);
                    if(s.getOccupiedSquares().contains(attempt)){
                        return false;
                    }

                }
            }

            int colNum = y;

            //out of board bounds
            if(x > 9 || x < 0 || colNum > 74 || colNum < 65){
                return false;
            }
        }

        //all ships on the board have been checked, add ship to all ships list
        ship.setOccupiedSquares(x, y);
        for(int i = 1; i < length; i++){
            if(isVertical){
                //vertical ship adds the rows above the current square
                int newRow = x+i;
                ship.setOccupiedSquares(newRow, y);
            } else {
                //horizontal ships add all the rows to the right
                int newCol = y + i;
                char col = (char)newCol;
                ship.setOccupiedSquares(x, col);
            }
        }

        all.add(ship);
        setShips(all);
        return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		//TODO Implement
		return null;
	}

	public List<Ship> getShips() {
		return this.ships;
	}

	public void setShips(List<Ship> ships) {

		this.ships = ships;
	}

	public List<Result> getAttacks() {
		return this.attacks;
	}

	public void setAttacks(List<Result> attacks) {
		this.attacks.addAll(attacks);
	}
}
