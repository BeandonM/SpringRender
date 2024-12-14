package springrender.engine.core;

import springrender.engine.graphics.RenderManager;
import springrender.engine.rendering.GamePanel;

import java.awt.Graphics2D;

public class Camera implements Updatable, Transformable {

    private GamePanel gamePanel;

    private UpdateManager updateManager;
    private RenderManager renderManager;

    private TileManager tileManager;

    private Transform transform;
    private Transform target;

    public float viewportWidth;
    public float viewportHeight;

    public Camera(GamePanel gamePanel, UpdateManager updateManager, RenderManager renderManager) {
        this.gamePanel = gamePanel;
        this.updateManager = updateManager;
        this.renderManager = renderManager;
        this.transform = new Transform(Vector2D.ZERO);

        initializeViewport();
        // Set the viewport size based on the GamePanel dimensions
        this.viewportWidth = gamePanel.getWidth();
        this.viewportHeight = gamePanel.getHeight();

        this.updateManager.addUpdatable(this);
    }

    public void initializeViewport() {
        this.viewportWidth = (float) gamePanel.getWidth();
        this.viewportHeight = (float) gamePanel.getHeight();

        if (viewportWidth == 0 || viewportHeight == 0) {
            // Log a warning to indicate a potential issue
            System.err.println("Warning: Viewport size is not initialized correctly. Defaulting to fallback dimensions.");
            // Provide a default fallback size
            this.viewportWidth = 800;
            this.viewportHeight = 600;
        }
    }

    public void attachToEntity(Transform targetTransform) {
        this.target = targetTransform;

    }

    public void setTileManager(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    public void update(double dt) {
        if (tileManager != null && target != null) {
            // Get the target's position
            Vector2D targetPosition = target.getPosition();

            // Calculate the centered camera position
            float centeredX = targetPosition.getX() - viewportWidth / 2 + (GamePanel.TILE_SIZE / 2);
            float centeredY = targetPosition.getY() - viewportHeight / 2 + (GamePanel.TILE_SIZE / 2);

            // Get the map dimensions in pixels
            float mapWidth = tileManager.getMapWidth() * GamePanel.TILE_SIZE;
            float mapHeight = tileManager.getMapHeight() * GamePanel.TILE_SIZE;

            // Clamp the camera position to prevent it from going out of bounds
            float clampedX = Math.max(0, Math.min(centeredX, mapWidth - viewportWidth));
            float clampedY = Math.max(0, Math.min(centeredY, mapHeight - viewportHeight));

            // Update the camera's position
            this.transform.setPosition(new Vector2D(clampedX, clampedY));

        }
    }

    /*
        public void update(double dt) {
            if (target != null) {
                // Center the camera on the target
                Vector2D targetPosition = target.getPosition();
                float centeredX = targetPosition.getX() - viewportWidth / 2;
                float centeredY = targetPosition.getY() - viewportHeight / 2;

                this.transform.setPosition(new Vector2D(centeredX, centeredY));
            }
            System.out.println(viewportWidth + "," + viewportHeight);
            System.out.println("Camera position: " + transform.getPosition());
        }
    */
    public Vector2D getWorldToScreenCoordinates(Vector2D worldPosition) {
        return worldPosition.subtract(transform.getPosition());
    }

    public Vector2D getScreenToWorldCoordinates(Vector2D screenPosition) {
        return screenPosition.add(transform.getPosition());
    }

    public void render(Graphics2D g2) {
        // Translate the Graphics context for rendering
        Vector2D cameraOffset = transform.getPosition();
        g2.translate(-cameraOffset.getX(), -cameraOffset.getY());

        renderManager.render(g2);

        // Reset translation after rendering
        g2.translate(cameraOffset.getX(), cameraOffset.getY());
    }

    @Override
    public Transform getTransform() {
        return transform;
    }
}
