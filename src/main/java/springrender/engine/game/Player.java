package springrender.engine.game;

import springrender.engine.graphics.Sprite;
import springrender.engine.graphics.SpriteRender;
import springrender.engine.input.InputHandler;
import springrender.engine.rendering.GamePanel;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private GamePanel gamePanel;
    private InputHandler inputHandler;

    private double moveSpeed = 120; // pixels per second

    private SpriteRender spriteRender;
    private Sprite sprite;

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
        initializeSprite();
    }

    private void initializeSprite() {
        sprite = new Sprite();
        sprite.loadImage("up1", "/images/player/player_up_1.png");
        sprite.loadImage("up1", "/images/player/player_up_2.png");
        sprite.loadImage("up1", "/images/player/player_up_3.png");
        sprite.loadImage("up1", "/images/player/player_up_4.png");
        sprite.loadImage("down1", "/images/player/player_down_1.png");
        sprite.loadImage("down1", "/images/player/player_down_2.png");
        sprite.loadImage("down1", "/images/player/player_down_3.png");
        sprite.loadImage("down1", "/images/player/player_down_4.png");
        sprite.loadImage("right1", "/images/player/player_right_1.png");
        sprite.loadImage("right1", "/images/player/player_right_2.png");
        sprite.loadImage("right1", "/images/player/player_right_3.png");
        sprite.loadImage("right1", "/images/player/player_right_4.png");
        sprite.loadImage("left1", "/images/player/player_left_1.png");
        sprite.loadImage("left1", "/images/player/player_left_2.png");
        sprite.loadImage("left1", "/images/player/player_left_3.png");
        sprite.loadImage("left1", "/images/player/player_left_4.png");

        sprite.setState("down1");
        this.spriteRender = new SpriteRender(sprite, gamePanel.updateManager);
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
        System.out.println(moveAmount);
        boolean moving = false;
        if (inputHandler.isUpPressed()) {
            currentPositionY -= moveAmount;
            sprite.setState("up1");
            moving = true;
        }
        if (inputHandler.isDownPressed()) {
            currentPositionY += moveAmount;
            System.out.println(currentPositionY);
            sprite.setState("down1");
            moving = true;
        }
        if (inputHandler.isLeftPressed()) {
            currentPositionX -= moveAmount;
            sprite.setState("left1");
            moving = true;
        }
        if (inputHandler.isRightPressed()) {
            currentPositionX += moveAmount;
            sprite.setState("right1");
            moving = true;
        }


        // Add boundary checks to prevent the player from moving out of the screen
        currentPositionX = Math.max(0, Math.min(currentPositionX, gamePanel.getWidth() - GamePanel.TILE_SIZE));
        currentPositionY = Math.max(0, Math.min(currentPositionY, gamePanel.getHeight() - GamePanel.TILE_SIZE));

        if (moving) {
            //sprite.update(dt);
        } else {
            // Optionally set to a default standing frame
            //sprite.setState("idle_" + direction);
        }
    }

    /**
     * Interpolates the player's position for smooth rendering.
     *
     * @param alpha The interpolation factor between 0 and 1.
     */
    public void interpolate(double alpha) {
        alpha = 0;
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
        BufferedImage image = spriteRender.getCurrentImage();
        graphics2D.drawImage(image, (int) currentPositionX, (int) currentPositionY, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);

    }

    public double getCurrentPositionX() {
        return currentPositionX;
    }

    public double getCurrentPositionY() {
        return currentPositionY;
    }

}
