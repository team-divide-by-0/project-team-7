package cs361.battleships.models;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import static cs361.battleships.models.AtackStatus.*;

public class BoardTest {

    @Test
    public void testInvalidPlacement() {
        Board board = new Board();
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 11, 'C', true));
        assertFalse(board.placeShip(new Ship("DESTROYER"), 0, 'A', true));
        assertFalse(board.placeShip(new Ship("BATTLESHIP"), 4, 'I', false));
    }

    @Test
    public void testValidPlacement(){
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 4, 'B', false));
        assertTrue(board.placeShip(new Ship("DESTROYER"), 9, 'G', true));
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 0, 'A', false));
    }

    @Test
    public void testGuessed(){
        Board board = new Board();
        Square square = new Square(1,'A');
        board.attack(1,'A');
        List<Result> attacks = board.getAttacks();
        assertEquals(square.getColumn(),attacks.get(0).getLocation().getColumn());
        assertEquals(square.getRow(),attacks.get(0).getLocation().getRow());
    }

    @Test
    public void GuessAgain(){
        Board board = new Board();
        board.attack(1,'A');
        Result res = board.attack(1,'A');
        assertEquals(INVALID,res.getResult());
    }
    @Test
    public void GuessoffBoard(){
        Board board = new Board();
        Result res = board.attack(11,'A');
        assertEquals(INVALID,res.getResult());
        Result res2 = board.attack(1,'K');
        assertEquals(INVALID,res2.getResult());
        Result res3 = board.attack(11,'L');
        assertEquals(INVALID,res3.getResult());
    }

    @Test
    public void HorizontalWhenNotVertical(){ //test when ship is not vertical, it is placed horizontally
        Board board = new Board();
        Ship ship = new Ship("DESTROYER");
        board.placeShip(ship,9, 'G', false);
        List<Square> os = ship.getOccupiedSquares();
        for (int i=0; i<os.size();i++){
            assertEquals(os.get(i).getRow(),9);
        }
        Ship ship2 = new Ship("BATTLESHIP");
        board.placeShip(ship2,3, 'E', false);
        List<Square> os2 = ship2.getOccupiedSquares();
        for (int i=0; i<os2.size();i++){
            assertEquals(os2.get(i).getRow(),3);
        }
    }

    @Test
    public void Vertical(){
        Board board = new Board();
        Ship ship = new Ship("DESTROYER");
        board.placeShip(ship,9, 'G', true);
        List<Square> os = ship.getOccupiedSquares();
        for (int i=0; i<os.size();i++){
            assertEquals(os.get(i).getColumn(),'G');
        }
    }
}
