package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static cs361.battleships.models.AtackStatus.*;

public class Game {

    @JsonProperty private Board playersBoard = new Board();
    @JsonProperty private Board opponentsBoard = new Board();

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	This placeShip function calls the board's placeship function and returns a success or not
	 */
    public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
        boolean successful = playersBoard.placeShip(ship, x, y, isVertical);
        if (!successful)
            return false;

        boolean opponentPlacedSuccessfully;
        do {
            // AI places random ships, so it might try and place overlapping ships
            // let it try until it gets it right
            opponentPlacedSuccessfully = opponentsBoard.placeShip(ship, randRow(), randCol(), randVertical());
        } while (!opponentPlacedSuccessfully);
        return true;
    }

    /*
	DO NOT change the signature of this method. It is used by the grading scripts.
	This calls the board's attack function and returns if it was a valid attack or not.
	 */
    public boolean attack(int x, char  y) {
        List<Result> playerAttack = opponentsBoard.attack(x, y);
        if (playerAttack.get(0).getResult() == INVALID) {
            return false;
        }
        List<Result> opponentAttackResult;
        do {
            // AI does random attacks, so it might attack the same spot twice
            // let it try until it gets it right
            opponentAttackResult = playersBoard.attack(randRow(), randCol());
        } while(opponentAttackResult.get(0).getResult() == INVALID);

        return true;
    }
    //This is the driving function for the sonar, it calls the board's activateSonar function
    public boolean sonarAttack(int x, char y){
        return opponentsBoard.activateSonar(x,y);
    }

    //These three function are for opponent placing and attacking
    //Get random column
    private char randCol() {
        int random = new Random().nextInt(10);
        return (char) ('A' + random);
    }
    //Get random row
    private int randRow() {
        return  new Random().nextInt(10) + 1;
    }

    //Get a random boolean
    private boolean randVertical() {
        return new Random().nextBoolean();
    }

    //Get the boards, again, I don't know what you were expecting
    public Board getPlayersBoard(){ return playersBoard; }
    public Board getOpponentsBoard() { return opponentsBoard; }
}
