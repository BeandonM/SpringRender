package springrender.engine.rendering;


import springrender.engine.graphics.RenderManager;

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.util.logging.Logger;

public class GamePanel extends JPanel {

    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int SCALE = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    private static final int MAX_SCREEN_WIDTH_MULTI = 16;
    private static final int MAX_SCREEN_HEIGHT_MULTI = 12;
    private static final int DEFAULT_SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_WIDTH_MULTI;
    private static final int DEFAULT_SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_HEIGHT_MULTI;

    private int width;

    private int height;

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());

    private transient RenderManager renderManager;


    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRenderManager(RenderManager renderManager) {
        this.renderManager = renderManager;
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

        renderManager.render(g2);

        g2.dispose();
    }
}
