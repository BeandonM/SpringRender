package springrender.engine.rendering;

import springrender.engine.input.InputHandler;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Logger;

public class GamePanel extends JPanel implements Runnable {

    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int SCALE = 3;
    private static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    private static final int MAX_SCREEN_WIDTH_MULTI = 16;
    private static final int MAX_SCREEN_HEIGHT_MULTI = 12;
    private static final int DEFAULT_SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_WIDTH_MULTI;
    private static final int DEFAULT_SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_HEIGHT_MULTI;

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());

    private static final double DT = 1.0 / 60.0; // fixed timestep

    private transient Thread gameThread;
    private InputHandler inputHandler;

    // Player states (current and previous)
    private double currentPlayerX = 100;
    private double currentPlayerY = 100;
    private double previousPlayerX = 100;
    private double previousPlayerY = 100;

    private double renderX = 100;
    private double renderY = 100;

    private int playerSpeed = 60; // pixels per second, for example

    public GamePanel() {
        this.setPreferredSize(new Dimension(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        inputHandler = new InputHandler(this);
        startGameThread();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

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

            // Prevent spiral of death
            if (frameTime > 0.25) {
                frameTime = 0.25;
            }

            accumulator += frameTime;

            // Update as many steps as needed
            while (accumulator >= DT) {
                // Move current state into previous before the next logic update
                previousPlayerX = currentPlayerX;
                previousPlayerY = currentPlayerY;

                // Update game logic at a fixed timestep
                update(DT);
                accumulator -= DT;
            }

            double alpha = accumulator / DT;

            // Interpolate for rendering
            renderX = previousPlayerX * (1.0 - alpha) + currentPlayerX * alpha;
            renderY = previousPlayerY * (1.0 - alpha) + currentPlayerY * alpha;

            // Schedule a repaint on the EDT
            repaint();

            frameCount++;
            if ((System.nanoTime() - lastFPSCheck) >= 1_000_000_000L) {
                System.out.println("FPS: " + frameCount);
                frameCount = 0;
                lastFPSCheck = System.nanoTime();
            }
        }
    }

    public void update(double dt) {
        // dt is in seconds, so speed * dt is how many pixels per update
        double moveAmount = playerSpeed * dt;

        if (inputHandler.isUpPressed()) currentPlayerY -= moveAmount;
        if (inputHandler.isDownPressed()) currentPlayerY += moveAmount;
        if (inputHandler.isLeftPressed()) currentPlayerX -= moveAmount;
        if (inputHandler.isRightPressed()) currentPlayerX += moveAmount;
    }

    @Override
    protected void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);
        Graphics2D g2 = (Graphics2D) graphic;

        // Clear the screen
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);

        // Draw the player
        g2.setColor(Color.WHITE);
        g2.fillRect((int) renderX, (int) renderY, TILE_SIZE, TILE_SIZE);
    }
}
