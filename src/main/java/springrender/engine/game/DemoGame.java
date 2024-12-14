package springrender.engine.game;

import springrender.engine.core.Camera;
import springrender.engine.core.GameManager;
import springrender.engine.core.TileManager;
import springrender.engine.rendering.GamePanel;
import springrender.engine.rendering.GameWindow;
import springrender.engine.utils.GamePanelFactory;
import springrender.engine.utils.GameWindowFactory;

public class DemoGame {
    private static final int ORIGINAL_TILE_SIZE = 16;
    private static final int SCALE = 3;
    public static final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE;
    private static final int MAX_SCREEN_WIDTH_MULTI = 16;
    private static final int MAX_SCREEN_HEIGHT_MULTI = 12;
    private static final int DEFAULT_SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_WIDTH_MULTI;
    private static final int DEFAULT_SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_HEIGHT_MULTI;

    public static void main(String[] args) {
        GameWindowFactory gameWindowFactory = new GameWindowFactory();
        gameWindowFactory.setTitle("Demo Game");
        gameWindowFactory.setResizable(true);
        gameWindowFactory.setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);

        GamePanelFactory gamePanelFactory = new GamePanelFactory();
        gamePanelFactory.setSize(DEFAULT_SCREEN_WIDTH, DEFAULT_SCREEN_HEIGHT);
        GamePanel gamePanel = gamePanelFactory.create();

        gameWindowFactory.setGamePanel(gamePanel);

        GameWindow gameWindow = gameWindowFactory.create();

        GameManager gameManager = new GameManager(gameWindow, gamePanel);

        TileManager tileManager = new TileManager(gamePanel, "/tiles/tiles.json", "/maps/map1.txt");

        gamePanel.setTileManager(tileManager);

        Player player = new Player(gameManager.getUpdateManager(), gameManager.getRenderManager(), gameManager.getInputManager().getInputHandler());

        Camera camera = new Camera(gamePanel, gameManager.getUpdateManager(), gameManager.getRenderManager());

        player.setCamera(camera);
        gamePanel.setCamera(camera);
        camera.attachToEntity(player.getTransform());
        camera.setTileManager(tileManager);

        gameManager.startGame();
    }
}
