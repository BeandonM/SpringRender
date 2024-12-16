package springrender.engine.game;

import springrender.engine.core.*;
import springrender.engine.graphics.RenderManager;
import springrender.engine.graphics.Sprite;
import springrender.engine.graphics.SpriteRender;
import springrender.engine.input.InputHandler;
import springrender.engine.rendering.GamePanel;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    private UpdateManager updateManager;

    private RenderManager renderManager;
    private InputHandler inputHandler;

    private CollisionManager collisionManager;

    public DynamicBoxCollider boxCollider;

    private Camera camera;

    private float moveSpeed = 3; // pixels per second

    private int layer;

    private SpriteRender spriteRender;
    private Sprite sprite;

    private String direction = "down";

    // Player positions


    // Interpolated render positions
    private double renderX;
    private double renderY;

    public Player(UpdateManager updateManager, RenderManager renderManager, CollisionManager collisionManager, InputHandler inputHandler) {
        this.updateManager = updateManager;
        this.renderManager = renderManager;
        this.collisionManager = collisionManager;
        this.inputHandler = inputHandler;
        initializeSprite();
        // this.currentPositionX = 100;
        //this.currentPositionY = 100;
        transform = new Transform(new Vector2D(300f, 300f));
        boxCollider = new DynamicBoxCollider(transform, 48, 48);
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
        float moveAmount = (float) (moveSpeed * dt);
        Vector2D moveVector = Vector2D.ZERO;
        boolean moving = false;

        if (inputHandler.isUpPressed()) {
            moveVector = moveVector.add(Vector2D.UP);
            direction = "up";
            moving = true;
        }
        if (inputHandler.isDownPressed()) {
            moveVector = moveVector.add(Vector2D.DOWN);
            direction = "down";
            moving = true;
        }
        if (inputHandler.isLeftPressed()) {
            moveVector = moveVector.add(Vector2D.LEFT);
            direction = "left";
            moving = true;
        }
        if (inputHandler.isRightPressed()) {
            moveVector = moveVector.add(Vector2D.RIGHT);
            direction = "right";
            moving = true;
        }
        moveVector = moveVector.normalize().multiply(moveSpeed);

        // Split movement into X and Y components for independent collision checks
        Vector2D futurePositionX = new Vector2D(transform.getPosition().getX() + moveVector.getX(), transform.getPosition().getY());
        Vector2D futurePositionY = new Vector2D(transform.getPosition().getX(), transform.getPosition().getY() + moveVector.getY());

        // Check X-axis collision
        Rectangle futureBoundingBoxX = new Rectangle(
                (int) futurePositionX.getX(),
                (int) futurePositionX.getY(),
                boxCollider.getBoundingBox().getBounds().width,
                boxCollider.getBoundingBox().getBounds().height
        );
        if (collisionManager.isValidMove(futureBoundingBoxX)) {
            transform.translate(new Vector2D(moveVector.getX(), 0));
        }

        // Check Y-axis collision
        Rectangle futureBoundingBoxY = new Rectangle(
                (int) futurePositionY.getX(),
                (int) futurePositionY.getY(),
                boxCollider.getBoundingBox().getBounds().width,
                boxCollider.getBoundingBox().getBounds().height
        );
        if (collisionManager.isValidMove(futureBoundingBoxY)) {
            transform.translate(new Vector2D(0, moveVector.getY()));
        }

        String state = direction;
        if (moving) {
            state += "_move";
        } else {
            state += "_idle";
        }
        spriteRender.setState(state);
    }

    /*
    private boolean canMove(Vector2D futurePosition) {
        // Get bounding box for the player's future position
        Rectangle futureBoundingBox = new Rectangle(
                (int) futurePosition.getX(),
                (int) futurePosition.getY(),
                boxCollider.getBoundingBox().getBounds().width,
                boxCollider.getBoundingBox().getBounds().height
        );

        // Check collisions using CollisionManager
        for (StaticCollider collider : collisionManager.getStaticColliders()) {
            if (futureBoundingBox.intersects(collider.getBoundingBox().getBounds())) {
                return false;
            }
        }
        return true;
    }

     */

    /**
     * Interpolates the player's position for smooth rendering.
     *
     * @param alpha The interpolation factor between 0 and 1.
     */
    public void interpolate(double alpha) {
        //alpha = 0;
        //renderX = previousPositionX * (1.0 - alpha) + currentPositionX * alpha;
        //renderY = previousPositionY * (1.0 - alpha) + currentPositionY * alpha;
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
        Rectangle2D bounds2D = boxCollider.getBoundingBox().getBounds2D();
        graphics2D.drawRect((int) bounds2D.getX(), (int) bounds2D.getY(), (int) bounds2D.getWidth(), (int) bounds2D.getHeight());
    }

    @Override
    public int getLayer() {
        return layer;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

}
