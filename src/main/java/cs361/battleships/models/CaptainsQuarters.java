
package cs361.battleships.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, property="@CaptainsQuarters")

public class CaptainsQuarters extends Square{

    public CaptainsQuarters(){
        this.row = -1;
        this.column = 'z';
    }

    public CaptainsQuarters(int row, char column, int captainHitNum) {
        this.row = row;
        this.column = column;
        this.hitsTilSunk = captainHitNum;
    }

    public void decHitsTilSunk(){
        this.hitsTilSunk--;
    }

}

