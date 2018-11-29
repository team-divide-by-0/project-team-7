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

	public AtackStatus getResult() {
		return result;
	}

	public void setResult(AtackStatus result) {
		this.result = result;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Square getLocation() {
		return location;
	}

	public void setLocation(Square s) { this.location = s; }

	//changes the square based on the direction parameter
/*	public void newLocation(char dir) {
		Square movedSq = new Square(this.location.getRow(), this.location.getColumn());
		if(dir == 'u' || dir == 'U'){
			movedSq.setRow(location.getRow() + 1);
		} else if(dir == 'd' || dir == 'D'){
			movedSq.setRow(location.getRow() - 1);
		} else if(dir == 'l' || dir == 'L'){
			char newCol = movedSq.getColumn();
			newCol = (char)(newCol-1);
			movedSq.setColumn(newCol);
		} else if(dir == 'r' || dir == 'R'){
			char newCol = movedSq.getColumn();
			newCol = (char)(newCol+1);
			movedSq.setColumn(newCol);
		}
		if(!movedSq.isOutOfBounds()) {
			setLocation(movedSq);
		}
	}*/
}
