package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.mchange.v1.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Ship {

	@JsonProperty protected String kind;
	@JsonProperty protected List<Square> occupiedSquares;
	@JsonProperty protected int size;
	protected int hitsTilSunk;
	protected int cqRow;

	public void setKind(String kind) {
		this.kind = kind;
	}

	public void setOccupiedSquares(List<Square> occupiedSquares) {
		this.occupiedSquares = occupiedSquares;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getHitsTilSunk() {
		return hitsTilSunk;
	}

	public void setHitsTilSunk(int hitsTilSunk) {
		this.hitsTilSunk = hitsTilSunk;
	}

	public int getCqRow() {
		return cqRow;
	}

	public void setCqRow(int cqRow) {
		this.cqRow = cqRow;
	}

	public char getCqCol() {
		return cqCol;
	}

	public void setCqCol(char cqCol) {
		this.cqCol = cqCol;
	}

	protected char cqCol;
	//protected Square cqSquare;
	//protected Square sq = new Square(1, 'A');

	public Ship() {
		occupiedSquares = new ArrayList<>();
		//cqSquare = new Square();
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
		if(this.kind.equals("MINESWEEPER")) {
			cqRow = row;
			cqCol = col;
		}
		else if(this.kind.equals("DESTROYER")){
			if(isVertical) {
				cqRow = row + 1;
				cqCol = col;
			}
			else{
				cqRow = row;
				cqCol = (char) (col+1);
			}
		}
		else if(this.kind.equals("BATTLESHIP")) {
			if(isVertical) {
				cqRow = row + 2;
				cqCol = col;
			}
			else{
				cqRow = row;
				cqCol = (char) (col+2);
			}
		}
		System.out.println("CQ Square: " + cqCol + "Size: " + this.size);
		/*if(this.kind.equals("MINESWEEPER")){
			cqSquare.setRow(row);
			cqSquare.setColumn(col);
			cqSquare.setHitsTilSunk(captainHitNum);
			occupiedSquares.add(cqSquare);   //set the captains quarter square
			if (isVertical) {    //set the non-captains quarter square
				occupiedSquares.add(new Square(row+1, col));
			} else {
				occupiedSquares.add(new Square(row, (char) (col + 1)));
			}
		}
		else if(this.kind.equals("DESTROYER")){
			if (isVertical) {
				occupiedSquares.add(new Square(row, col));
				cqSquare.setRow(row+1);
				cqSquare.setColumn(col);
				cqSquare.setHitsTilSunk(captainHitNum);
				occupiedSquares.add(cqSquare);   //set the captain quarter square
				occupiedSquares.add(new Square(row+2, col));
			} else {
				occupiedSquares.add(new Square(row, (col)));
				cqSquare.setRow(row);
				cqSquare.setColumn((char) (col+1));
				cqSquare.setHitsTilSunk(captainHitNum);
				occupiedSquares.add(cqSquare);  //set the captain quarter square
				occupiedSquares.add(new Square(row, (char) (col + 2)));
			}
		}
		else if(this.kind.equals("BATTLESHIP")){
			if (isVertical) {
				occupiedSquares.add(new Square(row, col));
				occupiedSquares.add(new Square(row+1, col));
				cqSquare.setRow(row+2);
				cqSquare.setColumn(col);
				cqSquare.setHitsTilSunk(captainHitNum);
				occupiedSquares.add(cqSquare);   //set the captain quarter square
				occupiedSquares.add(new Square(row+3, col));
			} else {
				occupiedSquares.add(new Square(row, (col)));
				occupiedSquares.add(new Square(row, (char) (col + 1)));
				cqSquare.setRow(row);
				cqSquare.setColumn((char)(col+2));
				cqSquare.setHitsTilSunk(captainHitNum);
				occupiedSquares.add(cqSquare);  //set the captain quarter square
				occupiedSquares.add(new Square(row, (char) (col + 3)));
			}
		}*/
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

	//public Result attackCapsQuarters(){
		//call attack captains quarters function
		//call blow up ship
		//if blow up ship returns true
			//set every square in ship status to hit
			//set captains quarters result to sunk
			//return sunk
		//if blow up ship is not true
			//set captains quarters ships status to miss
			//return miss
	//}

	public Result attack(int x, char y) {
		//System.out.println("CQ Square: " + cqCol + "Size: " + this.size);
		//System.out.println(cqSquare.getColumn());
		//System.out.println("CQ Square: " + cqSquare.getColumn() + "Size: " + this.size);
		var attackedLocation = new Square(x, y);
		var square = getOccupiedSquares().stream().filter(s -> s.equals(attackedLocation)).findFirst();
		if (!square.isPresent()) {
			return new Result(attackedLocation);
		}
		var attackedSquare = square.get();
		if(attackedSquare.getColumn() == cqCol && attackedSquare.getRow() == cqRow){
			hitsTilSunk--;
			//System.out.println("HITS TIL SUNK: " + this.kind + " " + hitsTilSunk);
			if(hitsTilSunk <= 0){
				var result = new Result(attackedLocation);
				result.setShip(this);
				for(Square i: occupiedSquares){
					i.hit();
				}
				result.setResult(AtackStatus.SUNK);
				return result;
			}
			else{
				var result = new Result(attackedLocation);
				result.setShip(this);
				//result.setShip(this);
				result.setResult(AtackStatus.PROTECTED);
				//System.out.println(result);
				return result;
			}
		}
		//If Attacked Square is a Captain's Quarters call attack Caps Quarters
		//else
		//if (attackedSquare.getRow() == cqSquare.getRow() && attackedSquare.getColumn() == cqSquare.getColumn()) {
		//	System.out.println("I AM CAPTAINS QUARTERS");
		//}
		if (attackedSquare.isHit()) {
			var result = new Result(attackedLocation);
			result.setResult(AtackStatus.INVALID);
			return result;
		}
		attackedSquare.hit();
		var result = new Result(attackedLocation);
		result.setShip(this);
		if (isSunk()) {
			result.setResult(AtackStatus.SUNK);
		} else {
			result.setResult(AtackStatus.HIT);
		}
		return result;
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
}
