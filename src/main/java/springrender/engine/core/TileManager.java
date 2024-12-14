package springrender.engine.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import springrender.engine.graphics.Sprite;
import springrender.engine.rendering.GamePanel;

import java.io.IOException;
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
        loadTileDefinitions(tileConfigPath);
        loadMap(mapPath);
    }

    public void loadTileDefinitions(String filePath) {
        try {
            String jsonString = Files.readString(Path.of(filePath));
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

    public void loadMap(String filepath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filepath));
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

        } catch (IOException e) {
            e.printStackTrace();
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
