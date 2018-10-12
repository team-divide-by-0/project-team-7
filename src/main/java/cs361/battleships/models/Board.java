package cs361.battleships.models;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

import static cs361.battleships.models.AtackStatus.*;

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
	    //System.out.print("in function\n");
        if((x < 1 || x > 10) || (y < 'A' || y > 'J')){
            Result res=new Result();
            res.setResult(INVALID);
            res.setShip(null);
            Square sq = new Square(0, 'x');
            res.setLocation(sq);
            return res;
        }
        for(int i=0; i<attacks.size(); i++){
            if(x == attacks.get(i).getLocation().getRow() && y == attacks.get(i).getLocation().getColumn()){
                Result res=new Result();
                res.setResult(INVALID);
                res.setShip(null);
                Square sq = new Square(0, 'x');
                res.setLocation(sq);
                return res;
            }
        }
        attacks.add(new Result());
        int where = attacks.size() - 1;
        for(int i=0; i<ships.size(); i++) {    //search through the ships
            //System.out.print(i);
            for(int j=0; j<ships.get(i).getOccupiedSquares().size(); j++) {    //search through the squares of each ship
               // System.out.print(j);
                //System.out.print('\n');
                if ((ships.get(i).getOccupiedSquares().get(j).getRow() == x) && (ships.get(i).getOccupiedSquares().get(j).getColumn() == y)) {
                    if (checkSunk(ships.get(i), ships, i) && checkSurrender(ships)) {
                        System.out.print("HERE\n");
                        attacks.get(where).setResult(SURRENDER);
                        attacks.get(where).setShip(ships.get(i));
                        attacks.get(where).setLocation(ships.get(i).getOccupiedSquares().get(j));
                        ships.get(i).removeOccupiedSquares(x, y);
                        ships.remove(i);
                        return attacks.get(where);
                    } else if (checkSunk(ships.get(i), ships, i) && !checkSurrender(ships)) {
                        System.out.print("HEREHERE\n");
                        attacks.get(where).setResult(SUNK);
                        attacks.get(where).setShip(ships.get(i));
                        attacks.get(where).setLocation(ships.get(i).getOccupiedSquares().get(j));
                        ships.get(i).removeOccupiedSquares(x, y);
                        return attacks.get(where);
                    } else if (!checkSunk(ships.get(i), ships, i) && !checkSurrender(ships)) {
                        attacks.get(where).setResult(HIT);
                        attacks.get(where).setShip(ships.get(i));
                        attacks.get(where).setLocation(ships.get(i).getOccupiedSquares().get(j));
                        ships.get(i).removeOccupiedSquares(x, y);
                        return attacks.get(where);
                    }
                }
            }
        }
        attacks.get(where).setResult(MISS);
        attacks.get(where).setLocation(new Square(x, y));
        attacks.get(where).setShip(null);
        return attacks.get(where);
    }

    public boolean checkSunk(Ship ship, List<Ship> ships, int i) {
        if(ship.getOccupiedSquares().size() == 0) {
            return true;
        }
        return false;
    }

    public boolean checkSurrender(List<Ship> ships){
	    if(ships.size() == 0){
	        return true;
        }
        return false;
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
