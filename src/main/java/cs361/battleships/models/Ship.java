package cs361.battleships.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Ship {

	@JsonProperty private List<Square> occupiedSquares;
	private String kind;
	
	public Ship(String kind) {
		this.kind = kind;
	}

	public List<Square> getOccupiedSquares() {
		return occupiedSquares;
	}
	
	public void setOccupiedSquares(int row, char col){
		occupiedSquares.add(new Square(row, col));
	}

	public void setKind(String kind) {
			this.kind = kind;
	}

	public String getKind() {
		return this.kind;
	}
    public void removeOccupiedSquares(int row, int column) {
        for (int i=0; i < occupiedSquares.size(); i++){
            if (occupiedSquares.get(i).getColumn()== column && occupiedSquares.get(i).getRow()==row){//finding the square with the given row and column
                occupiedSquares.remove(i); //removing found square
                break;
            }
        }

    }
}
