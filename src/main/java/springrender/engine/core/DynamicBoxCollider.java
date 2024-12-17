package springrender.engine.core;

import java.awt.Rectangle;
import java.awt.Shape;

public class DynamicBoxCollider implements DynamicCollider, Transformable {

    private Transform transform;
    private int width;
    private int height;

    private Shape boundingBox;

    public DynamicBoxCollider(Transform transform, int width, int height) {
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
        //Basic implementation, just converts the shape to a rect, I do plan on adding intersections between other shapes
        return getBoundingBox().getBounds2D().intersects(other.getBoundingBox().getBounds2D());
        //TODO Handle intersections with other shapes
        /**
         * Some starting implementation I have.
         * if( getBoundingBox().getBounds().intersects(other.getBoundingBox().getBounds()) ){
         *     Area a = new Area(myShape);
         *     a.intersect(new Area(otherShape));
         *     return !a.isEmpty();
         * }
         */
        //if (getBoundingBox().getBounds().intersects(other.getBounds())) {
        //     return getBoundingBox().intersects(other.getBoundingBox())
        // }
    }

    @Override
    public void onCollisionEnter(Collider other) {
        System.out.println("Collision started with: " + other);
        //DynamicCollider.super.onCollisionEnter(other);
    }

    @Override
    public void onCollisionExit(Collider other) {

        System.out.println("Collision ended with: " + other);
        //DynamicCollider.super.onCollisionExit(other);
    }

    @Override
    public Transform getTransform() {
        return transform;
    }

    @Override
    public void resolveCollision(Collider other) {
        if (other instanceof StaticCollider staticCollider) {
            Rectangle playerBoundingBox = getBoundingBox().getBounds();

        }
    }
}
