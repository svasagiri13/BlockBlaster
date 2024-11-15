package test;
import org.junit.Before;
import org.junit.Test;

import main.Ball;
import main.Rectangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BallTest {
    private Ball ball;

    @Before
    public void setUp() {
        ball = new Ball(); // Initialize the ball before each test
    }

    @Test
    public void testMove() {
        System.out.println("Testing move");
        ball.move();  // Move the ball
        System.out.println("Ball X position after move: " + ball.getX());
        System.out.println("Ball Y position after move: " + ball.getY());
        assertEquals(402, ball.getX());  // Check X position after moving
        assertEquals(298, ball.getY());  // Check Y position after moving
    }
    

    @Test
    public void testReverseY() {
        ball.reverseY(); // Reverse the Y direction
        ball.move(); // Move after reversing
        assertEquals(302, ball.getY()); // Y position should increase
    }

    @Test
    public void testReset() {
        ball.reset(); // Reset the ball
        assertEquals(400, ball.getX()); // Check if X position is reset
        assertEquals(300, ball.getY()); // Check if Y position is reset
    }

    @Test
    public void testSpeedUp() {
        ball.speedUp(); // Speed up the ball
        assertEquals(3, ball.getSpeed()); // Speed should increase
    }

    @Test
    public void testMaxSpeed() {
        for (int i = 0; i < 3; i++) {
            ball.speedUp(); // Increase speed up to max
        }
        assertEquals(5, ball.getSpeed()); // Should reach max speed
    
        ball.speedUp(); // Attempt to increase beyond max
    }

    @Test
public void testSpeedChangeFlag() {
    ball.speedUp(); // Speed is 3
    assertTrue(ball.isSpeedChanged()); // Speed should have changed

    ball.slowDown(); // Speed should be 2
    assertTrue(ball.isSpeedChanged()); // Speed should have changed again

    ball.slowDown(); // No change
    assertFalse(ball.isSpeedChanged()); // Speed change should not be true anymore
}

@Test
public void testMinSpeed() {
    ball.slowDown(); // Decrease from initial speed (2)
    assertEquals(2, ball.getSpeed()); // Speed should remain at minimum
}


    @Test
    public void testIncreaseBallSize() {
        ball.increaseBallSize(); // Increase the ball size
        assertEquals(11, ball.getRadius()); // Radius should increase by 1
    }

    @Test
    public void testResetSize() {
        ball.increaseBallSize(); // Increase size first
        ball.resetSize(); // Then reset size
        assertEquals(10, ball.getRadius()); // Radius should reset to default
    }

    @Test
    public void testGetRect() {
        Rectangle rect = ball.getRect(); // Get the rectangle for collision detection
        assertEquals(400, rect.getX()); // Check X position
        assertEquals(300, rect.getY()); // Check Y position
        assertEquals(10, rect.getWidth()); // Check width
        assertEquals(10, rect.getHeight()); // Check height
    }

    @Test
    public void testGetSpeed() {
        assertEquals(2, ball.getSpeed()); // Initial speed should be 2
    }
}
