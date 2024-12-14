package springrender.engine.core;

import springrender.engine.graphics.RenderManager;
import springrender.engine.rendering.GamePanel;

import java.awt.Graphics2D;

public class Camera implements Updatable {

    private GamePanel gamePanel;

    private UpdateManager updateManager;
    private RenderManager renderManager;

    private Transform transform;
    private Transform target;

    private float viewportWidth;
    private float viewportHeight;

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

    public void update(double dt) {
        if (target != null) {
            // Center the camera on the target
            Vector2D targetPosition = target.getPosition();
            float centeredX = targetPosition.getX() - viewportWidth / 2;
            float centeredY = targetPosition.getY() - viewportHeight / 2;

            this.transform.setPosition(new Vector2D(centeredX, centeredY));
        }
    }

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

    public Transform getTransform() {
        return transform;
    }
}
