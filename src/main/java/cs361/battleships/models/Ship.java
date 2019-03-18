package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.Sets;
import com.mchange.v1.util.CollectionUtils;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, property="@Ship")
public class Ship {

	@JsonProperty protected String kind;
	@JsonProperty protected List<Square> occupiedSquares;
	@JsonProperty protected int size;
	@JsonProperty protected int hitsTilSunk;
	protected CaptainsQuarters cqSquare;
	protected int submerged;



	public void setSubmerged(int submerged) {
		this.submerged = submerged;
	}



	public CaptainsQuarters getCqSquare() {
		return cqSquare;
	}




	public Ship() {
		occupiedSquares = new ArrayList<>();
	}
	
	public Ship(String kind) {
		this();
		//System.out.println("IN SHIP ND CONSTRUCTOR");
		this.kind = kind;
		switch(kind) {
			case "MINESWEEPER":
				size = 2;
				break;
			case "DESTROYER":
				size = 3;
				break;
			case "BATTLESHIP":
				size = 4;
				break;
		}
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}

	public void place(char col, int row, boolean isVertical) {
		for (int i=0; i<size; i++) {
			if (isVertical) {
				occupiedSquares.add(new Square(row+i, col));
			} else {
				occupiedSquares.add(new Square(row, (char) (col + i)));
			}
		}
		}

	public boolean overlaps(Ship other) {
		Set<Square> thisSquares = Set.copyOf(getOccupiedSquares());
		Set<Square> otherSquares = Set.copyOf(other.getOccupiedSquares());
		Sets.SetView<Square> intersection = Sets.intersection(thisSquares, otherSquares);
		return intersection.size() != 0;
	}

	public boolean isAtLocation(Square location) {
		return getOccupiedSquares().stream().anyMatch(s -> s.equals(location));
	}

	public String getKind() {
		return kind;
	}

	public List<Result> attack(int x, char y) {
		List<Result> resList = new ArrayList<>();
		//var attackedLocation = new Square(x, y);
		Square attackedLocation = null;
		Square attackedSquare = null;
		for(Square i : occupiedSquares){
			if(i.getRow() == x && i.getColumn() == y){
				attackedLocation = i;
				attackedSquare = i;
			}
		}
		Square square = attackedLocation;
		//var square = getOccupiedSquares().stream().filter(s -> s.equals(attackedLocation)).findFirst();
		if (square == null) {
			attackedLocation = new Square(x, y);
			resList.add(new Result(attackedLocation));
			return resList;
		}
		//var attackedSquare = square.get();
		if(attackedSquare.equals(getCqSquare())) {
			getCqSquare().decHitsTilSunk();
			if (getCqSquare().getHitsTilSunk() <= 0) {
				for (Square i : occupiedSquares) {
					i.hit();
					Result r = new Result(i);
					r.setShip(this);
					r.setResult(AtackStatus.SUNK);
					resList.add(r);
				}
				return resList;
			} else {
				var result = new Result(attackedLocation);
				result.setShip(this);
				result.setResult(AtackStatus.MISS);
				resList.add(result);
				return resList;
			}
		}
		if (attackedSquare.isHit()) {
			var result = new Result(attackedLocation);
			result.setResult(AtackStatus.INVALID);
			resList.add(result);
			return resList;
		}
		attackedSquare.hit();
		var result = new Result(attackedLocation);
		result.setShip(this);
		if (isSunk()) {
			result.setResult(AtackStatus.SUNK);
		} else {
			result.setResult(AtackStatus.HIT);
		}
		resList.add(result);
		return resList;
	}

	public boolean moveFleet(String direction){
		int counter = 0;
		if(direction == "right") {
			char[] array = new char[occupiedSquares.size()];
            for(Square i : occupiedSquares){
            	array[counter] = (char) (i.getColumn()+1);
            	if(array[counter] > 'J'){
            		return false;
				}
            	counter++;
			}
			counter = 0;
			for(Square i: occupiedSquares){
				i.setColumn(array[counter]);
				counter++;
			}
            return true;
	    }
	    else if(direction == "left"){
			char[] array = new char[occupiedSquares.size()];
			for(Square i : occupiedSquares){
				array[counter] = (char) (i.getColumn()-1);
				if(array[counter] < 'A'){
					return false;
				}
				counter++;
			}
			counter = 0;
			for(Square i: occupiedSquares){
				i.setColumn(array[counter]);
				counter++;
			}
			return true;
        }
        else if(direction == "up"){
			int[] array = new int[occupiedSquares.size()];
			for(Square i : occupiedSquares){
				array[counter] = i.getRow()-1;
				if(array[counter] < 1){
					return false;
				}
				counter++;
			}
			counter = 0;
			for(Square i: occupiedSquares){
				i.setRow(array[counter]);
				counter++;
			}
			return true;
        }
        else if(direction == "down"){
			int[] array = new int[occupiedSquares.size()];
			for(Square i : occupiedSquares){
				array[counter] = i.getRow()+1;
				if(array[counter] > 10){
					return false;
				}
				counter++;
			}
			counter = 0;
			for(Square i: occupiedSquares){
				i.setRow(array[counter]);
				counter++;
			}
			return true;
        }
        return false;
    }

	@JsonIgnore
	public boolean isSunk() {
		return getOccupiedSquares().stream().allMatch(s -> s.isHit());
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Ship)) {
			return false;
		}
		var otherShip = (Ship) other;

		return this.kind.equals(otherShip.kind)
				&& this.size == otherShip.size
				&& this.occupiedSquares.equals(otherShip.occupiedSquares);
	}

	@Override
	public int hashCode() {
		return 33 * kind.hashCode() + 23 * size + 17 * occupiedSquares.hashCode();
	}

	@Override
	public String toString() {
		return kind + occupiedSquares.toString();
	}

	public void moveShip(char dir){
		for(Square sq:occupiedSquares){
			sq.newLocation(dir);
		}
	}
}
