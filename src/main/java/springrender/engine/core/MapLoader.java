package springrender.engine.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

public class MapLoader implements ResourceLoader {
    private Tile[][] map;

    public void load(InputStream inputStream, Map<Integer, Tile> tileDefinitions) {
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

    public Tile[][] getMap() {
        return map;
    }

    @Override
    public void load(InputStream inputStream) {
        throw new UnsupportedOperationException("Use load(InputStream, Map<Integer, Tile>) instead");
    }
}