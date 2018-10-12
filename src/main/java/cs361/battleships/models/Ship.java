package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	private String kind;
	
	public Ship(String kind) {
		this.kind = kind;
		this.sunk = false;
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public String getKind() {
		return this.kind;
	}
}
