
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

/*
    @Override
    public char getColumn() {
        return column;
    }
    @Override
    public int getRow() {
        return row;
    }

    public void setColumn(char x){
        this.column = x;
    }

    public void setRow(int x){
        this.row = x;
    }

    public void setHitsTilSunk(int x){
        this.hitsTilSunk = x;
    }
*/
    //calls blowupship returns hits and miss
    /*public boolean isHit(){
        //if hitsTilSunk > 0 return false if hitsTilSunk == 0 return true
        return true;//just returning true for now so there is no errors
    }*/
/*
    public void hit(){
        hitsTilSunk--;
    }

    public boolean blowUpShip(){
        if(hitsTilSunk == 0){
            return true;
        }
        //returns true for hitsTilSunk == 0
        //else false
        return false;//just returning true for now so there is no errors
    }*/

    /*
    public int setHitsTilSunk(int num){
        return num;//just returning num for now so there is no errors
    }

    public boolean hit(){
        // if hitsTilSunk == 0
        // hit = true
        //else
        // hit = false
        return true;//just returning true for now so there is no errors
    }
*/
}

