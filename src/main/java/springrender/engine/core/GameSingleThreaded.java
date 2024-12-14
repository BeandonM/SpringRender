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
        gameManager.update(deltaTime);
    }

    @Override
    public void render() {
        gamePanel.repaint();
    }

    @Override
    public void run() {
        double previousTime = System.nanoTime() / 1e9;
        double accumulator = 0.0;
        int frameCount = 0;
        long lastFPSCheck = System.nanoTime();
        while (running) {
            double currentTime = System.nanoTime() / 1e9;
            double deltaTime = currentTime - previousTime;
            previousTime = currentTime;

            accumulator += deltaTime;

            while (accumulator >= TIME_PER_FRAME) {
                update(TIME_PER_FRAME);
                accumulator -= TIME_PER_FRAME;
            }

            //double alpha = accumulator / DT;
            //interpolate(alpha);

            frameCount++;
            render();
            if ((System.nanoTime() - lastFPSCheck) >= 1_000_000_000L) {
                System.out.println("FPS: " + frameCount);
                frameCount = 0;
                lastFPSCheck = System.nanoTime();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}