package test;
import org.junit.Before;
import org.junit.Test;

import main.Rectangle;

import static org.junit.Assert.*;

public class RectangleTest {
    private Rectangle rect1;
    private Rectangle rect2;

    @Before
    public void setUp() {
        rect1 = new Rectangle(0, 0, 10, 10); // Initialize with default values
        rect2 = new Rectangle(5, 5, 10, 10); // Another rectangle for intersection tests
    }

    @Test
    public void testGetters() {
        assertEquals(0, rect1.getX());
        assertEquals(0, rect1.getY());
        assertEquals(10, rect1.getWidth());
        assertEquals(10, rect1.getHeight());
    }

    @Test
    public void testSetters() {
        rect1.setX(15);
        rect1.setY(25);
        rect1.setWidth(30);
        rect1.setHeight(35);
        
        assertEquals(15, rect1.getX());
        assertEquals(25, rect1.getY());
        assertEquals(30, rect1.getWidth());
        assertEquals(35, rect1.getHeight());
    }

    @Test
    public void testIntersectsTrue() {
        // rect1 intersects with rect2
        assertTrue(rect1.intersects(rect2));
    }

    @Test
    public void testIntersectsFalse() {
        // Move rect2 far away, so it won't intersect with rect1
        rect2.setX(20);
        rect2.setY(20);
        assertFalse(rect1.intersects(rect2));
    }

    @Test
    public void testNoIntersectionByWidth() {
        // Adjust width so the rectangles don't intersect
        rect2.setX(11); // Just outside rect1's width range
        rect2.setY(0);
        assertFalse(rect1.intersects(rect2));
    }

    @Test
    public void testNoIntersectionByHeight() {
        // Adjust height so the rectangles don't intersect
        rect2.setX(0);
        rect2.setY(11); // Just outside rect1's height range
        assertFalse(rect1.intersects(rect2));
    }

    @Test
    public void testSamePositionIntersects() {
        // If two rectangles are at the same position with the same dimensions, they should intersect
        rect2.setX(0);
        rect2.setY(0);
        assertTrue(rect1.intersects(rect2));
    }
}
