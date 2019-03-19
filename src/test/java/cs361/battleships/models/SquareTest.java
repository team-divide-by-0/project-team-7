package cs361.battleships.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SquareTest {

    private Square square1;
    private Square square2;
    private Square square3;
    private Square square4;
    private Square square5;
    private Square square6;
    private Square square7;
    private Square square8;

    @Before
    public void declareSquares() {
        square1 = new Square(11, 'A');
        square2 = new Square(1, 'Z');
        square3 = new Square(1, 'a');
        square4 = new Square(0, 'A');
        square5 = new Square(1, 'I');
        square6 = new Square(1, 'A');
        square7 = new Square(5, 'G');
        square8 = new Square(9, 'A');
    }

    @Test
    public void testIsOutOfBoundTest() {
        assertTrue(square1.isOutOfBounds());
        assertTrue(square2.isOutOfBounds());
        assertTrue(square3.isOutOfBounds());
        assertTrue(square4.isOutOfBounds());
        assertFalse(square5.isOutOfBounds());
    }

    @Test
    public void testIsHit() {
        assertFalse(square6.isHit());
        square6.hit();
        assertTrue(square6.isHit());
    }

    @Test
    public void testEquals() {
        Square square = new Square(1, 'A');

        assertTrue(square6.equals(square));
        assertEquals(square6.hashCode(), square.hashCode());
    }

    @Test
    public void testNotEquals() {
        Square square = new Square(2, 'A');

        assertFalse(square6.equals(square));
        assertNotEquals(square6.hashCode(), square.hashCode());
    }

    @Test
    public void testNewLocation(){

        square7.newLocation('l');
        assertEquals(square7.column, 'F');
        square8.newLocation('l');
        assertEquals(square8.column, 'A');
    }

    //Justin's Tests
}
