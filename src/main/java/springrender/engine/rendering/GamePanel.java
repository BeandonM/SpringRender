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

    private transient Thread gameThread;

    private int playerX = 100;
    private int playerY = 100;
    private int playerSpeed = 3;
    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());

    private InputHandler inputHandler;

    public GamePanel() {
        this.setPreferredSize(new Dimension(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        inputHandler = new InputHandler(this);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / 60;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            //logger.info("Thread");
            update();
            repaint();


            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);

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

        graphics2D.setColor(Color.white);

        graphics2D.fillRect(playerX, playerY, TILE_SIZE, TILE_SIZE);

        graphics2D.dispose();
    }
}
