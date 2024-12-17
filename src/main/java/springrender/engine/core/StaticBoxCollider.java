package springrender.engine.core;

import java.awt.Rectangle;
import java.awt.Shape;

public class StaticBoxCollider implements StaticCollider {
    private Transform transform;
    private int width;
    private int height;

    private Shape boundingBox;

    public StaticBoxCollider(Transform transform, int width, int height) {
        this.transform = transform;
        this.width = width;
        this.height = height;
    }

    @Override
    public Shape getBoundingBox() {
        if (boundingBox == null) {
            boundingBox = new Rectangle(
                    (int) transform.getPosition().getX(),
                    (int) transform.getPosition().getY(),
                    width,
                    height
            );
        }
        return boundingBox;
    }

    @Override
    public boolean isColliding(Collider other) {
        return false;
    }

    @Override
    public Rectangle getBoundingBoxAt(int x, int y) {
        return null;
    }

    @Override
    public boolean isCollidable(int x, int y) {
        return false;
    }
}
