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

	//If this ship is a submarine, we can set it as "submerged"
	public void setSubmerged(int submerged) {
		this.submerged = submerged;
	}


	public CaptainsQuarters getCqSquare() {
		return cqSquare;
	}


	//Default constructor for the ship class
	public Ship() {
		occupiedSquares = new ArrayList<>();
	}

	//More in depth ship constructor
	public Ship(String kind) {
		this();
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

	//This place function adds the new squares to the occupied squares array
	public void place(char col, int row, boolean isVertical) {
		for (int i=0; i<size; i++) {
			if (isVertical) {
				occupiedSquares.add(new Square(row+i, col));
			} else {
				occupiedSquares.add(new Square(row, (char) (col + i)));
			}
		}
		}
	//This checks if a ship is overlapping with another and returns true if it is and false if it isn't
	public boolean overlaps(Ship other) {
		Set<Square> thisSquares = Set.copyOf(getOccupiedSquares());
		Set<Square> otherSquares = Set.copyOf(other.getOccupiedSquares());
		Sets.SetView<Square> intersection = Sets.intersection(thisSquares, otherSquares);
		return intersection.size() != 0;
	}

	//This is a helper function that sees if there is a ship at a given square
	public boolean isAtLocation(Square location) {
		return getOccupiedSquares().stream().anyMatch(s -> s.equals(location));
	}

	public String getKind() {
		return kind;
	}

	/*
	This attack function determines if the square was a successful hit or not. It first checks if the attack
	was on the ship square. If it was, check if it's a captain's quarters and act accordingly.
	 */
	public List<Result> attack(int x, char y) {
		//Determine if the attack was on a ship square
		List<Result> resList = new ArrayList<>();
		Square attackedLocation = null;
		Square attackedSquare = null;
		for(Square i : occupiedSquares){
			if(i.getRow() == x && i.getColumn() == y){
				//if it was, save the location
				attackedLocation = i;
				attackedSquare = i;
			}
		}
		Square square = attackedLocation;
		if (square == null) {
			//if there was no ship attacked, create a square to add to reslist and return
			attackedLocation = new Square(x, y);
			resList.add(new Result(attackedLocation));
			return resList;
		}
		if(attackedSquare.equals(getCqSquare())) {
			//if its a captains quarters that was hit, decrement the armor or register the hit
			//if a hit had already been registered, return a miss
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
			//if the attacked square is already hit, set the status as INVALID
			var result = new Result(attackedLocation);
			result.setResult(AtackStatus.INVALID);
			resList.add(result);
			return resList;
		}

		//If we got this far, we can assume it's a valid hit, check if it's sunk or just a regular hit
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

	//Take a direction and move the fleet one square that direction.
	public boolean moveFleet(String direction){
		int counter = 0;
		//This if else determines the direction of movement
		if(direction == "right") {
			char[] array = new char[occupiedSquares.size()];
            //This for loop makes sure the moves are within the bounds of the array
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
			//This for loop makes sure the moves are within the bounds of the array
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
			//This for loop makes sure the moves are within the bounds of the array
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
			//This for loop makes sure the moves are within the bounds of the array
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

    //This checks the sunk status of a given ship
	@JsonIgnore
	public boolean isSunk() {
		return getOccupiedSquares().stream().allMatch(s -> s.isHit());
	}

	//An equality function for ships
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

	//This helps move to and from a numerical or character representation of a column
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
