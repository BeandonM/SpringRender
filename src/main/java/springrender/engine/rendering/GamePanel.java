package springrender.engine.rendering;


import springrender.engine.core.Camera;
import springrender.engine.core.TileManager;
import springrender.engine.core.Vector2D;
import springrender.engine.graphics.RenderManager;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.logging.Logger;

public class GamePanel extends JPanel {

    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int SCALE = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;

    private int width;

    private int height;

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());

    private transient RenderManager renderManager;

    private transient TileManager tileManager;

    private Camera camera;


    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRenderManager(RenderManager renderManager) {
        this.renderManager = renderManager;
    }

    public void setTileManager(TileManager tileManager) {
        this.tileManager = tileManager;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Renders the game components.
     *
     * @param graphic The graphics context.
     */
    @Override
    protected void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);

        Graphics2D g2 = (Graphics2D) graphic;

        // Clear the screen
        g2.setColor(getBackground());
        g2.fillRect(0, 0, width, height);

        if (camera != null) {
            Vector2D cameraOffset = camera.getWorldToScreenCoordinates(Vector2D.ZERO);
            g2.translate(-cameraOffset.getX(), -cameraOffset.getY());
            if (tileManager != null) {
                tileManager.renderMapRelativeToCamera(g2, camera);
            }
            // Render other game objects through the camera
            if (renderManager != null) {
                renderManager.render(g2);
            }
            g2.translate(cameraOffset.getX(), cameraOffset.getY());
        } else {
            if (renderManager != null) {
                renderManager.render(g2);
            }
        }


        g2.dispose();
    }
}
