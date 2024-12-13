package springrender.engine.game;

import springrender.engine.input.InputHandler;
import springrender.engine.rendering.GamePanel;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private GamePanel gamePanel;
    private InputHandler inputHandler;

    private double moveSpeed = 60; // pixels per second

    private BufferedImage up1, down1, left1, right1;

    private String direction;
    // Player positions
    private double previousPositionX;
    private double previousPositionY;
    private double currentPositionX;
    private double currentPositionY;

    // Interpolated render positions
    private double renderX;
    private double renderY;

    public Player(GamePanel gamePanel, InputHandler inputHandler) {
        this.gamePanel = gamePanel;
        this.inputHandler = inputHandler;
        this.currentPositionX = 100;
        this.currentPositionY = 100;
        this.previousPositionX = 100;
        this.previousPositionY = 100;
        getPlayerImage();
    }

    /**
     * Updates the player's position based on input.
     *
     * @param dt The fixed timestep in seconds.
     */
    @Override
    public void update(double dt) {
        // Store the previous position before updating
        previousPositionX = currentPositionX;
        previousPositionY = currentPositionY;

        double moveAmount = moveSpeed * dt;

        if (inputHandler.isUpPressed()) currentPositionY -= moveAmount;
        if (inputHandler.isDownPressed()) currentPositionY += moveAmount;
        if (inputHandler.isLeftPressed()) currentPositionX -= moveAmount;
        if (inputHandler.isRightPressed()) currentPositionX += moveAmount;

        // Optional: Add boundary checks to prevent the player from moving out of the screen
        currentPositionX = Math.max(0, Math.min(currentPositionX, gamePanel.getWidth() - GamePanel.TILE_SIZE));
        currentPositionY = Math.max(0, Math.min(currentPositionY, gamePanel.getHeight() - GamePanel.TILE_SIZE));
    }

    /**
     * Interpolates the player's position for smooth rendering.
     *
     * @param alpha The interpolation factor between 0 and 1.
     */
    public void interpolate(double alpha) {
        renderX = previousPositionX * (1.0 - alpha) + currentPositionX * alpha;
        renderY = previousPositionY * (1.0 - alpha) + currentPositionY * alpha;
    }

    /**
     * Draws the player at the interpolated position.
     *
     * @param graphics2D The graphics context.
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect((int) renderX, (int) renderY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
    }

    // Getters for positions (optional, if needed elsewhere)
    public double getCurrentPositionX() {
        return currentPositionX;
    }

    public double getCurrentPositionY() {
        return currentPositionY;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/images/player/player_up_1.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
