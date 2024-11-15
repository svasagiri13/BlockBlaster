package main;
/**
 * Represents a brick in the game that can be hit and destroyed.
 * Each brick has a strength indicating how many hits it can take 
 * before being destroyed. Special bricks may provide power-ups when destroyed.
 */
public class Brick extends GameObject {
    private int strength;  // Number of hits required to destroy the brick
    private boolean isSpecial;  // Whether the brick is a special one
    private PowerUpType powerUpType;  // The type of power-up this brick provides, if special

    /**
     * Constructs a new Brick object with specified position, size, strength, and type.
     * @param x the x-coordinate of the brick
     * @param y the y-coordinate of the brick
     * @param width the width of the brick
     * @param height the height of the brick
     * @param strength the initial strength of the brick (hits required to destroy)
     * @param isSpecial indicates if this brick is a special brick that provides a power-up
     */
    public Brick(int x, int y, int width, int height, int strength, boolean isSpecial) {
        super(x, y, width, height);  // Initialize the brick's position and size
        this.strength = strength;  // Set the brick's strength (number of hits to destroy)
        this.isSpecial = isSpecial;  // Whether this brick is a special brick

        if (isSpecial) {
            this.powerUpType = getRandomPowerUp();  // Assign a random power-up type if the brick is special
        }
    }

    /**
     * Randomly assigns a power-up type to the brick if it is special.
     * @return a random PowerUpType from the available types
     */
    private PowerUpType getRandomPowerUp() {
        PowerUpType[] powerUps = PowerUpType.values();  // Get all power-up types
        int randomIndex = (int) (Math.random() * powerUps.length);  // Generate a random index
        return powerUps[randomIndex];  // Return the random power-up type
    }

    /**
     * Gets the power-up type associated with this brick.
     * @return the PowerUpType if the brick is special, otherwise null
     */
    public PowerUpType getPowerUpType() {
        return powerUpType;  // Return the power-up type
    }

    /**
     * Checks if the brick is special.
     * @return true if the brick is special, false otherwise
     */
    public boolean isSpecial() {
        return isSpecial;  // Return whether the brick is special
    }

    /**
     * Reduces the strength of the brick by one when it is hit.
     */
    public void hit() {
        strength--;  // Decrease the strength of the brick
    }

    /**
     * Checks if the brick is destroyed.
     * @return true if the brick's strength is zero or less, false otherwise
     */
    public boolean isDestroyed() {
        return strength <= 0;  // Return whether the brick is destroyed
    }

    /**
     * Gets the current strength of the brick.
     * @return the current strength of the brick
     */
    public int getStrength() {
        return strength;  // Return the strength of the brick
    }

    // Getters for position and size (inherited from GameObject)
}
