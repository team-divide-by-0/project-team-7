package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {

	@JsonProperty private AtackStatus result;
	@JsonProperty private Square location;
	@JsonProperty private Ship ship;

	@SuppressWarnings("unused")
	public Result() {
	}

	public Result(Square location) {
		result = AtackStatus.MISS;
		this.location = location;
	}

	//The below functions are just the getters and setters for the result class.
	public AtackStatus getResult() {
		return result;
	}

	public void setResult( AtackStatus result) {
		this.result = result;
	}

	public void setLocation( Square location) { this.location = location; }

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Square getLocation() {
		return location;
	}
}
