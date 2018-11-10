package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings("unused")
public class Square {

	@JsonProperty protected int row;
	@JsonProperty protected char column;
	@JsonProperty private boolean hit = false;
	protected int hitsTilSunk;

	public Square() {
	}

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
		this.hitsTilSunk = -1;
	}

	public void setColumn(char x){
		this.column = x;
	}

	public void setRow(int x){
		this.row = x;
	}

	public void setHitsTilSunk(int x){
		this.hitsTilSunk = x;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public int getHitsTilSunk(){ return hitsTilSunk; }


	@Override
	public boolean equals(Object other) {
		if (other instanceof Square) {
			return ((Square) other).row == this.row && ((Square) other).column == this.column;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return 31 * row + column;
	}

	@JsonIgnore
	public boolean isOutOfBounds() {
		return row > 10 || row < 1 || column > 'J' || column < 'A';
	}

	public boolean isHit() {
		return hit;
	}

	public void hit(){
		hit = true;
	}

	@Override
	public String toString() {
		return "(" + row + ", " + column + ')';
	}
}
