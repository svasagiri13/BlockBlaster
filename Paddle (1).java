import java.awt.event.KeyEvent;

/**
 * Represents the paddle in the game, allowing the player to control its movement.
 * The paddle can increase or decrease in size and speed, and its movement is 
 * controlled by keyboard input.
 */
public class Paddle extends GameObject {
    private int dx;  // Change in the x-coordinate for paddle movement
    private int speed;  // Current speed of the paddle
    private static final int NORMAL_SPEED = 4;  // Default speed of the paddle
    private static final int MAX_SPEED = 10;  // Maximum speed limit
    private static final int MIN_SPEED = 1;  // Minimum speed limit
    private static final int MAX_WIDTH = 200;  // Maximum width of the paddle
    private static final int NORMAL_WIDTH = 80;  // Default width of the paddle
    protected boolean increasePaddleSpeed = false;  // Flag for increasing speed
    protected boolean decreasePaddleSpeed = false;  // Flag for decreasing speed

    /**
     * Initializes a new Paddle object with default position and size.
     */
    public Paddle() {
        super(350, 580, NORMAL_WIDTH, 10);  // Position and dimensions
        this.speed = NORMAL_SPEED;
    }

    /**
     * Moves the paddle based on its current speed and direction.
     * Ensures the paddle does not move out of the game bounds.
     */
    public void move() {
        super.move(dx, 0);

        if (x < 0) {
            x = 0;  // Prevent paddle from moving off the left edge
        }

        if (x + width > 800) {
            x = 800 - width;  // Prevent paddle from moving off the right edge
        }
    }

    /**
     * Handles key press events for paddle movement.
     * @param e the KeyEvent that triggered this method
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -speed;  // Move left
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = speed;  // Move right
        }
    }

    /**
     * Handles key release events to stop paddle movement.
     * @param e the KeyEvent that triggered this method
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            dx = 0;  // Stop paddle movement
        }
    }

    /**
     * Increases the size of the paddle up to the maximum width.
     */
    public void increaseSize() {
        if (width < MAX_WIDTH) {
            width += 40;  // Increase width by 40 units
        }
    }

    /**
     * Resets the paddle size to its normal width.
     */
    public void resetSize() {
        width = NORMAL_WIDTH;  // Reset to default size
    }

    /**
     * Increases the speed of the paddle up to the maximum speed.
     */
    public void increasePaddleSpeed() {
        if (speed < MAX_SPEED) {
            speed++;  // Increase speed
            increasePaddleSpeed = true;  // Flag that speed has increased
        } else {
            increasePaddleSpeed = false;  // No change if at max speed
        }
    }

    /**
     * Decreases the speed of the paddle down to the minimum speed.
     */
    public void decreasePaddleSpeed() {
        if (speed > MIN_SPEED) {
            speed--;  // Decrease speed
            decreasePaddleSpeed = true;  // Flag that speed has decreased
        } else {
            decreasePaddleSpeed = false;  // No change if at min speed
        }
    }

    /**
     * Resets the paddle speed to its normal speed.
     */
    public void resetSpeed() {
        speed = NORMAL_SPEED;  // Reset to default speed
    }

    /**
     * Gets the current speed of the paddle.
     * @return the current speed of the paddle
     */
    public int getSpeed() {
        return speed;  // Return the current speed
    }
}
