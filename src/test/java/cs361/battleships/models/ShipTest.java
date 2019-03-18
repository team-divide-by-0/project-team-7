package cs361.battleships.models;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShipTest {

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

    /*@Test
    public void testHitsTilSunk(){
        var minesweeper = new Minesweeper();
        minesweeper.place('A',1,true);
        minesweeper.attack(1,'A');
        assertEquals(0,minesweeper.getHitsTilSunk());

        var battle = new Battleship();
        battle.place('B',5,false);
        battle.attack(5,'D');
        assertEquals(1,battle.getHitsTilSunk());
        battle.attack(5,'D');
        assertEquals(0,battle.getHitsTilSunk());
    }*/

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
    public void testCaptainsQuartersStatus(){
        Ship battle = new Battleship();
        battle.place('A',1, false);
        battle.attack(1, 'C');
        List<Result> result = battle.attack(1, 'C');
        assertEquals(AtackStatus.SUNK, result.get(0).getResult());

        Ship minesweeper = new Minesweeper();
        minesweeper.place('A',3,false);
        result = minesweeper.attack(3,'A');
        assertEquals(AtackStatus.SUNK, result.get(0).getResult());
    }

    @Test
    public void testMoveFleet(){
        Ship s = new Minesweeper();
        s.place('A', 1, false);
        s.moveFleet("right");
        boolean correct = true;
        if(s.getOccupiedSquares().get(0).getColumn() != 'B'){
            correct = false;
        }
        if(s.getOccupiedSquares().get(0).getRow() != 1){
            correct = false;
        }
        if(s.getOccupiedSquares().get(1).getColumn() != 'C'){
            correct = false;
        }
        if(s.getOccupiedSquares().get(1).getRow() != 1){
            correct = false;
        }
        assertTrue(correct);
    }
}
