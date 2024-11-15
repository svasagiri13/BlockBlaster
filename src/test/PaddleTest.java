package test;
import org.junit.Before;
import org.junit.Test;


import main.Paddle;

import static org.junit.Assert.*;

public class PaddleTest {
    private Paddle paddle;

    @Before
    public void setUp() {
        paddle = new Paddle(); // Initialize the paddle before each test
    }

    @Test
    public void testInitialValues() {
        assertEquals(350, paddle.getX()); // Check initial x position
        assertEquals(580, paddle.getY()); // Check initial y position
        assertEquals(80, paddle.getWidth()); // Check initial width
        assertEquals(10, paddle.getHeight()); // Check height
        assertEquals(4, paddle.getSpeed()); // Check default speed
    }

   

    @Test
    public void testIncreaseSize() {
        paddle.increaseSize();
        assertEquals(120, paddle.getWidth()); // Width should increase by 40

        paddle.increaseSize();
        assertEquals(160, paddle.getWidth()); // Width should increase by 40 again

        paddle.increaseSize();
        assertEquals(200, paddle.getWidth()); // Width should be capped at 200

        paddle.increaseSize();
        assertEquals(200, paddle.getWidth()); // Width should remain capped at 200
    }

    @Test
    public void testResetSize() {
        paddle.increaseSize(); // Increase size first
        paddle.resetSize(); // Reset to normal width
        assertEquals(80, paddle.getWidth()); // Width should be reset to 80
    }

    @Test
    public void testIncreasePaddleSpeed() {
        paddle.increasePaddleSpeed();
        assertEquals(5, paddle.getSpeed()); // Speed should increase by 1

        paddle.increasePaddleSpeed();
        paddle.increasePaddleSpeed();
        paddle.increasePaddleSpeed();
        paddle.increasePaddleSpeed();
        paddle.increasePaddleSpeed(); // Speed should be capped

        assertEquals(10, paddle.getSpeed()); // Speed should be capped at 10

        // Test that the flag is set correctly
        assertTrue(paddle.isIncreasePaddleSpeed());
    }

    @Test
    public void testDecreasePaddleSpeed() {
        paddle.decreasePaddleSpeed();
        assertEquals(3, paddle.getSpeed()); // Speed should decrease by 1

        paddle.decreasePaddleSpeed();
        paddle.decreasePaddleSpeed();
        paddle.decreasePaddleSpeed(); // Speed should be capped at minimum

        assertEquals(1, paddle.getSpeed()); // Speed should be capped at 1
    }

    @Test
    public void testResetSpeed() {
        paddle.increasePaddleSpeed();
        paddle.resetSpeed();
        assertEquals(4, paddle.getSpeed()); // Speed should reset to 4
    }
}
