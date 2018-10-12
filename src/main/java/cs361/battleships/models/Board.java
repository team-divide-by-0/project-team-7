package cs361.battleships.models;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private List<Result> attack;
	private List<Ship> ships;
	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		attack = new ArrayList<Result>();
		ships = new ArrayList<Ship>();
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
		this.ships.addAll(ships);
	}

	public List<Result> getAttacks() {
		return this.attack;
	}

	public void setAttacks(List<Result> attacks) {
		this.attack.addAll(attacks);
	}
}
