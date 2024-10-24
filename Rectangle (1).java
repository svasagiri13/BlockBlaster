/**
 * Represents a rectangle defined by its position (x, y) and dimensions (width, height).
 * This class provides methods to check for intersections with other rectangles.
 */
public class Rectangle {
    private int x;      // The x-coordinate of the rectangle's top-left corner
    private int y;      // The y-coordinate of the rectangle's top-left corner
    private int width;  // The width of the rectangle
    private int height; // The height of the rectangle

    /**
     * Constructs a new Rectangle object with specified position and dimensions.
     * @param x the x-coordinate of the rectangle's top-left corner
     * @param y the y-coordinate of the rectangle's top-left corner
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;      // Set the x-coordinate
        this.y = y;      // Set the y-coordinate
        this.width = width;  // Set the width
        this.height = height; // Set the height
    }

    /**
     * Checks if this rectangle intersects with another rectangle.
     * @param other the rectangle to check for intersection with
     * @return true if the rectangles intersect, false otherwise
     */
    public boolean intersects(Rectangle other) {
        return this.x < other.x + other.width && this.x + this.width > other.x &&
               this.y < other.y + other.height && this.y + this.height > other.y;
    }

    /**
     * Gets the x-coordinate of the rectangle's top-left corner.
     * @return the x-coordinate
     */
    public int getX() {
        return x;  // Return the x-coordinate
    }

    /**
     * Gets the y-coordinate of the rectangle's top-left corner.
     * @return the y-coordinate
     */
    public int getY() {
        return y;  // Return the y-coordinate
    }

    /**
     * Gets the width of the rectangle.
     * @return the width of the rectangle
     */
    public int getWidth() {
        return width;  // Return the width
    }

    /**
     * Gets the height of the rectangle.
     * @return the height of the rectangle
     */
    public int getHeight() {
        return height;  // Return the height
    }

    /**
     * Sets the x-coordinate of the rectangle's top-left corner.
     * @param x the new x-coordinate
     */
    public void setX(int x) {
        this.x = x;  // Update the x-coordinate
    }

    /**
     * Sets the y-coordinate of the rectangle's top-left corner.
     * @param y the new y-coordinate
     */
    public void setY(int y) {
        this.y = y;  // Update the y-coordinate
    }

    /**
     * Sets the width of the rectangle.
     * @param width the new width
     */
    public void setWidth(int width) {
        this.width = width;  // Update the width
    }

    /**
     * Sets the height of the rectangle.
     * @param height the new height
     */
    public void setHeight(int height) {
        this.height = height;  // Update the height
    }
}
