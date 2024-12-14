package springrender.engine.core;

import springrender.engine.graphics.Sprite;
import springrender.engine.rendering.GamePanel;

import java.util.HashMap;
import java.util.Map;

public class TileManager {
    GamePanel gamePanel;

    private Map<Integer, Tile> tileDefinitions;

    private Tile[][] map;

    // Sprite tileSpriteSheet;
    //Tile[] tile;

    public TileManager(GamePanel gamePanel, String tileConfigPath, String mapPath) {
        this.gamePanel = gamePanel;
        this.tileDefinitions = new HashMap<>();
        loadTileDefinitions(tileConfigPath);
    }

    public void loadTileDefinitions(String filePath) {

    }
/*
    public void initializeTileSprite() {
        tileSpriteSheet = new Sprite();
        tileSpriteSheet.loadImage("grass", "/images/player/grass_tile.png");
        tileSpriteSheet.loadImage("wall", "/images/player/wall_tile.png");
    }

 */

}
