package springrender.engine.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import springrender.engine.graphics.Sprite;
import springrender.engine.rendering.GamePanel;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileManager {
    GamePanel gamePanel;

    private TileLoader tileLoader;
    private MapLoader mapLoader;

    private Map<Integer, Tile> tileDefinitions;
    

    public TileManager(GamePanel gamePanel, String tileConfigPath, String mapPath) {
        this.gamePanel = gamePanel;
        this.tileDefinitions = new HashMap<>();
        tileLoader = new TileLoader();
        mapLoader = new MapLoader();

        InputStream tilesInputStream = getClass().getResourceAsStream(tileConfigPath);
        if (tilesInputStream == null) {
            throw new IllegalArgumentException("Tile file not found: " + tileConfigPath);
        }

        tileLoader.load(tilesInputStream);

        InputStream mapInputStream = getClass().getResourceAsStream(mapPath);
        if (mapInputStream == null) {
            throw new IllegalArgumentException("Map file not found: " + mapPath);
        }
        mapLoader.load(mapInputStream);

    }

    public void renderMapRelativeToCamera(Graphics2D g, Camera camera) {
        // Get camera position
        Vector2D cameraPosition = camera.getTransform().getPosition();
        float cameraX = cameraPosition.getX();
        float cameraY = cameraPosition.getY();

        // Calculate visible tiles based on camera position
        int startCol = Math.max(0, (int) (cameraX / GamePanel.TILE_SIZE));
        int startRow = Math.max(0, (int) (cameraY / GamePanel.TILE_SIZE));

        int endCol = Math.min(getMapWidth(), startCol + (int) (camera.viewportWidth / GamePanel.TILE_SIZE) + 1);
        int endRow = Math.min(getMapHeight(), startRow + (int) (camera.viewportHeight / GamePanel.TILE_SIZE) + 1);

        Tile[][] map = mapLoader.getMap();
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                Tile tile = map[row][col];
                if (tile != null) {
                    int x = col * GamePanel.TILE_SIZE;
                    int y = row * GamePanel.TILE_SIZE;
                    tile.draw(g, x, y);
                }
            }
        }
    }

    public int getMapWidth() {
        return mapLoader.getMap()[0].length;
        // return map[0].length; // Number of columns
    }

    public int getMapHeight() {
        return mapLoader.getMap().length;
        // return map.length; // Number of rows
    }

    public Tile getTileAt(int row, int col) {
        if (row < 0 || col < 0 || row >= mapLoader.getMap().length || col >= mapLoader.getMap()[0].length) {
            return null; // Out of bounds
        }
        return mapLoader.getMap()[row][col];
    }
}
