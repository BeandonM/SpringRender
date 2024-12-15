package springrender.engine.core;

import java.awt.Shape;

public class DynamicBoxCollider implements DynamicCollider {

    @Override
    public Shape getBoundingBox() {
        return null;
    }

    @Override
    public boolean isColliding(Collider other) {
        return false;
    }

    @Override
    public void onCollisionEnter(Collider other) {
        DynamicCollider.super.onCollisionEnter(other);
    }

    @Override
    public void onCollisionExit(Collider other) {
        DynamicCollider.super.onCollisionExit(other);
    }
}
