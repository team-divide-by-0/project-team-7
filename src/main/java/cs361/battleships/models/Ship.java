package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	
	public Ship(String kind) {
		//TODO implement
		//this is a comment to save a change
	}

	public List<Square> getOccupiedSquares() {
		//TODO implement
		return null;
	}
}
