import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class BrickTest {
    private Brick normalBrick;
    private Brick specialBrick;

    @Before
    public void setUp() {
        // Initialize a normal brick with strength of 3
        normalBrick = new Brick(100, 200, 50, 20, 3, false);

        // Initialize a special brick with strength of 5
        specialBrick = new Brick(200, 300, 50, 20, 5, true);
    }

    @Test
    public void testInitialValuesNormalBrick() {
        assertEquals(100, normalBrick.getX()); // Check x position
        assertEquals(200, normalBrick.getY()); // Check y position
        assertEquals(50, normalBrick.getWidth()); // Check width
        assertEquals(20, normalBrick.getHeight()); // Check height
        assertEquals(3, normalBrick.getStrength()); // Check strength
        assertFalse(normalBrick.isSpecial()); // Check if it's not special
    }

    @Test
    public void testInitialValuesSpecialBrick() {
        assertEquals(200, specialBrick.getX()); // Check x position
        assertEquals(300, specialBrick.getY()); // Check y position
        assertEquals(50, specialBrick.getWidth()); // Check width
        assertEquals(20, specialBrick.getHeight()); // Check height
        assertEquals(5, specialBrick.getStrength()); // Check strength
        assertTrue(specialBrick.isSpecial()); // Check if it's special
        assertNotNull(specialBrick.getPowerUpType()); // Check if it has a power-up type
    }

    @Test
    public void testHitNormalBrick() {
        normalBrick.hit(); // Simulate a hit
        assertEquals(2, normalBrick.getStrength()); // Check if strength decreased

        normalBrick.hit(); // Another hit
        assertEquals(1, normalBrick.getStrength()); // Check if strength decreased

        normalBrick.hit(); // Another hit, should destroy the brick
        assertEquals(0, normalBrick.getStrength()); // Strength should be 0
        assertTrue(normalBrick.isDestroyed()); // Brick should be destroyed
    }

    @Test
    public void testHitSpecialBrick() {
        specialBrick.hit(); // Simulate a hit
        assertEquals(4, specialBrick.getStrength()); // Check if strength decreased

        for (int i = 0; i < 4; i++) {
            specialBrick.hit(); // Hit until destruction
        }
        assertEquals(0, specialBrick.getStrength()); // Strength should be 0
        assertTrue(specialBrick.isDestroyed()); // Brick should be destroyed
    }

    @Test
    public void testPowerUpTypeForSpecialBrick() {
        assertTrue(specialBrick.isSpecial()); // Check if it's special
        assertNotNull(specialBrick.getPowerUpType()); // Ensure it has a power-up type
    }

    @Test
    public void testIsDestroyedForNormalBrick() {
        assertFalse(normalBrick.isDestroyed()); // Initially, the brick is not destroyed
        normalBrick.hit(); // First hit
        normalBrick.hit(); // Second hit
        normalBrick.hit(); // Third hit
        assertTrue(normalBrick.isDestroyed()); // Now the brick should be destroyed
    }

    @Test
    public void testIsDestroyedForSpecialBrick() {
        assertFalse(specialBrick.isDestroyed()); // Initially, the special brick is not destroyed
        for (int i = 0; i < 5; i++) {
            specialBrick.hit(); // Hit the brick 5 times
        }
        assertTrue(specialBrick.isDestroyed()); // Now the brick should be destroyed
    }

    @Test
    public void testRandomPowerUpAssignment() {
        Brick specialBrick2 = new Brick(300, 400, 50, 20, 5, true); // Create another special brick
        assertTrue(specialBrick2.isSpecial());
        assertNotNull(specialBrick2.getPowerUpType()); // Ensure it has a random power-up type
    }
}
