package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

<<<<<<< HEAD
	private List<Result> attacks;
=======
	private List<Result> attack;
>>>>>>> 567626c026de0fc943b26062e6a0d27281787f8a
	private List<Ship> ships;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
<<<<<<< HEAD
		attacks = new ArrayList<Result>();
=======
		attack = new ArrayList<Result>();
		ships = new ArrayList<Ship>();
>>>>>>> 567626c026de0fc943b26062e6a0d27281787f8a
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		// TODO Implement
		return false;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Result attack(int x, char y) {
		//TODO Implement
		return null;
	}

	public List<Ship> getShips() {
		return this.ships;
	}

	public void setShips(List<Ship> ships) {
<<<<<<< HEAD
		this.ships = ships;
	}

	public List<Result> getAttacks() {
		return this.attacks;
	}

	public void setAttacks(List<Result> attacks) {
		this.attacks = attacks;
=======
		this.ships.addAll(ships);
	}

	public List<Result> getAttacks() {
		return this.attack;
	}

	public void setAttacks(List<Result> attacks) {
		this.attack.addAll(attacks);
>>>>>>> 567626c026de0fc943b26062e6a0d27281787f8a
	}
}
