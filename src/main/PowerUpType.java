package main;
/**
 * An enumeration representing the different types of power-ups available in the game.
 * Each power-up type corresponds to a specific effect that can enhance gameplay.
 */
public enum PowerUpType {
    /**
     * Increases the size of the player's paddle.
     */
    INCREASE_PADDLE_SIZE,

    /**
     * Increases the speed of the ball.
     */
    SPEED_BALL,

    /**
     * Decreases the speed of the ball.
     */
    DECREASE_BALL_SPEED,

    /**
     * Increases the speed of the player's paddle.
     */
    INCREASE_PADDLE_SPEED,

    /**
     * Decreases the speed of the player's paddle.
     */
    DECREASE_PADDLE_SPEED,

    /**
     * Grants an additional life to the player.
     */
    INCREASE_LIFE,

    /**
     * Increases the size of the ball.
     */
    INCREASE_BALL_SIZE,
}
