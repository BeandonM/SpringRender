package springrender.engine.core;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import springrender.engine.graphics.Sprite;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TileLoader implements ResourceLoader {
    private Map<Integer, Tile> tileDefinitions;

    public TileLoader() {
        tileDefinitions = new HashMap<>();
    }

    @Override
    public void load(InputStream inputStream) {
        try {
            String jsonString = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            JSONObject json = new JSONObject(jsonString);
            JSONArray tiles = json.getJSONArray("tiles");

            for (int i = 0; i < tiles.length(); i++) {
                JSONObject tileJson = tiles.getJSONObject(i);
                int id = tileJson.getInt("id");
                String type = tileJson.getString("type");
                String spritePath = tileJson.getString("sprite");
                boolean collision = tileJson.getBoolean("collision");

                Sprite sprite = new Sprite();
                sprite.loadImage(type, spritePath);
                tileDefinitions.put(id, new Tile(sprite, type, collision));
            }

            ResourceManager.getInstance().addResource("tileDefinitions", tileDefinitions);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Tile> getTileDefinitions() {
        return tileDefinitions;
    }
}