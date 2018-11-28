package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testPlaceMinesweeper() {
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 1, 'A', true));
        assertFalse(board.placeShip(new Ship("DESTROYER"), 0, 'A', true));
        //this tests when the ship goes off the board horizontally
        assertFalse(board.placeShip(new Ship("BATTLESHIP"), 4, 'I', false));
        //this tests when the ship goes off the board vertically
        assertFalse(board.placeShip(new Ship("BATTLESHIP"), 9, 'B', true));
    }

    @Test
    public void testPlaceSubmarine() {
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("SUBMARINE"), 2, 'A', false));
    }

    @Test
    public void testValidPlacement(){
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 4, 'B', false));
        assertTrue(board.placeShip(new Ship("DESTROYER"), 5, 'G', true));
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 1, 'A', false));
    }

    @Test
    public void testSameShip(){
        Board board = new Board();
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 1, 'A', false));
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 1, 'A', false));
        assertTrue(board.placeShip(new Ship("DESTROYER"), 5, 'C', false));
        assertFalse(board.placeShip(new Ship("DESTROYER"), 3, 'C', true));
    }

    @Test
    public void testAttackEmptySquare() {
        board.placeShip(new Ship("MINESWEEPER"), 1, 'A', true);
        List<Result> results = board.attack(2, 'E');
        assertEquals(AtackStatus.MISS, results.get(0).getResult());
    }

    @Test
    public void testAttackShip() {
        Ship minesweeper = new Ship("MINESWEEPER");
        board.placeShip(minesweeper, 1, 'A', true);
        minesweeper = board.getShips().get(0);
        List<Result> results = board.attack(2, 'A');
        assertEquals(AtackStatus.HIT, results.get(0).getResult());
        assertEquals(minesweeper, results.get(0).getShip());
    }
    @Test
    public void testPlaceMultipleShipsOfSameType() {
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 1, 'A', true));
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), 5, 'D', true));
    }

    @Test
    public void testCantPlaceMoreThan4Ships() {
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), 1, 'A', true));
        assertTrue(board.placeShip(new Ship("SUBMARINE"), 10, 'F', false));
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), 5, 'D', true));
        assertTrue(board.placeShip(new Ship("DESTROYER"), 6, 'A', false));
        assertFalse(board.placeShip(new Ship(""), 8, 'A', false));

    }

    @Test
    public void testSurrender(){
        board.placeShip(new Minesweeper(), 1, 'A', true);
        board.placeShip(new Battleship(), 5, 'D', true);
        board.placeShip(new Destroyer(), 6, 'A', false);
        board.attack(1,'A');
        board.attack(7,'D');
        board.attack(7,'D');
        board.attack(6,'B');
        List<Result> results = board.attack(6,'B');
        assertEquals(AtackStatus.SURRENDER,results.get(0).getResult());

    }

    @Test
    public void testSubCaptainsQuarters(){
        board.placeShip(new Minesweeper(),10,'A',false);
        board.placeShip(new Submarine(),2,'A',false);
        board.attack(2,'D');
        List<Result> results = board.attack(2,'D');
        assertEquals(AtackStatus.SUNK,results.get(0).getResult());
    }

    /*@Test
    public void testActivateSonar() {
        List<Result> test = board.activateSonar(5,'E');
        assertEquals(13,test.size());
    }*/
}
