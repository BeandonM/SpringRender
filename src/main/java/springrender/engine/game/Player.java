package springrender.engine.game;

import springrender.engine.core.*;
import springrender.engine.graphics.RenderManager;
import springrender.engine.graphics.Sprite;
import springrender.engine.graphics.SpriteRender;
import springrender.engine.input.InputHandler;
import springrender.engine.rendering.GamePanel;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private UpdateManager updateManager;

    private RenderManager renderManager;
    private InputHandler inputHandler;

    private Camera camera;

    private double moveSpeed = 300; // pixels per second

    private int layer;

    private SpriteRender spriteRender;
    private Sprite sprite;

    private String direction = "down";
    // Player positions

    //private Transform transform;

    private Vector2D previousTransform;
    private double previousPositionX;
    private double previousPositionY;
    private double currentPositionX;
    private double currentPositionY;

    // Interpolated render positions
    private double renderX;
    private double renderY;

    public Player(UpdateManager updateManager, RenderManager renderManager, InputHandler inputHandler) {
        this.updateManager = updateManager;
        this.renderManager = renderManager;
        this.inputHandler = inputHandler;
        initializeSprite();
        // this.currentPositionX = 100;
        //this.currentPositionY = 100;
        transform = new Transform(new Vector2D(300f, 300f));
        //previousTransform = transform;
        // this.previousPositionX = 100;
        //this.previousPositionY = 100;
        updateManager.addUpdatable(this);
        renderManager.addRenderable(this);
        this.layer = 2;
    }

    private void initializeSprite() {
        sprite = new Sprite();
        sprite.loadImage("up_idle", "/images/player/player_up_1.png");
        sprite.loadImage("up_move", "/images/player/player_up_1.png");
        sprite.loadImage("up_move", "/images/player/player_up_2.png");
        sprite.loadImage("up_move", "/images/player/player_up_3.png");
        sprite.loadImage("up_move", "/images/player/player_up_4.png");
        sprite.loadImage("down_idle", "/images/player/player_down_1.png");
        sprite.loadImage("down_move", "/images/player/player_down_1.png");
        sprite.loadImage("down_move", "/images/player/player_down_2.png");
        sprite.loadImage("down_move", "/images/player/player_down_3.png");
        sprite.loadImage("down_move", "/images/player/player_down_4.png");
        sprite.loadImage("right_idle", "/images/player/player_right_1.png");
        sprite.loadImage("right_move", "/images/player/player_right_1.png");
        sprite.loadImage("right_move", "/images/player/player_right_2.png");
        sprite.loadImage("right_move", "/images/player/player_right_3.png");
        sprite.loadImage("right_move", "/images/player/player_right_4.png");
        sprite.loadImage("left_idle", "/images/player/player_left_1.png");
        sprite.loadImage("left_move", "/images/player/player_left_1.png");
        sprite.loadImage("left_move", "/images/player/player_left_2.png");
        sprite.loadImage("left_move", "/images/player/player_left_3.png");
        sprite.loadImage("left_move", "/images/player/player_left_4.png");

        sprite.setState("down_idle");
        this.spriteRender = new SpriteRender(sprite, updateManager);
    }

    /**
     * Updates the player's position based on input.
     *
     * @param dt The fixed timestep in seconds.
     */
    @Override
    public void update(double dt) {
        //System.out.println("player update");
        // Store the previous position before updating
        //previousTransform = transform;
        previousPositionX = currentPositionX;
        previousPositionY = currentPositionY;

        float moveAmount = (float) (moveSpeed * dt);
        Vector2D moveVector = Vector2D.ZERO;
        //System.out.println(moveAmount);
        boolean moving = false;
        if (inputHandler.isUpPressed()) {
            moveVector = moveVector.add(Vector2D.UP.multiply(moveAmount));
            direction = "up";
            //transform.translate(); transform.add(new Vector2D(0f, -moveAmount));
            currentPositionY -= moveAmount;
            moving = true;
        }
        if (inputHandler.isDownPressed()) {
            moveVector = moveVector.add(Vector2D.DOWN.multiply(moveAmount));
            direction = "down";
            //transform = transform.add(new Vector2D(0f, moveAmount));
            currentPositionY += moveAmount;
            //System.out.println(currentPositionY);
            moving = true;
        }
        if (inputHandler.isLeftPressed()) {
            moveVector = moveVector.add(Vector2D.LEFT.multiply(moveAmount));
            //transform = transform.add(new Vector2D(-moveAmount, 0f));
            direction = "left";
            currentPositionX -= moveAmount;
            moving = true;
        }
        if (inputHandler.isRightPressed()) {
            moveVector = moveVector.add(Vector2D.RIGHT.multiply(moveAmount));
            direction = "right";
            //transform = transform.add(new Vector2D(moveAmount, 0f));
            currentPositionX += moveAmount;
            moving = true;
        }
        transform.translate(moveVector);

        // Add boundary checks to prevent the player from moving out of the screen
        //currentPositionX = Math.max(0, Math.min(currentPositionX, gamePanel.getWidth() - GamePanel.TILE_SIZE));
        //currentPositionY = Math.max(0, Math.min(currentPositionY, gamePanel.getHeight() - GamePanel.TILE_SIZE));
        String state = direction;
        if (moving) {
            state += "_move";
            //sprite.update(dt);
        } else {
            state += "_idle";
            // Optionally set to a default standing frame
            //sprite.setState("idle_" + direction);
        }
        spriteRender.setState(state);
        //System.out.println("Player position: " + transform.getPosition());
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

    @Override
    public Transform getTransform() {
        return transform;
    }

    /**
     * Draws the player at the interpolated position.
     *
     * @param graphics2D The graphics context.
     */
    @Override
    public void draw(Graphics2D graphics2D) {
        BufferedImage image = spriteRender.getCurrentImage();
        //System.out.println("Player position: " + transform.getPosition());
        //Vector2D screenPosition = camera.getWorldToScreenCoordinates(transform.getPosition());
        graphics2D.drawImage(image, (int) transform.getPosition().getX(), (int) transform.getPosition().getY(), GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);

    }

    @Override
    public int getLayer() {
        return layer;
    }

    public double getCurrentPositionX() {
        return currentPositionX;
    }

    public double getCurrentPositionY() {
        return currentPositionY;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

}
