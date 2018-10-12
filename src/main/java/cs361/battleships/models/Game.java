package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();
    Random rand = new Random();
    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(new Ship(ship.getKind()), randRow(), randCol(), randVertical());
        } while (!opponentPlacedSuccessfully);

        return true;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
    public boolean attack(int x, char  y) {
        Result playerAttack = opponentsBoard.attack(x, y);
        if (playerAttack.getResult() == INVALID) {
            return false;
        }

        Result opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            opponentAttackResult = playersBoard.attack(randRow(), randCol());
        } while(opponentAttackResult.getResult() == INVALID);

        return true;
    }

    private char randCol() {
        int tempcol= rand.nextInt(10); //generate random value between 0 and 9
        tempcol=tempcol+65; //ADD 65 to get to ascii values for uppercase letters
        char col = (char) tempcol; //cast to a char
        return col;
    }

    private int randRow() {
        int row= rand.nextInt(10);
        row++;//add one so the range is 1-10 instead of 0-9
        return row;
    }

    private boolean randVertical() {
        int fiftyfifty=rand.nextInt(2);//0 or 1 gives a fifty fifty chance of vertical or not
        if(fiftyfifty==0){
            return false;
        }else{
            return true;
        }
    }
}
