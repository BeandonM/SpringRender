package springrender.engine.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import springrender.engine.graphics.Sprite;
import springrender.engine.rendering.GamePanel;

import java.awt.Graphics2D;
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


    // Sprite tileSpriteSheet;
    //Tile[] tile;

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

    public void loadTileDefinitions(InputStream inputStream) {
        try {
            String jsonString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(jsonString);
            JSONArray tiles = json.getJSONArray("tiles");

            for (int i = 0; i < tiles.length(); i++) {
                JSONObject tileJson = tiles.getJSONObject(i);
                int id = tileJson.getInt("id");
                String type = tileJson.getString("type");
                String spritePath = tileJson.getString("sprite");
                boolean isWalkable = tileJson.getBoolean("isWalkable");

                Sprite sprite = new Sprite();
                sprite.loadImage(type, spritePath);
                tileDefinitions.put(id, new Tile(sprite, type, isWalkable));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /*
    public void loadMap(InputStream inputStream) {
        try {
            List<String> lines = new BufferedReader(new InputStreamReader(inputStream)).lines().toList();
            int rows = lines.size();
            int cols = lines.get(0).split(" ").length;

            map = new Tile[rows][cols];

            for (int row = 0; row < rows; row++) {
                String[] tokens = lines.get(row).split(" ");
                for (int col = 0; col < tokens.length; col++) {
                    int tileId = Integer.parseInt(tokens[col]);
                    map[row][col] = tileDefinitions.get(tileId);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/

    /**
     * Renders the map relative to the camera's position.
     *
     * @param g2     The graphics context.
     * @param camera The camera to base rendering on.
     */
    /*
    public void renderMapRelativeToCamera(Graphics2D g2, Camera camera) {
        // Get camera position
        Vector2D cameraPosition = camera.getTransform().getPosition();
        float cameraX = cameraPosition.getX();
        float cameraY = cameraPosition.getY();

        // Calculate visible tiles based on camera position
        int startCol = Math.max(0, (int) (cameraX / GamePanel.TILE_SIZE));
        int startRow = Math.max(0, (int) (cameraY / GamePanel.TILE_SIZE));

        int endCol = Math.min(map[0].length, startCol + (int) (camera.viewportWidth / GamePanel.TILE_SIZE) + 1);
        int endRow = Math.min(map.length, startRow + (int) (camera.viewportHeight / GamePanel.TILE_SIZE) + 1);

        // Render visible tiles
        for (int row = startRow; row < endRow; row++) {
            for (int col = startCol; col < endCol; col++) {
                Tile tile = map[row][col];
                if (tile != null) {
                    int screenX = (col * GamePanel.TILE_SIZE) - (int) cameraX;
                    int screenY = (row * GamePanel.TILE_SIZE) - (int) cameraY;
                    tile.draw(g2, screenX, screenY);
                }
            }
        }
    }

     */
    public void renderMap(Graphics2D g) {
        Tile[][] map = mapLoader.getMap();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
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

}
