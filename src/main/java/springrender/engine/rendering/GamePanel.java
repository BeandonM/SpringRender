package springrender.engine.rendering;

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

    private static final Logger logger = Logger.getLogger(GamePanel.class.getName());

    public GamePanel() {
        this.setPreferredSize(new Dimension(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            //logger.info("Thread");
            update();
            repaint();
        }
    }

    public void update() {

    }

    @Override
    public void paintComponent(Graphics graphic) {
        super.paintComponent(graphic);

        Graphics2D graphics2D = (Graphics2D) graphic;

        graphics2D.setColor(Color.white);

        graphics2D.fillRect(100, 100, TILE_SIZE, TILE_SIZE);

        graphics2D.dispose();
    }
}
