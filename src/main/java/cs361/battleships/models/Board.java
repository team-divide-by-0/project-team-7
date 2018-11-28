package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

	@JsonProperty private List<Ship> ships;
	@JsonProperty private List<Result> attacks;
	@JsonProperty private Sonar sonar;
	@JsonProperty private List<Ship> sunkShips;

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
		if(ship.getKind().equals("MINESWEEPER")){
			getShip = new Minesweeper();
		}
		else if(ship.getKind().equals("DESTROYER")){
			getShip = new Destroyer();
		}
		else if(ship.getKind().equals("BATTLESHIP")){
			getShip = new Battleship();
		}
		else if(ship.getKind().equals("SUBMARINE")){
			getShip = new Submarine();
		}
		final var placedShip = getShip;
		placedShip.place(y, x, isVertical);

		for(Ship i: ships){
			if (i.overlaps(placedShip)) {
				if (i instanceof Submarine) {
					((Submarine) i).setSubmerged(1);
				}
				else if (placedShip instanceof Submarine) {
					((Submarine) i).setSubmerged(1);
				}
				else{
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
		for(Result i : attackResult) {
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
		return attackResult;
	}

	List<Ship> getShips() {
		return ships;
	}

	List<Ship> getSunkShips(){ return sunkShips;}

	List<Result> getResults() { return attacks; }

	/*public List<Result> activateSonar(int x, char y) {
		List<Square> sonarSqs = sonar.getAllSquares(x, y);
		Result tempResult;
		List<Result> tempResults = new ArrayList<>();
		for (var s : sonarSqs) {
			tempResult = attack(s);
			if(tempResult.getResult() == AtackStatus.MISS) {
				tempResult.setResult(AtackStatus.REVEALED);
				attacks.add((tempResult));
				tempResults.add(tempResult);
			} else if(tempResult.getResult() == AtackStatus.HIT || tempResult.getResult() == AtackStatus.SUNK) {
				tempResult.setResult(AtackStatus.OCCUPIED);
				attacks.add((tempResult));
				tempResults.add(tempResult);
			}
		}
		return tempResults;
	}*/
}
