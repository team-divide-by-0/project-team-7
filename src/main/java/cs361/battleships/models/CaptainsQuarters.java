
package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CaptainsQuarters {

    @JsonProperty private int hitsTilSunk;


    //calls blowupship returns hits and miss
    public boolean isHit(){
        //if hitsTilSunk > 0 return false if hitsTilSunk == 0 return true
        return true;//just returning true for now so there is no errors
    }

    public void decHitsTilSunk(){

    }

    public boolean blowUpShip(){
        //returns true for hitsTilSunk == 0
        //else false
        return true;//just returning true for now so there is no errors
    }

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

}

