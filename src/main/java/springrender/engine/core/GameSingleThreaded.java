package springrender.engine.core;

import springrender.engine.rendering.GamePanel;

public class GameSingleThreaded implements GameThread {
    private static final double TARGET_FPS = 60.0;
    private static final double TIME_PER_FRAME = 1.0 / TARGET_FPS;

    private volatile boolean running;

    private GameManager gameManager;

    private GamePanel gamePanel;

    public GameSingleThreaded(GameManager gameManager, GamePanel gamePanel) {
        this.gameManager = gameManager;
        this.gamePanel = gamePanel;
    }

    @Override
    public void start() {
        running = true;
        Thread gameThread = new Thread(this, "GameSingleThreaded");
        gameThread.start();
    }

    @Override
    public void stop() {
        running = false;
    }

    @Override
    public void update(double deltaTime) {
        //gameManager.update(deltaTime);
    }

    @Override
    public void render() {
        gamePanel.repaint();
    }

    @Override
    public void run() {

    }
}
