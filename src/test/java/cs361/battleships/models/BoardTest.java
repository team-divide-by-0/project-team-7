package cs361.battleships.models;

import org.junit.Test;
import static cs361.battleships.models.AtackStatus.*;

import static org.junit.Assert.assertEquals;
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

    @Test
    public void testHit(){
        Board board = new Board();
        Ship ship = new Ship("DESTROYER");
        board.placeShip(ship, 1, 'A', false);
        Result res = board.attack(1, 'A');
        assertEquals(HIT, res.getResult());
    }

    @Test
    public void testMiss(){
        Board board = new Board();
        Ship ship = new Ship("DESTROYER");
        board.placeShip(ship, 1, 'A', false);
        Result res = board.attack(1, 'D');
        assertEquals(MISS, res.getResult());
    }

    @Test
    public void testSunk(){
        Board board = new Board();
        Ship ship = new Ship("MINESWEEPER");
        Ship ship2 = new Ship("MINESWEEPER");
        board.placeShip(ship, 1, 'A', false);
        board.placeShip(ship2, 2, 'A', false);
        board.attack(1, 'A');
        Result res = board.attack(1, 'B');
        assertEquals(SUNK, res.getResult());
    }

    @Test
    public void testSurrender(){
        Board board = new Board();
        Ship ship = new Ship("MINESWEEPER");
        board.placeShip(ship, 1, 'A', false);
        board.attack(1, 'A');
        Result res = board.attack(1, 'B');
        assertEquals(SURRENDER, res.getResult());
    }
}
