package springrender.engine.core;

import springrender.engine.rendering.GamePanel;
import springrender.engine.rendering.GameWindow;
import springrender.engine.utils.GamePanelFactory;
import springrender.engine.utils.GameWindowFactory;

public class GameApplication {
    private final GameWindow gameWindow;
    private final GamePanel gamePanel;
    private final GameManager gameManager;
    private final TileManager tileManager;

    public GameApplication(int screenWidth, int screenHeight, String tileConfig, String mapPath) {
        GamePanelFactory gamePanelFactory = new GamePanelFactory().setSize(screenWidth, screenHeight);
        this.gamePanel = gamePanelFactory.create();

        GameWindowFactory gameWindowFactory = new GameWindowFactory()
                .setTitle("Game")
                .setResizable(true)
                .setSize(screenWidth, screenHeight)
                .setGamePanel(gamePanel);
        this.gameWindow = gameWindowFactory.create();


        this.tileManager = new TileManager(gamePanel, tileConfig, mapPath);
        this.gameManager = new GameManager(gameWindow, gamePanel, tileManager);
        gamePanel.setTileManager(tileManager);
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public GamePanel getGamePanel() {
        return gamePanel;
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }
}
