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
		//final var placedShip = getShip;
		var placedShip = getShip;
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

	//once the sonar button is clicked, the square chosen by the user will
	//expose the value of itself and 13 surrounding results
	//returns a bool to use in game.java
	public boolean activateSonar(int x, char y) {
		List<Square> sonarSquares = sonar.getAllSquares(x, y);
		List<Square> notGuessedOverlap = sonarSquares;

		//if the result is already set, remove it from the array and do nothing
		for (Result anAttack : attacks) {
			if (sonarSquares.contains(anAttack.getLocation())) {
				notGuessedOverlap.remove(anAttack.getLocation());
			}
		}
			//checks if any of the ships contain the squares in the sonar range
			// if a ship's square is in the sonar, check if it's been hit, change the result status to OCCUPIED,
			//and add it to the attacks. remove from array when result is set
			for (Ship ship : ships) {
				for (Square shipSq : ship.getOccupiedSquares()) {
					if (sonarSquares.contains(shipSq)) {
						Result r = sonarResult(shipSq, AtackStatus.OCCUPIED, true, ship);
						attacks.add(r);
						notGuessedOverlap.remove(shipSq);
					}
				}
			}
			//all the remaining squares need to be colored gray, since they haven'nt been guessed yet and there
			//is no ship on it. add a new result for each to attacks
			for (Square sq : notGuessedOverlap) {
				Ship none = null;
				if(checkSquareBounds(sq)) {
					Result r2 = sonarResult(sq, AtackStatus.REVEALED, false, none);
					attacks.add(r2);
				} else {
					return false;
				}
			}

			sonar.setUsages(sonar.getUsages()+1);
		return true;
	}

	//allocates memory for and initializes a result to be added to the attacks list
	private Result sonarResult(Square sq, AtackStatus classname, boolean hasShip, Ship ship){
		Result r = new Result();
		r.setLocation(sq);
		r.setResult(classname);
		if(hasShip) {
			r.setShip(ship);
		}
		return r;
	}


	public List<Ship> getShips() {
		return ships;
	}

	public void moveFleet(char dir) {
		//move all the locations in each ship
		for (Ship s : ships) {
				//check if the ship can be moved in the bounds of the board
				boolean goodMove = checkShipBoundsWithMove(s, dir);
				if (goodMove) {
					s.moveShip(dir);
				}
		}
	}

	private boolean checkSquareBounds(Square square){

		if (square.getRow() < 1 || square.getRow() > 10) { return false; }
		if (square.getColumn() < 'A' || square.getColumn() > 'J'){ return false; }
		return true;

	}

	public boolean checkShipBoundsWithMove(Ship s, char dir) {
		//check if all squares are in bound,
		//if out of bound, return false and don't move ship
		for (Square sq : s.getOccupiedSquares()) {
			if (dir == 'u') {
				if (sq.getRow() - 1 < 1 || sq.getRow() - 1 > 10) {
					return false;
				}
			} else if (dir == 'd') {
				if (sq.getRow() + 1 < 1 || sq.getRow() + 1 > 10) {
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