public class Brick extends GameObject {
    private int strength;  // Number of hits required to destroy the brick
    private boolean isSpecial;  // Whether the brick is a special one
    private PowerUpType powerUpType;

    public Brick(int x, int y, int width, int height, int strength, boolean isSpecial) {
        super(x, y, width, height);
        this.strength = strength;  // Set the brick's strength (number of hits to destroy)
        this.isSpecial = isSpecial;  // Whether this brick is a special brick

        if (isSpecial) {
            this.powerUpType = getRandomPowerUp();  // Assign a random power-up type if the brick is special
        }
    }

    // Method to randomly assign a power-up type
    private PowerUpType getRandomPowerUp() {
        PowerUpType[] powerUps = PowerUpType.values();
        int randomIndex = (int) (Math.random() * powerUps.length);
        return powerUps[randomIndex];
    }

    // Getter for power-up type (if the brick is special)
    public PowerUpType getPowerUpType() {
        return powerUpType;
    }

    // Method to check if the brick is special
    public boolean isSpecial() {
        return isSpecial;
    }

    // Method to reduce the strength of the brick when hit
    public void hit() {
        strength--;
    }

    // Method to check if the brick is destroyed
    public boolean isDestroyed() {
        return strength <= 0;
    }

    // Getter for the brick's strength
    public int getStrength() {
        return strength;
    }

    // Getters for position and size (inherited from GameObject)
}
