
package cs361.battleships.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, property="@CaptainsQuarters")

public class CaptainsQuarters extends Square{
    //Default constructor
    public CaptainsQuarters(){
        this.row = -1;
        this.column = 'z';
    }
    //Better constructor
    public CaptainsQuarters(int row, char column, int captainHitNum) {
        this.row = row;
        this.column = column;
        this.hitsTilSunk = captainHitNum;
    }
    //Decrement the hitsTilSunk counter that gives the Captain's Quarters its armor
    public void decHitsTilSunk(){
        this.hitsTilSunk--;
    }

}

