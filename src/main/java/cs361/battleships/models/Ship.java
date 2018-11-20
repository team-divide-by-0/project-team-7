package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.collect.Sets;
import com.mchange.v1.util.CollectionUtils;

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

	public CaptainsQuarters getCqSquare() {
		return cqSquare;
	}

	public void setCqSquare(CaptainsQuarters cqSquare) {
		this.cqSquare = cqSquare;
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

	public Result attack(int x, char y) {
		var attackedLocation = new Square(x, y);
		var square = getOccupiedSquares().stream().filter(s -> s.equals(attackedLocation)).findFirst();
		if (!square.isPresent()) {
			return new Result(attackedLocation);
		}
		var attackedSquare = square.get();
		if(attackedSquare.equals(getCqSquare())) {
			getCqSquare().decHitsTilSunk();
			if (getCqSquare().getHitsTilSunk() == 0) {
				var result = new Result(attackedLocation);
				result.setShip(this);
				for (Square i : occupiedSquares) {
					i.hit();
				}
				result.setResult(AtackStatus.SUNK);
				return result;
			} else {
				var result = new Result(attackedLocation);
				result.setShip(this);
				result.setResult(AtackStatus.PROTECTED);
				return result;
			}
		}
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
