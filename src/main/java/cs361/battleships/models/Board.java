package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.stream.Collectors;

public class Board {

	@JsonProperty
	private List<Ship> ships;
	@JsonProperty
	private List<Result> attacks;
	@JsonProperty
	private Sonar sonar;
	@JsonProperty
	private List<Ship> sunkShips;

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public Board() {
		ships = new ArrayList<>();
		attacks = new ArrayList<>();
		sonar = new Sonar();
		sunkShips = new ArrayList<>();
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public boolean placeShip(Ship ship, int x, char y, boolean isVertical) {
		if (ships.size() >= 4) {
			return false;
		}
		if (ships.stream().anyMatch(s -> s.getKind().equals(ship.getKind()))) {
			return false;
		}
		Ship getShip = new Ship(ship.getKind());
		if (ship.getKind().equals("MINESWEEPER")) {
			getShip = new Minesweeper();
		} else if (ship.getKind().equals("DESTROYER")) {
			getShip = new Destroyer();
		} else if (ship.getKind().equals("BATTLESHIP")) {
			getShip = new Battleship();
		} else if (ship.getKind().equals("SUBMARINE")) {
			getShip = new Submarine();
		}
		final var placedShip = getShip;
		placedShip.place(y, x, isVertical);

		for (Ship i : ships) {
			if (i.overlaps(placedShip)) {
				if (i instanceof Submarine) {
					((Submarine) i).setSubmerged(1);
				} else if (placedShip instanceof Submarine) {
					((Submarine) placedShip).setSubmerged(1);
				} else {
					return false;
				}
			}
		}

		/*if (ships.stream().anyMatch(s -> s.overlaps(placedShip))) {
			if(placedShip instanceof Submarine){
				((Submarine) placedShip).setSubmerged(1);
				return true;
			}
			return false;
		}*/
		if (placedShip.getOccupiedSquares().stream().anyMatch(s -> s.isOutOfBounds())) {
			return false;
		}
		ships.add(placedShip);
		return true;
	}

	/*
	DO NOT change the signature of this method. It is used by the grading scripts.
	 */
	public List<Result> attack(int x, char y) {
		List<Result> attackResult = attack(new Square(x, y));
		for (Result i : attackResult) {
			attacks.add(i);
		}
		return attackResult;
	}

	private List<Result> attack(Square s) {
		var shipsAtLocation = ships.stream().filter(ship -> ship.isAtLocation(s)).collect(Collectors.toList());
		if (shipsAtLocation.size() == 0) {
			s.hit();
			var attackResult = new Result(s);
			List<Result> r = new ArrayList<>();
			r.add(attackResult);
			return r;
		}
		var hitShip = shipsAtLocation.get(0);

		List<Result> attackResult = hitShip.attack(s.getRow(), s.getColumn());
		if (attackResult.get(0).getResult() == AtackStatus.SUNK) {
			sunkShips.add(hitShip);
			if (ships.stream().allMatch(ship -> ship.isSunk())) {
				attackResult.get(0).setResult(AtackStatus.SURRENDER);
			}
		}
		if(shipsAtLocation.size() == 2 && sunkShips.size() > 0){
			hitShip = shipsAtLocation.get(1);

			 attackResult.addAll(hitShip.attack(s.getRow(), s.getColumn()));
			if (attackResult.get(0).getResult() == AtackStatus.SUNK) {
				sunkShips.add(hitShip);
				if (ships.stream().allMatch(ship -> ship.isSunk())) {
					attackResult.get(0).setResult(AtackStatus.SURRENDER);
				}
			}
		}
		return attackResult;
	}

	List<Ship> getShips() {
		return ships;
	}

	public void moveFleet(char dir) {
		//move all the locations in each ship
		for (Ship s : ships) {
				//check if the ship can be moved in the bounds of the board
				boolean goodMove = checkShipBounds(s, dir);
				if (goodMove) {
					s.moveShip(dir);
				}
		}
	}

	public boolean checkShipBounds(Ship s, char dir) {
		//check if all squares are in bound,
		//if out of bound, return false and don't move ship
		for (Square sq : s.getOccupiedSquares()) {
			if (dir == 'u') {
				if (sq.getRow() - 1 > 10 || sq.getRow() - 1 < 1) {
					return false;
				}
			} else if (dir == 'd') {
				if (sq.getRow() + 1 > 10 || sq.getRow() + 1 < 1) {
					return false;
				}
			} else if (dir == 'l') {
				if (sq.getColumn() - 1 < 'A' || sq.getColumn() - 1 > 'J') {
					return false;
				}
			} else if (dir == 'r') {
				if (sq.getColumn() + 1 < 'A' || sq.getColumn() + 1 > 'J') {
					return false;
				}
			}
		}
		return true;
	}
}