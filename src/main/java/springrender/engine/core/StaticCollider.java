package springrender.engine.core;

import java.awt.Rectangle;

public interface StaticCollider extends Collider {
    Rectangle getBoundingBoxAt(int x, int y); // Calculate the bounding box for a specific tile

    boolean isCollidable(int x, int y); // Check if a tile is walkable
}
