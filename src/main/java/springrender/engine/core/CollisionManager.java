package springrender.engine.core;

import springrender.engine.rendering.GamePanel;

import java.awt.Rectangle;
import java.util.*;

public class CollisionManager {

    private Map<DynamicCollider, Set<Collider>> activeCollisions;

    private TileManager tileManager;

    private List<DynamicCollider> dynamicColliders;

    public CollisionManager(TileManager tileManager) {
        this.tileManager = tileManager;
        activeCollisions = new HashMap<>();
        dynamicColliders = new ArrayList<>();
    }


    public void addDynamicCollider(DynamicCollider collider) {
        dynamicColliders.add(collider);
    }


    public void removeDynamicCollider(DynamicCollider collider) {
        dynamicColliders.remove(collider);
    }


    public void checkCollisions() {
        for (DynamicCollider dynamicCollider : dynamicColliders) {
            Rectangle dynamicBounds = dynamicCollider.getBoundingBox().getBounds();

            int startCol = (int) (dynamicBounds.x / GamePanel.TILE_SIZE);
            int startRow = (int) (dynamicBounds.y / GamePanel.TILE_SIZE);
            int endCol = (int) ((dynamicBounds.x + dynamicBounds.width) / GamePanel.TILE_SIZE);
            int endRow = (int) ((dynamicBounds.y + dynamicBounds.height) / GamePanel.TILE_SIZE);

            for (int row = startRow; row <= endRow; row++) {
                for (int col = startCol; col <= endCol; col++) {
                    Tile tile = tileManager.getTileAt(row, col);
                    StaticBoxCollider tileCollider = new StaticBoxCollider(new Transform(new Vector2D(col * GamePanel.TILE_SIZE, row * GamePanel.TILE_SIZE)), GamePanel.TILE_SIZE, GamePanel.TILE_SIZE);
                    if (tile != null && tile.isCollidable()) {
                        Rectangle tileBounds = tileCollider.getBoundingBox().getBounds();
                        if (dynamicBounds.intersects(tileBounds)) {
                            dynamicCollider.resolveCollision(tileCollider);
                        }
                    }
                }
            }
        }
        /*
        Map<DynamicCollider, Set<Collider>> newCollisions = new HashMap<>();

        for (DynamicCollider dynamic : dynamicColliders) {
            Rectangle dynamicBoundingBox = dynamic.getBoundingBox().getBounds();
            if (tileManager.checkCollision(dynamicBoundingBox)) {
                dynamic.resolveCollision(tileManager);
            }

            for (StaticCollider staticCollider : staticColliders) {
                if (dynamic.isColliding(staticCollider)) {
                    newCollisions.
                            computeIfAbsent(dynamic, k -> new HashSet<>())
                            .add(staticCollider);

                }
                if (!activeCollisions.containsKey(dynamic) ||
                        !activeCollisions.get(dynamic).contains(staticCollider)) {
                    dynamic.onCollisionEnter(staticCollider);
                }

        }

        for (int i = 0; i < dynamicColliders.size(); i++) {
            for (int j = i + 1; j < dynamicColliders.size(); j++) {
                DynamicCollider a = dynamicColliders.get(i);
                DynamicCollider b = dynamicColliders.get(j);
                if (a.isColliding(b)) {
                    newCollisions
                            .computeIfAbsent(a, k -> new HashSet<>())
                            .add(b);
                    if (!activeCollisions.containsKey(a) ||
                            !activeCollisions.get(a).contains(b)) {
                        a.onCollisionEnter(b);
                    }
                }
            }
        }
        // Check for exited collisions
        for (DynamicCollider dynamic : activeCollisions.keySet()) {
            for (Collider other : activeCollisions.get(dynamic)) {
                if (!newCollisions.containsKey(dynamic) ||
                        !newCollisions.get(dynamic).contains(other)) {
                    dynamic.onCollisionExit(other);
                }
            }
        }

        // Update active collisions
        activeCollisions = newCollisions;
    }

             */
    }

    public boolean isValidMove(Rectangle futureBoundingBox) {
        int startCol = (int) (futureBoundingBox.x / GamePanel.TILE_SIZE);
        int startRow = (int) (futureBoundingBox.y / GamePanel.TILE_SIZE);
        int endCol = (int) ((futureBoundingBox.x + futureBoundingBox.width) / GamePanel.TILE_SIZE);
        int endRow = (int) ((futureBoundingBox.y + futureBoundingBox.height) / GamePanel.TILE_SIZE);

        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                Tile tile = tileManager.getTileAt(row, col);
                if (tile != null && tile.isCollidable()) {
                    Rectangle tileBounds = new Rectangle(
                            col * GamePanel.TILE_SIZE,
                            row * GamePanel.TILE_SIZE,
                            GamePanel.TILE_SIZE,
                            GamePanel.TILE_SIZE
                    );
                    if (futureBoundingBox.intersects(tileBounds)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
