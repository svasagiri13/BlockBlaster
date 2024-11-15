package main;
import javax.swing.JFrame;

/**
 * The BlockBlaster class represents the main frame for the Block Blaster game.
 * It extends JFrame to create a window where the game will be displayed.
 */
public class BlockBlaster extends JFrame {

    /**
     * Constructs a BlockBlaster game window and initializes the user interface.
     */
    public BlockBlaster() {
        initUI();  // Initialize the user interface
    }

    /**
     * Initializes the user interface of the game.
     * This method sets up the main game board, window title, 
     * close operation, size, and other window properties.
     */
    private void initUI() {
        add(new GameBoard());  // Adding the game board where the game will run
        setTitle("Block Blaster");  // Set the title of the window
        setDefaultCloseOperation(EXIT_ON_CLOSE);  // Exit the application when the window is closed
        setSize(1000, 800);  // Set the window size to 1000x800 pixels
        setLocationRelativeTo(null);  // Center the window on the screen
        setResizable(false);  // Disable window resizing
    }

    /**
     * The main method to start the Block Blaster game.
     * It creates an instance of BlockBlaster and makes it visible.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            BlockBlaster game = new BlockBlaster();  // Create a new game instance
            game.setVisible(true);  // Make the game window visible
        });
    }
}
