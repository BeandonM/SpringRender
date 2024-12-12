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

    // Fixed time step (in seconds) for updates. For 60 updates/sec: 1/60 ≈ 0.0166667s
    private static final double DT = 1.0 / 60.0;

    private transient Thread gameThread;
    private InputHandler inputHandler;

    // Simple player position and movement
    private int playerX = 100;
    private int playerY = 100;
    private int playerSpeed = 1;

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
        double t = 0.0;

        long lastFPSCheck = System.nanoTime();
        int frameCount = 0;

        while (gameThread != null) {
            double newTime = System.nanoTime() / 1e9;
            double frameTime = newTime - currentTime;
            currentTime = newTime;

            // Prevent 'spiral of death' if the game lags
            if (frameTime > 0.25) {
                frameTime = 0.25;
            }

            accumulator += frameTime;

            while (accumulator >= DT) {
                update();
                t += DT;
                accumulator -= DT;
            }

            repaint();
            frameCount++;

            if ((System.nanoTime() - lastFPSCheck) >= 1_000_000_000L) {
                System.out.println("FPS: " + frameCount);
                frameCount = 0;
                lastFPSCheck = System.nanoTime();
            }
        }
    }

    public void update() {
        if (inputHandler.isUpPressed()) playerY -= playerSpeed;
        if (inputHandler.isDownPressed()) playerY += playerSpeed;
        if (inputHandler.isLeftPressed()) playerX -= playerSpeed;
        if (inputHandler.isRightPressed()) playerX += playerSpeed;
    }

    @Override
    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);

        Graphics2D graphics2D = (Graphics2D) graphic;

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(playerX, playerY, TILE_SIZE, TILE_SIZE);

        graphics2D.dispose();
    }
}
