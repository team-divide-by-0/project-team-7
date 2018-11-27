package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, property="@Square")
@SuppressWarnings("unused")
public class Square {

	@JsonProperty protected int row;
	@JsonProperty protected char column;
	@JsonProperty private boolean hit = false;
	protected int hitsTilSunk;
	public Square() {
		this.row = -1;
		this.column = 'z';
		//this.hitsTilSunk = -1;
	}

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
	}


	public int getHitsTilSunk() {
		return hitsTilSunk;
	}

	public void setHitsTilSunk(int hitsTilSunk) {
		this.hitsTilSunk = hitsTilSunk;
	}


	public void setColumn(char x){
		this.column = x;
	}

	public void setRow(int x){
		this.row = x;
	}

	public char getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}


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
