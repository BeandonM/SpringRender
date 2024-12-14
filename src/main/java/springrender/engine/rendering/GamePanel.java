package springrender.engine.rendering;

import springrender.engine.game.Player;
import springrender.engine.graphics.Renderable;
import springrender.engine.core.UpdateManager;
import springrender.engine.input.InputHandler;
import springrender.engine.graphics.RenderManager;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GamePanel extends JPanel implements Runnable {

    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int SCALE = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    private static final int MAX_SCREEN_WIDTH_MULTI = 16;
    private static final int MAX_SCREEN_HEIGHT_MULTI = 12;
    private static final int DEFAULT_SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_WIDTH_MULTI;
    private static final int DEFAULT_SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_HEIGHT_MULTI;

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());

    private static final double DT = 1.0 / 60.0; // Fixed timestep (60 FPS)

    private transient Thread gameThread;
    private InputHandler inputHandler;

    public UpdateManager updateManager;

    private RenderManager renderManager;

    private List<Renderable> renderables;

    private Player player;

    public GamePanel() {
        this.setPreferredSize(new Dimension(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        inputHandler = new InputHandler(this);
        updateManager = new UpdateManager();
        renderManager = new RenderManager();
        renderables = new ArrayList<>();

        player = new Player(this, inputHandler);
        updateManager.addUpdatable(player);

        renderManager.addRenderable(player);
        //addRenderable(player);
        startGameThread();
    }

    /**
     * Starts the game loop thread.
     */
    public void startGameThread() {
        gameThread = new Thread(this, "Game Thread");
        gameThread.start();
    }

    /**
     * The main game loop.
     */
    @Override
    public void run() {
        double currentTime = System.nanoTime() / 1e9;
        double accumulator = 0.0;

        long lastFPSCheck = System.nanoTime();
        int frameCount = 0;

        while (gameThread != null) {
            double newTime = System.nanoTime() / 1e9;
            double frameTime = newTime - currentTime;
            currentTime = newTime;

            // Prevent spiral of death by capping the frameTime
            if (frameTime > 0.25) {
                frameTime = 0.25;
            }

            accumulator += frameTime;

            // Update game logic as many times as necessary to catch up
            while (accumulator >= DT) {
                update(DT);
                accumulator -= DT;
            }

            double alpha = accumulator / DT;

            // Interpolate for rendering
            //player.interpolate(alpha);

            // Schedule a repaint on the EDT
            repaint();

            frameCount++;
            if ((System.nanoTime() - lastFPSCheck) >= 1_000_000_000L) {
                System.out.println("FPS: " + frameCount);
                frameCount = 0;
                lastFPSCheck = System.nanoTime();
            }

            // Sleep to prevent 100% CPU usage
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the game state. Delegated to the player.
     *
     * @param dt The fixed timestep in seconds.
     */
    public void update(double dt) {
        updateManager.updateAll(dt);
    }

    public void interpolate(double alpha) {
        for (Renderable renderable : renderables) {
            //interpolate
        }
    }

    public void addRenderable(Renderable renderable) {
        renderManager.addRenderable(renderable);
        //renderables.add(renderable);
        //Collections.sort(renderables, Comparator.comparingInt(Renderable::getLayer));
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
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);

        renderManager.render(g2);

        g2.dispose();
    }
}
