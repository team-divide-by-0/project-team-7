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
        //after three ships are placed, start the game
        if(this.ships.size() >= 3){
            return false;
        }

        //get the length of the ship to check all possible values and to use in loops
        int length = 0;
        if (ship.getKind().equals("MINESWEEPER")) {
            length = 2;
        } else if (ship.getKind().equals("DESTROYER")) {
            length = 3;
        } else if (ship.getKind().equals("BATTLESHIP")) {
            length = 4;
        } else {
            return false;
        }

        boolean inBounds; //used for checking bounds on each square

        //create a list of squares to be placed if they are valid
        //this list will be used for the rest of the function
        ArrayList<Square> attempts = new ArrayList<>();
        for(int i = 0; i < length; i++) {
            if(isVertical){
                //for vertical placements, increase the row number and add square to list
                attempts.add(new Square(x+i, y));
            } else {
                int newCol = y;
                newCol = newCol + i;
                //add all the columns to the right
                attempts.add(new Square(x, (char)newCol));
            }
        }

        //check every attempt square is in bounds
        for(Square sq : attempts) {
            inBounds = checkBounds(sq.getRow(), sq.getColumn());
            if(!inBounds){
                System.out.println("Bounds check failed on " + sq.getRow() + " , " + sq.getColumn());
                return false;
            }
        }

        //check all the squares in all the ships that are occupied
        //if attempt has a matching row and col, return false
        for(Ship placed : this.ships){
            for(Square placedSq : placed.getOccupiedSquares()) {
                for(Square attemptSq : attempts) {
                    if(placedSq.getRow() == attemptSq.getRow() && placedSq.getColumn() == attemptSq.getColumn()){
                        return false;
                    }
                }
            }
        }

        //all ships on the board have been checked, add ship to all ships list
        System.out.println("Adding ship");
        ship.setOccupiedSquares(attempts);
        ships.add(ship);
        return true;
    }

    public boolean checkBounds(int x, char y){
        if (x < 1 || x > 10 || y < 'A' || y > 'J') {
            System.out.println("out of bounds");
            return false;
        } else {
         return true;
        }
    }

    /*
    DO NOT change the signature of this method. It is used by the grading scripts.
     */
    public Result attack(int x, char y) {
        //System.out.print("in function\n");
        if ((x < 1 || x > 10) || (y < 'A' || y > 'J')) {
            Result res = new Result();
            res.setResult(INVALID);
            res.setShip(null);
            Square sq = new Square(0, 'x');
            res.setLocation(sq);
            return res;
        }
        for (int i = 0; i < attacks.size(); i++) {
            if (x == attacks.get(i).getLocation().getRow() && y == attacks.get(i).getLocation().getColumn()) {
                Result res = new Result();
                res.setResult(INVALID);
                res.setShip(null);
                Square sq = new Square(0, 'x');
                res.setLocation(sq);
                return res;
            }
        }
        attacks.add(new Result());
        int where = attacks.size() - 1;
        for (int i = 0; i < ships.size(); i++) {    //search through the ships
            for (int j = 0; j < ships.get(i).getOccupiedSquares().size(); j++) {    //search through the squares of each ship
                if ((ships.get(i).getOccupiedSquares().get(j).getRow() == x) && (ships.get(i).getOccupiedSquares().get(j).getColumn() == y)) {
                    attacks.get(where).setResult(HIT);
                    attacks.get(where).setShip(ships.get(i));
                    attacks.get(where).setLocation(ships.get(i).getOccupiedSquares().get(j));
                    ships.get(i).removeOccupiedSquares(x, y);
                    if (checkSunk(ships.get(i), ships, i)) {
                        attacks.get(where).setResult(SUNK);
                    }
                    if (checkSurrender(ships)) {
                        attacks.get(where).setResult(SURRENDER);
                    }
                    return attacks.get(where);
                }
            }
        }
        attacks.get(where).setResult(MISS);
        attacks.get(where).setLocation(new Square(x, y));
        attacks.get(where).setShip(null);
        return attacks.get(where);
    }

    public boolean checkSunk(Ship ship, List<Ship> ships, int i) {
        if (ship.getOccupiedSquares().size() == 0) {
            ships.remove(i);
            return true;
        }
        return false;
    }

    public boolean checkSurrender(List<Ship> ships) {
        if (ships.size() == 0) {
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
