
import java.awt.event.KeyEvent;

public class Paddle extends GameObject {
    private int dx;
    private int speed;
    private static final int NORMAL_SPEED = 4;
    private static final int MAX_SPEED = 10;
    private static final int MIN_SPEED = 1;  // Add a minimum speed limit
    private static final int MAX_WIDTH = 200;
    private static final int NORMAL_WIDTH = 80;
    protected boolean increasePaddleSpeed = false;  // Flag for increasing speed
    protected boolean decreasePaddleSpeed = false;  // Flag for decreasing speed

    public Paddle() {
        super(350, 580, NORMAL_WIDTH, 10);  // Position and dimensions
        this.speed = NORMAL_SPEED;
    }

    public void move() {
        super.move(dx, 0);

        if (x < 0) {
            x = 0;
        }

        if (x + width > 800) {
            x = 800 - width;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -speed;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = speed;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }
    }

    public void increaseSize() {
        if (width < MAX_WIDTH) {
            width += 40;
        }
    }

    public void resetSize() {
        width = NORMAL_WIDTH;
    }

    public void increasePaddleSpeed() {
        if (speed < MAX_SPEED) {
            speed++;
            increasePaddleSpeed = true;
        } else {
            increasePaddleSpeed = false;
        }
    }

    // Add the missing decreasePaddleSpeed method
    public void decreasePaddleSpeed() {
        if (speed > MIN_SPEED) {
            speed--;
            decreasePaddleSpeed = true;
        } else {
            decreasePaddleSpeed = false;
        }
    }

    public void resetSpeed() {
        speed = NORMAL_SPEED;
    }

    public int getSpeed() {
        return speed;
    }
}
