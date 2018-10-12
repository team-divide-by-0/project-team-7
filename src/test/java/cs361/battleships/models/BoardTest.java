package cs361.battleships.models;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 0, 'A'), false);
    }
}
