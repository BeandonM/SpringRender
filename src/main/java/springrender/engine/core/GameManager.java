package springrender.engine.core;

import springrender.engine.graphics.RenderManager;
import springrender.engine.input.InputManager;
import springrender.engine.rendering.GamePanel;
import springrender.engine.rendering.GameWindow;

public class GameManager implements Runnable {
    private static final double DT = 1.0 / 60.0;

    private transient Thread gameThread;

    private UpdateManager updateManager;
    private RenderManager renderManager;
    private InputManager inputManager;
    private GamePanel gamePanel;

    private GameWindow gameWindow;

    public GameManager(GameWindow gameWindow, GamePanel gamePanel) {
        this.gameWindow = gameWindow;
        this.gamePanel = gamePanel;
        this.updateManager = new UpdateManager();
        this.renderManager = new RenderManager();
        this.inputManager = new InputManager(gamePanel);
        gamePanel.setRenderManager(this.renderManager);
    }

    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stopGame() {
        gameThread = null;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public UpdateManager getUpdateManager() {
        return updateManager;
    }

    public RenderManager getRenderManager() {
        return renderManager;
    }

    @Override
    public void run() {
        double currentTime = System.nanoTime() / 1e9;
        double accumulator = 0.0;

        while (gameThread != null) {
            double newTime = System.nanoTime() / 1e9;
            double frameTime = newTime - currentTime;
            currentTime = newTime;

            if (frameTime > 0.25) frameTime = 0.25;
            accumulator += frameTime;

            while (accumulator >= DT) {
                updateManager.updateAll(DT);
                accumulator -= DT;
            }

            gamePanel.repaint();

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}