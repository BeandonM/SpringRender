package springrender.engine.core;

import java.awt.Rectangle;
import java.awt.Shape;

interface Collider {
    Shape getBoundingBox();

    boolean isColliding(Collider other);

    // Collision event methods
    default void onCollisionEnter(Collider other) {
    }

    default void onCollisionExit(Collider other) {
    }
}
