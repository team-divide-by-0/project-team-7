package cs361.battleships.models;

public class Result {

	private AtackStatus status;
	private Ship ship;
<<<<<<< HEAD
	private Square location;
=======
	private Square square;
>>>>>>> 567626c026de0fc943b26062e6a0d27281787f8a

	public AtackStatus getResult() {
		return this.status;
	}

	public void setResult(AtackStatus result) {
		this.status = result;
	}

	public Ship getShip() {
		return this.ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
	}

	public Square getLocation() {
<<<<<<< HEAD
		return this.location;
	}

	public void setLocation(Square square) {
		this.location = square;
=======
		return this.square;
	}

	public void setLocation(Square square) {
		this.square = square;
>>>>>>> 567626c026de0fc943b26062e6a0d27281787f8a
	}
}
