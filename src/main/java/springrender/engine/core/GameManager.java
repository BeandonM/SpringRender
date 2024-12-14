package springrender.engine.core;

import springrender.engine.graphics.RenderManager;
import springrender.engine.graphics.Renderable;
import springrender.engine.graphics.Renderer;
import springrender.engine.input.InputManager;
import springrender.engine.rendering.GamePanel;
import springrender.engine.rendering.GameWindow;

public class GameManager implements Updatable, Renderer {

    private transient GameThread gameThread;

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
        gameThread = new GameSingleThreaded(this, gamePanel);
        gameThread.start();
    }

    public void stopGame() {
        gameThread.stop();
    }

    public GameThread getGameThread() {
        return gameThread;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
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

    public void interpolate(double alpha) {
        //for (Renderable renderable : renderManager) {
        //interpolate
        //}
    }

    @Override
    public void update(double deltaTime) {
        updateManager.updateAll(deltaTime);
    }

    @Override
    public void render() {
        gamePanel.repaint();
    }
}