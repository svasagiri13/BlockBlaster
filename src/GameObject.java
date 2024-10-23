/**
 * Represents a game object with a position and dimensions in a 2D space.
 * This class provides methods for moving the object and retrieving its bounding rectangle.
 */
public class GameObject {
    protected int x;      // The x-coordinate of the game object's position
    protected int y;      // The y-coordinate of the game object's position
    protected int width;  // The width of the game object
    protected int height; // The height of the game object

    /**
     * Constructs a new GameObject with specified position and dimensions.
     * @param x the x-coordinate of the game object's position
     * @param y the y-coordinate of the game object's position
     * @param width the width of the game object
     * @param height the height of the game object
     */
    public GameObject(int x, int y, int width, int height) {
        this.x = x;      // Set the x-coordinate
        this.y = y;      // Set the y-coordinate
        this.width = width;  // Set the width
        this.height = height; // Set the height
    }

    /**
     * Moves the game object by a specified amount in the x and y directions.
     * @param dx the amount to move in the x direction
     * @param dy the amount to move in the y direction
     */
    public void move(int dx, int dy) {
        x += dx;  // Update the x-coordinate
        y += dy;  // Update the y-coordinate
    }

    /**
     * Returns the bounding rectangle of the game object.
     * @return a Rectangle representing the position and dimensions of the game object
     */
    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);  // Create and return a new Rectangle
    }

    // Getters

    /**
     * Gets the x-coordinate of the game object.
     * @return the x-coordinate
     */
    public int getX() {
        return x;  // Return the x-coordinate
    }

    /**
     * Gets the y-coordinate of the game object.
     * @return the y-coordinate
     */
    public int getY() {
        return y;  // Return the y-coordinate
    }

    /**
     * Gets the width of the game object.
     * @return the width of the game object
     */
    public int getWidth() {
        return width;  // Return the width
    }

    /**
     * Gets the height of the game object.
     * @return the height of the game object
     */
    public int getHeight() {
        return height;  // Return the height
    }
}
