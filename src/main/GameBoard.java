package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the game board for the Block Blaster game.
 * This class is responsible for managing the game state, rendering graphics,
 * and handling user input.
 */
public class GameBoard extends JPanel implements ActionListener {

    private Map<PowerUpType, Timer> activePowerUps; // Track active power-ups with their timers
    private final int POWER_UP_DURATION = 5000;
    private Timer messageTimer;
    private boolean isStartScreen = true; // Start the game with the start screen active
    private boolean isGameOver = false;
    private boolean isWaitingForRetry = false; // New flag to track if the player is waiting to retry
    private Timer timer;
    private Paddle paddle;
    private Ball ball;
    private ArrayList<Brick> bricks;
    private boolean isYouWon = false; // Track if the player has won
    private int lives = 15; // Player starts with 15 lives
    private String lastPowerUp = ""; // This will store the last power-up the user receives
    private int currentLevel = 1; // Start with level 1
    private boolean isLevelTransition = false; // Track if we are in between levels
    private Timer transitionTimer; // Timer for level transition

    /**
     * Constructs a new GameBoard instance and initializes the game board.
     */

    public GameBoard() {
        initGameBoard();
        activePowerUps = new HashMap<>();
    }

    /**
     * Draws the start screen with the game title, instructions, rules, and block
     * representations.
     * 
     * @param g the Graphics object used for drawing
     */
    private void drawStartScreen(Graphics g) {
        String title = "Welcome to Block Blaster!";
        String instructions = "Press ENTER to Start";
        String[] rules = {
                "Rules:",
                "You have three lives to win. When the ball hits a brick it breaks!",
                "To win, BREAK THEM ALL!",
                "- White Blocks: Break in 1 hit",
                "- Magenta Blocks: Break in 2 hits",
                "- Cyan Blue Blocks: Surprise Bricks!"
        };

        // Draw the title
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 36));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString(title, (getWidth() - metrics.stringWidth(title)) / 2, getHeight() / 4); // Moved a little higher

        // Draw the instructions
        g.setFont(new Font("Helvetica", Font.PLAIN, 24));
        metrics = getFontMetrics(g.getFont());
        g.drawString(instructions, (getWidth() - metrics.stringWidth(instructions)) / 2, getHeight() / 3);

        // Adjust yPosition to properly center the block of rules text
        g.setFont(new Font("Helvetica", Font.PLAIN, 18));
        metrics = getFontMetrics(g.getFont());

        int textBlockHeight = rules.length * 25; // Each line is 25 pixels apart
        int startYPosition = (getHeight() / 2) - (textBlockHeight / 2); // Center the entire text block vertically

        for (String line : rules) {
            g.drawString(line, (getWidth() - metrics.stringWidth(line)) / 2, startYPosition);
            startYPosition += 25; // Add vertical space between lines
        }

        // Increase spacing for the blocks to avoid overlap
        int blockSize = 40;
        int blockYPosition = startYPosition + 30; // Adjust Y position for the blocks

        // White Block (Normal)
        g.setColor(Color.WHITE);
        g.fillRect((getWidth() / 2) - blockSize - 100, blockYPosition, blockSize, blockSize);

        // Magenta Block (2-hit)
        g.setColor(Color.MAGENTA);
        g.fillRect((getWidth() / 2) - (blockSize / 2), blockYPosition, blockSize, blockSize);

        // Cyan Blue Block (Power-up)
        g.setColor(Color.CYAN);
        g.fillRect((getWidth() / 2) + blockSize + 60, blockYPosition, blockSize, blockSize);
    }

    /**
     * Initializes the game board by setting up key listeners,
     * game components, and the game timer.
     */
    private void initGameBoard() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();

                if (key == KeyEvent.VK_ENTER) {
                    if (isStartScreen) {
                        isStartScreen = false; // Move from start screen to game
                        timer.start(); // Start the game timer
                        repaint(); // Repaint the screen to switch to the game
                    } else if (isWaitingForRetry) {
                        resetBall(); // Reset the ball's position
                        isWaitingForRetry = false; // Continue the game
                        timer.start(); // Resume the timer
                        repaint(); // Redraw the game screen
                    } else if (isYouWon) {
                        restartGame(); // Restart the game after winning
                    } else if (isGameOver) {
                        restartGame(); // Restart the game
                    }
                }

                paddle.keyPressed(e); // Handle paddle movement
            }

            @Override
            public void keyReleased(KeyEvent e) {
                paddle.keyReleased(e);
            }
        });

        setFocusable(true);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(1000, 800)); // Setting game window size

        paddle = new Paddle();
        ball = new Ball();
        bricks = new ArrayList<>();

        initBricks();

        timer = new Timer(10, this);
        timer.start();
    }

    /**
     * Initializes the bricks for the current level based on defined parameters.
     * Sets the layout, strength, and type (special or normal) of the bricks.
     */
    private void initBricks() {
        int brickRows, brickColumns;
        int brickWidth = 60; // Set the width of each brick
        int brickHeight = 20; // Set the height of each brick
        int brickPadding = 5; // Padding between bricks
        double specialBrickProbability = 0.1; // 10% chance to be a special brick
        double strongBrickProbability = 0.3; // 30% chance to be a strong brick (2 hits required)

        // Define brick layout based on the current level
        if (currentLevel == 1) {
            brickRows = 7; // Level 1: fewer bricks
            brickColumns = 9;
        } else if (currentLevel == 2) {
            brickRows = 8; // Level 2: more bricks
            brickColumns = 11;
        } else {
            brickRows = 11; // Level 3: even more bricks
            brickColumns = 11;
        }

        // Clear any existing bricks before adding new ones
        bricks.clear();

        // Initialize new bricks for the current level
        for (int i = 0; i < brickRows; i++) {
            for (int j = 0; j < brickColumns; j++) {
                int brickX = j * (brickWidth + brickPadding) + 30; // X position of the brick
                int brickY = i * (brickHeight + brickPadding) + 50; // Y position of the brick

                // Determine if the brick is special or strong
                boolean isSpecial = Math.random() < specialBrickProbability;
                int strength;

                if (isSpecial) {
                    strength = 1; // Special bricks should only take 1 hit, but they trigger a power-up
                } else if (Math.random() < strongBrickProbability) {
                    strength = 2; // Strong brick (requires 2 hits)
                } else {
                    strength = 1; // Normal brick (requires 1 hit)
                }

                // Add the brick to the array
                bricks.add(new Brick(brickX, brickY, brickWidth, brickHeight, strength, isSpecial));
            }
        }

        // Debugging information to check brick initialization
        System.out.println("Bricks initialized for level " + currentLevel + ": " + bricks.size());
    }

    /**
     * Handles the action events for the game, such as moving the ball and paddle,
     * checking for collisions, and repainting the game board.
     * 
     * @param e the ActionEvent that triggered this method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isGameOver && !isWaitingForRetry) {
            ball.move(); // Move the ball
            paddle.move(); // Move the paddle
            checkCollision(); // Check for collisions with bricks and paddle
            checkMissedBall(); // Check if the ball was missed
            repaint(); // Redraw the game screen
        }
    }

    /**
     * Checks if the ball has been missed by the paddle. If the ball goes below
     * the screen, it reduces the player's lives and handles game over conditions.
     */
    private void checkMissedBall() {
        if (ball.getY() > getHeight()) { // If the ball goes below the screen (missed)
            lives--; // Decrease the player's lives by 1

            if (lives > 0) {
                isWaitingForRetry = true; // Player is waiting to retry
                timer.stop(); // Stop the game until they press Enter
            } else {
                isGameOver = true; // If no lives are left, the game is over
                timer.stop(); // Stop the game loop
            }

            repaint(); // Ensure the game objects are still painted after losing a life
        }
    }

    /**
     * Checks for collisions between the ball and the paddle, as well as the ball
     * and the bricks.
     * Handles the logic for what happens when a collision occurs, including brick
     * destruction
     * and power-up activation.
     */
    private void checkCollision() {
        if (ball.getRect().intersects(paddle.getRect())) {
            ball.reverseY(); // Ball bounces off the paddle
        }

        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);

            if (ball.getRect().intersects(brick.getRect())) {
                ball.reverseY(); // Ball bounces off the brick
                brick.hit(); // Reduce the brick's strength

                // If the brick is destroyed, handle removal and power-up
                if (brick.isDestroyed()) {
                    // If the brick is special, trigger the power-up
                    if (brick.isSpecial()) {
                        triggerPowerUp(brick.getPowerUpType()); // Trigger the power-up for special bricks
                    }

                    // Remove the brick after power-up is triggered
                    bricks.remove(i);
                    i--; // Adjust index after removing the brick
                }

                break; // Only process one brick collision per frame
            }
        }

        // Check if all bricks are destroyed AFTER iterating through all bricks
        if (bricks.isEmpty()) {
            if (currentLevel < 3) { // If not at the final level
                isLevelTransition = true; // Enter level transition state
                startTransitionTimer(); // Start the level transition timer
            }

            else {
                // If no more levels, the player has won
                isGameOver = true; // Mark the game as over
                isYouWon = true; // Set the "You Won" flag
                timer.stop(); // Stop the game loop
            }
        }

    }

    /**
     * Starts the timer for level transitions. This pauses the game and then
     * initializes the next level after a delay.
     */
    private void startTransitionTimer() {
        // Stop the main game timer to pause gameplay
        timer.stop();

        // Create a timer to delay the next level by 2 seconds (2000 milliseconds)
        transitionTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentLevel++; // Progress to the next level
                initBricks(); // Initialize bricks for the new level
                resetBall(); // Reset the ball's position
                isLevelTransition = false; // Exit the level transition state
                timer.start(); // Resume the game timer

                transitionTimer.stop(); // Stop the transition timer
            }
        });

        transitionTimer.setRepeats(false); // Timer should only fire once
        transitionTimer.start(); // Start the transition timer
    }

    /**
     * Triggers the specified power-up effect based on the powerUpType provided.
     * This method applies the effect of the power-up to the paddle or ball,
     * updates the lastPowerUp message, and starts a timer to reverse the effect
     * after a defined duration.
     *
     * @param powerUpType The type of power-up to be triggered.
     */
    private void triggerPowerUp(PowerUpType powerUpType) {
        switch (powerUpType) {
            case INCREASE_PADDLE_SIZE:
                paddle.increaseSize(); // Increase paddle size
                lastPowerUp = "Power-Up: Paddle Size Increased!";
                startMessageTimer();
                break;
            case SPEED_BALL:
                ball.speedUp(); // Speed up the ball
                if (ball.speedChange) {
                    lastPowerUp = "Power-Down: Ball Speed Increased!";
                    startMessageTimer();
                } else {
                    lastPowerUp = "At Max Ball Speed. Can Not Increase!";
                }
                break;
            case INCREASE_PADDLE_SPEED:
                paddle.increasePaddleSpeed(); // Speed up paddle
                if (paddle.increasePaddleSpeed) {
                    lastPowerUp = "Power-Up: Paddle Speed Increased!";
                    startMessageTimer();
                } else {
                    lastPowerUp = "At Max Paddle Speed. Paddle Speed Cannot Increase!";
                }
                break;
            case DECREASE_PADDLE_SPEED:
                paddle.decreasePaddleSpeed(); // Slow down paddle
                if (paddle.decreasePaddleSpeed) {
                    lastPowerUp = "Power-Down: Paddle Speed Decreased";
                    startMessageTimer();
                } else {
                    lastPowerUp = "At Minimum Paddle Speed. Cannot Decrease!";
                }
                break;
            case INCREASE_LIFE:
                lives += 1; // Increase player's life
                lastPowerUp = "Power-Up: Life Gained";
                startMessageTimer();
                break;
            case INCREASE_BALL_SIZE:
                ball.increaseBallSize();
                if (ball.radiusChange) {
                    lastPowerUp = "Power-Up: Ball Size Increased!";
                    startMessageTimer();
                } else {
                    lastPowerUp = "Ball at maximum size. Cannot Increase Ball Size!";
                }
                break;
            case DECREASE_BALL_SPEED:
                ball.slowDown();
                if (ball.speedChange) {
                    lastPowerUp = "Power-Up: Ball Speed Decreased!";
                    startMessageTimer();
                } else {
                    lastPowerUp = "At Min Ball Speed. Cannot Decrease Ball Speed!";
                }
                break;
        }

        // Start a timer for the power-up
        if (activePowerUps.containsKey(powerUpType)) {
            activePowerUps.get(powerUpType).stop(); // Stop any existing timer for the same power-up
        }

        Timer timer = new Timer(POWER_UP_DURATION, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reversePowerUp(powerUpType); // Revert the power-up effect after duration
                activePowerUps.remove(powerUpType);
            }
        });

        timer.setRepeats(false); // Power-up timer should not repeat
        timer.start();
        activePowerUps.put(powerUpType, timer); // Track the active power-up timer

        repaint(); // Repaint the screen to show the updated power-up message
    }

    /**
     * Starts a timer that clears the last power-up message after a specified
     * duration.
     * The message is displayed briefly to inform the player of the effect of the
     * power-up.
     */
    private void startMessageTimer() {
        if (messageTimer != null) {
            messageTimer.stop(); // Stop any previous timer if it exists
        }

        // Create a new timer to clear the message after 1 second (1000 milliseconds)
        messageTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lastPowerUp = ""; // Clear the message
                messageTimer.stop(); // Stop the timer after it fires
                repaint(); // Repaint the screen to remove the message
            }
        });

        messageTimer.setRepeats(false); // Make sure the timer only runs once
        messageTimer.start(); // Start the timer
    }

    /**
     * Reverses the effects of the specified power-up type after its duration ends.
     * Resets the attributes of the paddle or ball back to their original states.
     *
     * @param powerUpType The type of power-up to reverse.
     */
    private void reversePowerUp(PowerUpType powerUpType) {
        switch (powerUpType) {
            case INCREASE_PADDLE_SIZE:
                paddle.resetSize(); // Reset to normal size
                lastPowerUp = "Paddles size back to normal.";
                startMessageTimer();
                break;
            case SPEED_BALL:
                ball.slowDown(); // Reverse speed increase
                lastPowerUp = "Ball speed back to normal.";
                startMessageTimer();
                break;
            case INCREASE_PADDLE_SPEED:
                paddle.resetSpeed(); // Reset paddle speed to normal
                lastPowerUp = "Paddle speed back to normal.";
                startMessageTimer();
                break;
            case DECREASE_PADDLE_SPEED:
                paddle.resetSpeed(); // Reset paddle speed to normal
                lastPowerUp = "Paddle speed back to normal.";
                startMessageTimer();
                break;
            case INCREASE_BALL_SIZE:
                ball.resetSize(); // Reset ball size to normal
                lastPowerUp = "Ball size back to normal.";
                startMessageTimer();
                break;
            case DECREASE_BALL_SPEED:
                ball.speedUp(); // Reverse speed decrease
                lastPowerUp = "Ball speed back to normal.";
                startMessageTimer();
                break;
            case INCREASE_LIFE:
                lastPowerUp = "Life can not be reversed";
                startMessageTimer();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + powerUpType);
        }
        repaint();
    }

    /**
     * Resets the ball to its initial position and starts the game timer.
     */
    private void resetBall() {
        ball.reset(); // Reset the ball to its initial position
        timer.start(); // Start the game timer
    }

    /**
     * Draws the "You Won!" screen when the player wins the game.
     * Displays the number of lives left and prompts the player to play again.
     *
     * @param g the Graphics object to draw on
     */
    private void drawYouWonScreen(Graphics g) {
        String message = "You Won!";
        String livesMessage = "Lives Left: " + lives;
        String playAgainMessage = "Play Again? Press Enter";

        // Set font for "You Won" message
        Font font = new Font("Helvetica", Font.BOLD, 36);
        g.setFont(font);
        FontMetrics metrics = getFontMetrics(g.getFont());

        // Calculate the x position to center the "You Won" message
        int xMessage = (getWidth() - metrics.stringWidth(message)) / 2;
        int yMessage = getHeight() / 2 - 50; // Position the message slightly above the center

        // Draw the "You Won" message
        g.setColor(Color.GREEN);
        g.drawString(message, xMessage, yMessage);

        // Set font for the lives left and play again message
        Font smallFont = new Font("Helvetica", Font.PLAIN, 24);
        g.setFont(smallFont);
        FontMetrics smallMetrics = getFontMetrics(g.getFont());

        // Calculate the x position to center the "Lives Left" message
        int xLives = (getWidth() - smallMetrics.stringWidth(livesMessage)) / 2;
        int yLives = getHeight() / 2 + 10; // Position the lives message below the "You Won" message
        g.drawString(livesMessage, xLives, yLives);

        // Calculate the x position to center the "Play Again" message
        int xPlayAgain = (getWidth() - smallMetrics.stringWidth(playAgainMessage)) / 2;
        int yPlayAgain = getHeight() / 2 + 50; // Position the play again message below the lives message
        g.drawString(playAgainMessage, xPlayAgain, yPlayAgain);
    }

    /**
     * Paints the components of the game board.
     * This method handles the rendering of the game elements, including the
     * start screen, game objects, power-up messages, and the game over or
     * retry screens, based on the current game state.
     *
     * @param g the Graphics object to draw on
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Clear the screen first

        if (isStartScreen) {
            drawStartScreen(g); // Show the start screen
        } else if (isLevelTransition) {
            drawLevelTransitionScreen(g); // Show the transition screen between levels
        } else if (!isGameOver && !isWaitingForRetry) {
            drawObjects(g); // Draw the paddle, ball, and bricks during the game
            drawLives(g); // Draw the remaining lives

            // Draw power-up message
            if (!lastPowerUp.isEmpty()) {
                g.setFont(new Font("Helvetica", Font.PLAIN, 20));
                g.drawString(lastPowerUp, (getWidth() - g.getFontMetrics().stringWidth(lastPowerUp)) / 2,
                        getHeight() - 50);
            }

            // Draw stats on the side
            drawStats(g);
            drawBoundaryLine(g);
        } else if (isWaitingForRetry) {
            drawRetryScreen(g); // Show the retry screen with lives remaining
        } else if (isYouWon) { // Display the You Won screen
            drawYouWonScreen(g);
        } else if (isGameOver) {
            drawGameOver(g); // Show the game over screen
        }
    }

    /**
     * Draws the level transition screen when the player completes a level.
     * Displays messages indicating the completion of the current level and
     * prepares the player for the next level.
     *
     * @param g the Graphics object to draw on
     */
    private void drawLevelTransitionScreen(Graphics g) {
        String message = "Level " + currentLevel + " Complete!";
        String nextMessage = "Get ready for Level " + (currentLevel + 1) + "...";

        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.BOLD, 36));
        FontMetrics metrics = getFontMetrics(g.getFont());

        // Center the "Level Complete" message
        int xMessage = (getWidth() - metrics.stringWidth(message)) / 2;
        int yMessage = getHeight() / 2 - 50; // Position the message slightly above the center
        g.drawString(message, xMessage, yMessage);

        // Set font for the "Next Level" message
        g.setFont(new Font("Helvetica", Font.PLAIN, 24));
        metrics = getFontMetrics(g.getFont());

        // Center the "Next Level" message
        int xNextMessage = (getWidth() - metrics.stringWidth(nextMessage)) / 2;
        int yNextMessage = getHeight() / 2 + 10; // Position the next message below the first
        g.drawString(nextMessage, xNextMessage, yNextMessage);
    }

    /**
     * Draws the game statistics on the game board.
     * Displays current level, paddle speed, paddle size, ball speed,
     * and ball size on the right side of the game screen.
     *
     * @param g the Graphics object to draw on
     */
    private void drawStats(Graphics g) {
        g.setColor(Color.WHITE); // Set color for the stats text
        g.setFont(new Font("Helvetica", Font.PLAIN, 18));

        // Display the stats further to the right
        g.drawString("Game Stats:", 850, 50); // Move stats to start at x=850
        g.drawString("Level: " + currentLevel, 850, 80);
        g.drawString("Paddle Speed: " + paddle.getSpeed(), 850, 110);
        g.drawString("Paddle Size: " + paddle.getWidth(), 850, 140);
        g.drawString("Ball Speed: " + ball.getSpeed(), 850, 170);
        g.drawString("Ball Size: " + ball.getRadius(), 850, 200);
    }

    /**
     * Draws the boundary line on the game board.
     * This method draws a visual boundary to help players identify the play area.
     *
     * @param g the Graphics object to draw on
     */
    private void drawBoundaryLine(Graphics g) {
        // Convert Graphics to Graphics2D to use advanced features like setting stroke
        Graphics2D g2d = (Graphics2D) g;

        // Set the stroke to make the line wider (e.g., 5 pixels wide)
        g2d.setStroke(new BasicStroke(5));
        g2d.setColor(Color.WHITE); // Set the color for the boundary lines

        // Draw the horizontal boundary line at the bottom (y = 550), stopping before
        // the right vertical boundary
        g2d.drawLine(0, 550, getWidth() - 200, 550); // Adjust the end of the line to stop before the right boundary

        // Draw the vertical boundary line, starting from the top to just above the
        // horizontal line
        g2d.drawLine(getWidth() - 200, 0, getWidth() - 200, 550); // Stop the vertical line at the same y position as
                                                                  // the horizontal line
    }

    /**
     * Draws the game objects including the paddle, ball, and bricks.
     * Updates the graphics based on the current state of the game.
     *
     * @param g the Graphics object to draw on
     */
    private void drawObjects(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(paddle.getX(), paddle.getY(), paddle.getWidth(), paddle.getHeight());

        g.setColor(Color.WHITE);
        g.fillOval(ball.getX(), ball.getY(), ball.getRadius(), ball.getRadius());

        // Draw bricks with different colors based on strength
        for (Brick brick : bricks) {
            if (brick.getStrength() == 2) {
                g.setColor(Color.MAGENTA); // Stronger bricks are blue
            } else {
                g.setColor(Color.WHITE); // Standard bricks are red
            }

            if (brick.isSpecial()) {
                g.setColor(Color.CYAN); // Special bricks are green
            }

            g.fillRect(brick.getX(), brick.getY(), brick.getWidth(), brick.getHeight());
        }

        Toolkit.getDefaultToolkit().sync(); // Syncing for smoother animation
    }

    /**
     * Draws the remaining lives of the player on the game board.
     * Displays the number of lives in the top left corner.
     *
     * @param g the Graphics object to draw on
     */
    private void drawLives(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Helvetica", Font.PLAIN, 18));
        g.drawString("Lives: " + lives, 10, 20); // Display the remaining lives in the top left corner
    }

    /**
     * Draws the retry screen when the player has lost a life.
     * Informs the player how many lives they have left and provides an option to
     * retry.
     *
     * @param g the Graphics object to draw on
     */
    private void drawRetryScreen(Graphics g) {
        String message = "You have " + lives + " lives left.";
        String retryMessage = "Press Enter to try again.";

        Font font = new Font("Helvetica", Font.BOLD, 36);
        FontMetrics metrics = getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);

        // Center the first message horizontally
        int x = (getWidth() - metrics.stringWidth(message)) / 2;
        int y = getHeight() / 2 - 50; // Y-coordinate (vertically centered a bit higher)
        g.drawString(message, x, y);

        // Set a smaller font for the retry message
        Font smallFont = new Font("Helvetica", Font.PLAIN, 24);
        g.setFont(smallFont);
        FontMetrics smallMetrics = getFontMetrics(smallFont);

        // Center the retry message horizontally
        int xRetry = (getWidth() - smallMetrics.stringWidth(retryMessage)) / 2;
        int yRetry = getHeight() / 2 + 30; // Y-coordinate (vertically centered a bit lower)
        g.drawString(retryMessage, xRetry, yRetry);
    }

    /**
     * Draws the game over screen when the player runs out of lives.
     * Displays a message indicating that the game is over and provides an option to
     * play again.
     *
     * @param g the Graphics object to draw on
     */
    private void drawGameOver(Graphics g) {
        String message = "Game Over!";
        String playAgainMessage = "Play Again? Press Enter";

        // Set font for "Game Over" message
        Font font = new Font("Helvetica", Font.BOLD, 36);
        g.setFont(font);
        FontMetrics metrics = getFontMetrics(font);

        // Calculate the x position to center the "Game Over" message
        int xMessage = (getWidth() - metrics.stringWidth(message)) / 2;
        int yMessage = getHeight() / 2 - 50; // Position the "Game Over" message slightly above the center

        // Draw the "Game Over" message
        g.setColor(Color.RED);
        g.drawString(message, xMessage, yMessage);

        // Set font for "Play Again" message
        Font smallFont = new Font("Helvetica", Font.PLAIN, 24);
        g.setFont(smallFont);
        FontMetrics smallMetrics = getFontMetrics(smallFont);

        // Calculate the x position to center the "Play Again" message
        int xPlayAgain = (getWidth() - smallMetrics.stringWidth(playAgainMessage)) / 2;
        int yPlayAgain = getHeight() / 2 + 30; // Position the "Play Again" message slightly below the center

        // Draw the "Play Again" message
        g.setColor(Color.WHITE);
        g.drawString(playAgainMessage, xPlayAgain, yPlayAgain);
    }

    /**
     * Restarts the game by resetting the game state.
     * Resets the number of lives, ball position, paddle, and bricks.
     */
    private void restartGame() {
        isGameOver = false;
        currentLevel = 1; // Reset level to 1
        lives = 15; // Reset lives to 15
        ball.reset(); // Reset ball position
        paddle = new Paddle(); // Reset paddle
        initBricks(); // Reset the bricks
        timer.start(); // Restart the game loop
    }
}
