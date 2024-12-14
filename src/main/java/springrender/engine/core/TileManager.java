package springrender.engine.core;

import springrender.engine.graphics.Sprite;
import springrender.engine.rendering.GamePanel;

public class TileManager {
    GamePanel gamePanel;

    Sprite tileSpriteSheet;
    Tile[] tile;

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tile = new Tile[10];

        initializeTileSprite();

        tile[0] = new Tile(tileSpriteSheet, "grass");
        gamePanel.addRenderable(tile[0]);
    }

    public void initializeTileSprite() {
        tileSpriteSheet = new Sprite();
        tileSpriteSheet.loadImage("grass", "/images/player/grass_tile.png");
        tileSpriteSheet.loadImage("wall", "/images/player/wall_tile.png");
    }

}
