/**
 * The Ball class represents a moving ball in a game.
 * It extends the GameObject class and contains properties
 * for speed, direction, and size. The ball can move, change
 * its speed, and modify its size within defined limits.
 */
public class Ball extends GameObject {
    private int dx;  // Change in the x-coordinate (horizontal movement)
    private int dy;  // Change in the y-coordinate (vertical movement)
    private int speed;  // Current speed of the ball
    private final int max_speed = 5;  // Maximum speed
    private final int min_speed = 2;  // Minimum speed
    private int RADIUS = 10;  // Ball's radius
    private final int max_radius = 13;  // Maximum radius
    protected boolean speedChange = false;  // Track if the speed was changed
    protected boolean radiusChange = false;  // Track if the radius was changed

    /**
     * Constructs a Ball object with an initial position
     * at (400, 300) and a base speed of 2.
     */
    public Ball() {
        super(400, 300, 10, 10);  // Position and initial size (radius as width and height)
        this.speed = 2;  // Base speed
        this.dx = speed;  // Initial horizontal movement
        this.dy = -speed;  // Initial vertical movement
    }

    /**
     * Moves the ball in the current direction, bouncing it off the walls
     * if it reaches the boundaries of the game area.
     */
    public void move() {
        super.move(dx, dy);

        if (x < 0 || x > 800 - width) {
            dx = -dx;  // Reverse direction on x-axis
        }

        if (y < 0) {
            dy = -dy;  // Reverse direction on y-axis
        }
    }

    /**
     * Reverses the ball's direction on the y-axis.
     */
    public void reverseY() {
        dy = -dy;  // Invert the y-direction
    }

    /**
     * Resets the ball's position to the center of the game area
     * and restores its initial speed.
     */
    public void reset() {
        x = 400;  // Reset x position
        y = 300;  // Reset y position
        dx = speed;  // Reset horizontal speed
        dy = -speed;  // Reset vertical speed
    }

    /**
     * Increases the ball's speed by adjusting its x and y velocities,
     * without exceeding the maximum speed.
     */
    public void speedUp() {
        if (speed < max_speed) {
            speed++;
            dx += (dx > 0 ? 1 : -1);  // Increase/decrease horizontal speed
            dy += (dy > 0 ? 1 : -1);  // Increase/decrease vertical speed
            speedChange = true;  // Set speedChange to true when the speed increases
        } else {
            speedChange = false;  // No change if the ball is already at max speed
        }
    }

    /**
     * Decreases the ball's speed by adjusting its x and y velocities,
     * without going below the minimum speed.
     */
    public void slowDown() {
        if (speed > min_speed) {
            speed--;
            dx -= (dx > 0 ? 1 : -1);  // Decrease horizontal speed
            dy -= (dy > 0 ? 1 : -1);  // Decrease vertical speed
            speedChange = true;  // Set speedChange to true when the speed decreases
        } else {
            speedChange = false;  // No change if the ball is already at min speed
        }
    }

    /**
     * Increases the size of the ball by increasing its radius,
     * without exceeding the maximum radius.
     */
    public void increaseBallSize() {
        if (RADIUS < max_radius) {
            RADIUS++;  // Increment radius
            width = RADIUS;  // Update width
            height = RADIUS;  // Update height
            radiusChange = true;  // Set radiusChange to true if the size increased
        } else {
            radiusChange = false;  // No change if the ball is already at max size
        }
    }

    /**
     * Gets the current radius of the ball.
     *
     * @return the radius of the ball
     */
    public int getRadius() {
        return RADIUS;  // Return current radius
    }

    /**
     * Resets the size of the ball to its default radius.
     */
    public void resetSize() {
        RADIUS = 10;  // Reset to default size
        width = RADIUS;  // Update width
        height = RADIUS;  // Update height
        radiusChange = false;  // Reset radius change flag
    }

    /**
     * Gets the bounding rectangle of the ball for collision detection.
     *
     * @return a Rectangle object representing the ball's position and size
     */
    public Rectangle getRect() {
        return new Rectangle(x, y, RADIUS, RADIUS);  // Return rectangle representation
    }

    /**
     * Gets the absolute speed of the ball.
     *
     * @return the speed of the ball
     */
    public int getSpeed() {
        return Math.abs(dx);  // Return the ball's speed (either dx or dy)
    }
}
