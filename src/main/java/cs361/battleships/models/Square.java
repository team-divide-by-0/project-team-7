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
	//Constructors for the square type
	public Square() {
		this.row = -1;
		this.column = 'z';
	}

	public Square(int row, char column) {
		this.row = row;
		this.column = column;
	}

	//Getters and setters for the square members
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


	//An equals function that returns the equality of two squares
	@Override
	public boolean equals(Object other) {
		if (other instanceof Square) {
			return ((Square) other).row == this.row && ((Square) other).column == this.column;
		}
		return false;
	}

	//A converter function that helps move from numerical to character
	@Override
	public int hashCode() {
		return 31 * row + column;
	}

	//Quick checker for if a sqaure is out of bounds
	@JsonIgnore
	public boolean isOutOfBounds() {
		return row < 10 || row > 1 || column < 'J' || column > 'A';
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

	//moves the row and column of current square based on the direction
	//This is a helper function to the moveFleet function. It replaces the squares coordinates
	//based on the direction given.
	public void newLocation(char dir){
		Square movedSq = new Square(row, column);
		if(dir == 'd'){
			int newRow = row-1;
			movedSq.setRow(newRow);
		} else if(dir == 'u'){
			int newRow = row+1;
			movedSq.setRow(newRow);
		} else if(dir == 'l'){
			char newCol = (char)(column+1);
			movedSq.setColumn(newCol);
		} else if(dir == 'r'){
			char newCol = (char)(column-1);
			movedSq.setColumn(newCol);
		}
		if(!movedSq.isOutOfBounds()) {
			this.row = movedSq.row;
			this.column = movedSq.column;
		}
	}
}
