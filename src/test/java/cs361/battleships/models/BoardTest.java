package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;
import java.util.Random;

import java.util.List;

import static org.junit.Assert.*;

public class BoardTest {

    private Board board;
    private int X;
    private int BX;
    private char Y, BY;
    private Boolean bool;

    @Before
    public void setUp() {
        board = new Board();
        Random rand = new Random();
        X = rand.nextInt(6)+1;
        Y = (char) (rand.nextInt(6) + 66);
        bool = rand.nextBoolean();
        BX = rand.nextInt(20) - 5;
        while(BX < 10 && BX > 1) {
            BX = rand.nextInt(20) -5;
        }
        BY = (char) (rand.nextInt(20)-5);
        while(BY < 'J' && BY > 'A') {
            BY = (char) (rand.nextInt(20) - 5);
        }
    }
    // these test test valid ship placement and invalid ship placement.
    @Test
    public void testPlaceMinesweeper() {
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), X, Y, bool));
    }
    @Test
    public void testPlaceDestroyer() {
        assertTrue(board.placeShip(new Ship("DESTROYER"), X, Y, bool));
    }

    @Test
    public void testPlaceBattleShip() {
        assertTrue(board.placeShip(new Ship("BATTLESHIP"), X, Y, bool));
    }
    @Test
    public void testPlaceSubmarine() {
        assertTrue(board.placeShip(new Ship("SUBMARINE"), X, Y, bool));
    }
    @Test
    public void testPlaceBadMinsweeper() {
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), BX, BY, bool));
    }
    @Test
    public void testPlaceBadDestroyer() {
        assertFalse(board.placeShip(new Ship("DESTROYER"), BX, BY, bool));
    }
    @Test
    public void testPlaceBadBattleShip() {
        assertFalse(board.placeShip(new Ship("BATTLESHIP"), BX, BY, bool));
    }
    @Test
    public void testPlaceBadSubmarine() {
        assertFalse(board.placeShip(new Ship("SUBMARINE"), BX, BY, bool));
    }

    //This function test if you can place multiple of the same type of ship.
    @Test
    public void testSameShip(){
        assertTrue(board.placeShip(new Ship("MINESWEEPER"), X, Y, bool));
        assertFalse(board.placeShip(new Ship("MINESWEEPER"), X, Y, bool));
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
        board.placeShip(new Submarine(),2,'A',true);
        board.attack(5,'A');
        List<Result> results = board.attack(5,'A');
        assertEquals(AtackStatus.SUNK,results.get(0).getResult());
    }
    @Test
    public void testLaser(){
        board.placeShip(new Minesweeper(),1,'A',false);
        board.attack(1,'A'); //laser should now be activated
        board.placeShip(new Submarine(), 4,'D', false);
        board.placeShip(new Battleship(), 4, 'D', false);
        List<Result> results=board.attack(4,'D');
        assertEquals(AtackStatus.HIT, results.get(0).getResult());
        assertEquals(AtackStatus.HIT, results.get(0).getResult());
    }


    /*@Test
    public void testActivateSonar() {
        List<Result> test = board.activateSonar(5,'E');
        assertEquals(13,test.size());
    }*/

    @Test
    public void testMoveFleet(){
        board.placeShip(new Destroyer(), 8, 'I', false);
        board.placeShip(new Battleship(), 3, 'H', true);
        List<Ship> ships = board.getShips();

        board.moveFleet( 'd');
        for(Ship s: ships){
            if(s.getKind().equals("DESTROYER")){
                assertTrue(board.checkShipBoundsWithMove(s, 'd'));
                assertEquals(s.getOccupiedSquares().get(0).getRow(), 9);
            } else if(s.getKind().equals("BATTLESHIP")){
                assertTrue(board.checkShipBoundsWithMove(s, 'd'));
                assertEquals(s.getOccupiedSquares().get(0).getRow(), 4);
            }
        }
        board.moveFleet( 'u');
        for(Ship s: ships){
            if(s.getKind().equals("DESTROYER")){
                assertTrue(board.checkShipBoundsWithMove(s, 'u'));
                assertEquals(s.getOccupiedSquares().get(0).getRow(), 8);
            } else if(s.getKind().equals("BATTLESHIP")){
                assertTrue(board.checkShipBoundsWithMove(s, 'u'));
                assertEquals(s.getOccupiedSquares().get(0).getRow(), 3);
            }
        }
        board.moveFleet( 'l');
        for(Ship s: ships){
            if(s.getKind().equals("DESTROYER")){
                assertTrue(board.checkShipBoundsWithMove(s, 'l'));
                assertEquals(s.getOccupiedSquares().get(0).getColumn(), 'H');
            } else if(s.getKind().equals("BATTLESHIP")){
                assertTrue(board.checkShipBoundsWithMove(s, 'l'));
                assertEquals(s.getOccupiedSquares().get(0).getColumn(), 'G');
            }
        }
        board.moveFleet( 'r');
        for(Ship s: ships){
            if(s.getKind().equals("DESTROYER")){
                assertTrue(board.checkShipBoundsWithMove(s, 'r'));
                assertEquals(s.getOccupiedSquares().get(0).getColumn(), 'I');
            } else if(s.getKind().equals("BATTLESHIP")){
                assertTrue(board.checkShipBoundsWithMove(s, 'r'));
                assertEquals(s.getOccupiedSquares().get(0).getColumn(), 'H');
            }
        }
    }

    @Test
    public void testMoveFleetAtBounds(){
        board.placeShip(new Minesweeper(), 1, 'A', false);
        board.moveFleet('u');
        List<Ship> ships = board.getShips();
        for(Ship s:ships){
            if(s.getKind().equals("MINESWEEPER")){
                assertFalse(board.checkShipBoundsWithMove(s, 'u'));
                assertTrue(board.checkShipBoundsWithMove(s, 'd'));
                assertEquals(s.getOccupiedSquares().get(0).getRow(), 1);
            }
        }
    }

    @Test
    public void testActivateSonar(){
        board.placeShip(new Minesweeper(), 5, 'C', false);
        assertTrue(board.activateSonar(5, 'C'));

    }

}
