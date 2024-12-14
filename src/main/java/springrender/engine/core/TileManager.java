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

    private Map<Integer, Tile> tileDefinitions;

    private Tile[][] map;

    // Sprite tileSpriteSheet;
    //Tile[] tile;

    public TileManager(GamePanel gamePanel, String tileConfigPath, String mapPath) {
        this.gamePanel = gamePanel;
        this.tileDefinitions = new HashMap<>();
        // Load tile definitions
        InputStream tilesInputStream = getClass().getResourceAsStream(tileConfigPath);
        if (tilesInputStream == null) {
            throw new IllegalArgumentException("Tile file not found: " + tileConfigPath);
        }

        // Load map layout
        InputStream mapInputStream = getClass().getResourceAsStream(mapPath);
        if (mapInputStream == null) {
            throw new IllegalArgumentException("Map file not found: " + mapPath);
        }
        loadTileDefinitions(tilesInputStream);
        loadMap(mapInputStream);

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

    public void renderMap(Graphics2D g) {
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
/*
    public void initializeTileSprite() {
        tileSpriteSheet = new Sprite();
        tileSpriteSheet.loadImage("grass", "/images/player/grass_tile.png");
        tileSpriteSheet.loadImage("wall", "/images/player/wall_tile.png");
    }

 */

}
