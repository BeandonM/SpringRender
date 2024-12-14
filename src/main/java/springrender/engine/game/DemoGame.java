package springrender.engine.game;

import springrender.engine.rendering.GamePanel;
import springrender.engine.rendering.GameWindow;
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


        GameWindow gameWindow = gameWindowFactory.create(new GamePanel());
    }
}
