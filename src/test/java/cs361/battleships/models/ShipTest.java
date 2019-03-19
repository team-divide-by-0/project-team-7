package cs361.battleships.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShipTest {

    @Test
    public void testPlace() {
        Ship minesweeper = new Ship("MINESWEEPER");
        minesweeper.place('C', 1, false);
        Ship destroyer = new Ship ("DESTROYER");
        destroyer.place('D', 2, false);
        Ship battleship = new Ship("BATTLESHIP");
        battleship.place('E', 3, false);
        List<Square> moccupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> mexpected = new ArrayList<>();
        mexpected.add(new Square(1, 'C'));
        mexpected.add(new Square(1, 'D'));
        assertEquals(mexpected, moccupiedSquares);
        List<Square> doccupiedSquares = destroyer.getOccupiedSquares();
        ArrayList<Object> dexpected = new ArrayList<>();
        dexpected.add(new Square(2, 'D'));
        dexpected.add(new Square(2, 'E'));
        dexpected.add(new Square(2, 'F'));
        assertEquals(dexpected, doccupiedSquares);
        List<Square> boccupiedSquares = battleship.getOccupiedSquares();
        ArrayList<Object> bexpected = new ArrayList<>();
        bexpected.add(new Square(3, 'E'));
        bexpected.add(new Square(3, 'F'));
        bexpected.add(new Square(3, 'G'));
        bexpected.add(new Square(3, 'H'));
        assertEquals(bexpected, boccupiedSquares);
    }

    @Test
    public void testPlaceMinesweeperHorizontally() {
        Ship minesweeper = new Minesweeper();
        minesweeper.place('C', 1, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'C'));
        expected.add(new Square(1, 'D'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceMinesweeperVertically() {
        Ship minesweeper = new Minesweeper();
        minesweeper.place('A', 1, true);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(2, 'A'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceDestroyerHorizontally() {
        Ship minesweeper = new Destroyer();
        minesweeper.place('A', 1, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(1, 'B'));
        expected.add(new Square(1, 'C'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceDestroyerVertically() {
        Ship minesweeper = new Destroyer();
        minesweeper.place('A', 1, true);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(2, 'A'));
        expected.add(new Square(3, 'A'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceBattleshipHorizontally() {
        Ship minesweeper = new Battleship();
        minesweeper.place('A', 1, false);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(1, 'B'));
        expected.add(new Square(1, 'C'));
        expected.add(new Square(1, 'D'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testPlaceBattleshipVertically() {
        Ship minesweeper = new Battleship();
        minesweeper.place('A', 1, true);
        List<Square> occupiedSquares = minesweeper.getOccupiedSquares();
        ArrayList<Object> expected = new ArrayList<>();
        expected.add(new Square(1, 'A'));
        expected.add(new Square(2, 'A'));
        expected.add(new Square(3, 'A'));
        expected.add(new Square(4, 'A'));
        assertEquals(expected, occupiedSquares);
    }

    @Test
    public void testShipOverlaps() {
        Ship minesweeper1 = new Minesweeper();
        minesweeper1.place('A', 1, true);

        Ship minesweeper2 = new Minesweeper();
        minesweeper2.place('A', 1, true);

        assertTrue(minesweeper1.overlaps(minesweeper2));
    }

    @Test
    public void testShipsDontOverlap() {
        Ship minesweeper1 = new Minesweeper();
        minesweeper1.place('A', 1, true);

        Ship minesweeper2 = new Minesweeper();
        minesweeper2.place('C', 2, true);

        assertFalse(minesweeper1.overlaps(minesweeper2));
    }

    @Test
    public void testIsAtLocation() {
        Ship minesweeper = new Battleship();
        minesweeper.place('A', 1, true);

        assertTrue(minesweeper.isAtLocation(new Square(1, 'A')));
        assertTrue(minesweeper.isAtLocation(new Square(2, 'A')));
    }

    @Test
    public void testHit() {
        Ship minesweeper = new Battleship();
        minesweeper.place('A', 1, true);

        List<Result> result = minesweeper.attack(1, 'A');
        assertEquals(AtackStatus.HIT, result.get(0).getResult());
        assertEquals(minesweeper, result.get(0).getShip());
        assertEquals(new Square(1, 'A'), result.get(0).getLocation());
    }


    @Test
    public void testSink() {
        Ship minesweeper = new Minesweeper();
        minesweeper.place('A', 1, true);

        minesweeper.attack(2, 'A');
        List<Result> result = minesweeper.attack(1, 'A');

        assertEquals(AtackStatus.SUNK, result.get(0).getResult());
        assertEquals(minesweeper, result.get(0).getShip());
        assertEquals(new Square(1, 'A'), result.get(0).getLocation());
    }

    @Test
    public void testOverlapsBug() {
        Ship minesweeper = new Minesweeper();
        Ship destroyer = new Destroyer();
        minesweeper.place('C', 5, false);
        destroyer.place('C', 5, false);
        assertTrue(minesweeper.overlaps(destroyer));
    }

    @Test
    public void testAttackSameSquareTwice() {
        Ship minesweeper = new Minesweeper();
        minesweeper.place('A', 1, true);
        var result = minesweeper.attack(2, 'A');
        assertEquals(AtackStatus.HIT, result.get(0).getResult());
        result = minesweeper.attack(2, 'A');
        assertEquals(AtackStatus.INVALID, result.get(0).getResult());
    }

    @Test
    public void testEquals() {
        Ship minesweeper1 = new Minesweeper();
        minesweeper1.place('A', 1, true);
        Ship minesweeper2 = new Minesweeper();
        minesweeper2.place('A', 1, true);
        assertTrue(minesweeper1.equals(minesweeper2));
        assertEquals(minesweeper1.hashCode(), minesweeper2.hashCode());
    }

    @Test
    public void testCaptainsQuartersStatus() {
        Ship battle = new Battleship();
        battle.place('A', 1, false);
        battle.attack(1, 'C');
        List<Result> result = battle.attack(1, 'C');
        assertEquals(AtackStatus.SUNK, result.get(0).getResult());

        Ship minesweeper = new Minesweeper();
        minesweeper.place('A', 3, false);
        result = minesweeper.attack(3, 'A');
        assertEquals(AtackStatus.SUNK, result.get(0).getResult());
    }

    @Test
    public void testMoveFleetRight() {
        Ship s = new Minesweeper();
        s.place('A', 1, false);
        s.moveFleet("right");
        boolean correct = true;
        if (s.getOccupiedSquares().get(0).getColumn() != 'B') {
            correct = false;
        }
        if (s.getOccupiedSquares().get(0).getRow() != 1) {
            correct = false;
        }
        if (s.getOccupiedSquares().get(1).getColumn() != 'C') {
            correct = false;
        }
        if (s.getOccupiedSquares().get(1).getRow() != 1) {
            correct = false;
        }
        assertTrue(correct);
    }

    @Test
    public void testMoveFleetLeft(){
        Ship s = new Destroyer();
        s.place('G', 3, false);
        s.moveFleet("left");
        boolean correct = true;
        if(s.getOccupiedSquares().get(0).getColumn() != 'F'){
            correct = false;
        }
        if(s.getOccupiedSquares().get(0).getRow() != 3){
            correct = false;
        }
        if(s.getOccupiedSquares().get(1).getColumn() != 'G'){
            correct = false;
        }
        if(s.getOccupiedSquares().get(1).getRow() != 3){
            correct = false;
        }
        if(s.getOccupiedSquares().get(2).getColumn() != 'H'){
            correct = false;
        }
        if(s.getOccupiedSquares().get(2).getRow() != 3) {
            correct = false;
        }
        assertTrue(correct);
    }

    @Test
    public void testMoveFleetDown(){
        Ship s = new Battleship();
        s.place('A', 3, false);
        s.moveFleet("down");
        boolean correct = true;
        if(s.getOccupiedSquares().get(0).getColumn() != 'A'){
            correct = false;
        }
        if(s.getOccupiedSquares().get(0).getRow() != 4){
            correct = false;
        }
        if(s.getOccupiedSquares().get(1).getColumn() != 'B'){
            correct = false;
        }
        if(s.getOccupiedSquares().get(1).getRow() != 4){
            correct = false;
        }
        if(s.getOccupiedSquares().get(2).getColumn() != 'C'){
            correct = false;
        }
        if(s.getOccupiedSquares().get(2).getRow() != 4) {
            correct = false;
        }
        if(s.getOccupiedSquares().get(3).getColumn() != 'D'){
            correct = false;
        }
        if(s.getOccupiedSquares().get(3).getRow() != 4) {
            correct = false;
        }
        assertTrue(correct);
    }

    @Test
    public void testMoveFleetUp(){
        Ship s = new Submarine();
        s.place('A', 3, false);
        s.moveFleet("up");
        boolean correct = true;
        if(s.getOccupiedSquares().get(0).getColumn() != 'A'){
            correct = false;
        }
        if(s.getOccupiedSquares().get(0).getRow() != 2){
            correct = false;
        }
        if(s.getOccupiedSquares().get(1).getColumn() != 'B'){
            correct = false;
        }
        if(s.getOccupiedSquares().get(1).getRow() != 2){
            correct = false;
        }
        if(s.getOccupiedSquares().get(2).getColumn() != 'C'){
            correct = false;
        }
        if(s.getOccupiedSquares().get(2).getRow() != 2) {
            correct = false;
        }
        if(s.getOccupiedSquares().get(4).getColumn() != 'D'){
            correct = false;
        }
        if(s.getOccupiedSquares().get(4).getRow() != 2) {
            correct = false;
        }
        if(s.getOccupiedSquares().get(3).getColumn() != 'C'){
            correct = false;
        }
        if(s.getOccupiedSquares().get(3).getRow() != 1) {
            correct = false;
        }
        assertTrue(correct);
    }
}
