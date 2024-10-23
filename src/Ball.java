public class Ball extends GameObject {
    private int dx;
    private int dy;
    private int speed;
    private final int max_speed = 5;  // Maximum speed
    private final int min_speed = 2;  // Minimum speed
    private int RADIUS = 10;  // Ball's radius
    private final int max_radius = 13;  // Maximum radius
    protected boolean speedChange = false;  // Track if the speed was changed
    protected boolean radiusChange = false;  // Track if the radius was changed

    public Ball() {
        super(400, 300, 10, 10);  // Position and initial size (radius as width and height)
        this.speed = 2;  // Base speed
        this.dx = speed;
        this.dy = -speed;
    }

    public void move() {
        super.move(dx, dy);

        if (x < 0 || x > 800 - width) {
            dx = -dx;
        }

        if (y < 0) {
            dy = -dy;
        }
    }

    public void reverseY() {
        dy = -dy;
    }

    public void reset() {
        x = 400;
        y = 300;
        dx = speed;
        dy = -speed;
    }

    public void speedUp() {
        if (speed < max_speed) {
            dx += (dx > 0 ? 1 : -1);
            dy += (dy > 0 ? 1 : -1);
            speedChange = true;  // Set speedChange to true when the speed increases
        } else {
            speedChange = false;  // No change if the ball is already at max speed
        }
    }

    public void slowDown() {
        if (speed > min_speed) {
            dx -= (dx > 0 ? 1 : -1);
            dy -= (dy > 0 ? 1 : -1);
            speedChange = true;  // Set speedChange to true when the speed decreases
        } else {
            speedChange = false;  // No change if the ball is already at min speed
        }
    }

    public void increaseBallSize() {
        if (RADIUS < max_radius) {
            RADIUS++;
            width = RADIUS;
            height = RADIUS;
            radiusChange = true;  // Set radiusChange to true if the size increased
        } else {
            radiusChange = false;  // No change if the ball is already at max size
        }
    }

    public int getRadius() {
        return RADIUS;
    }

    public void resetSize() {
        RADIUS = 10;  // Reset to default size
        width = RADIUS;
        height = RADIUS;
        radiusChange = false;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, RADIUS, RADIUS);
    }

    public int getSpeed() {
        return Math.abs(dx);  // Return the ball's speed (either dx or dy)
    }
}
