package springrender.engine.core;

import org.junit.jupiter.api.Test;

import java.awt.Rectangle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class DynamicBoxColliderTest {
    @Test
    void testGetBoundingBox() {
        Transform transform = new Transform(new Vector2D(10, 20));
        DynamicBoxCollider collider = new DynamicBoxCollider(transform, 50, 100);

        Rectangle boundingBox = (Rectangle) collider.getBoundingBox();
        assertEquals(10, boundingBox.getX(), "Bounding box X-coordinate is incorrect");
        assertEquals(20, boundingBox.getY(), "Bounding box Y-coordinate is incorrect");
        assertEquals(50, boundingBox.getWidth(), "Bounding box width is incorrect");
        assertEquals(100, boundingBox.getHeight(), "Bounding box height is incorrect");
    }

    @Test
    void testIsColliding() {
        Transform transform1 = new Transform(new Vector2D(0, 0));
        DynamicBoxCollider collider1 = new DynamicBoxCollider(transform1, 50, 50);

        Transform transform2 = new Transform(new Vector2D(25, 25));
        DynamicBoxCollider collider2 = new DynamicBoxCollider(transform2, 50, 50);

        assertTrue(collider1.isColliding(collider2), "Colliders should be colliding");

        Transform transform3 = new Transform(new Vector2D(100, 100));
        DynamicBoxCollider collider3 = new DynamicBoxCollider(transform3, 50, 50);

        assertFalse(collider1.isColliding(collider3), "Colliders should not be colliding");
    }

    @Test
    void testOnCollisionEnter() {
        DynamicBoxCollider collider = spy(new DynamicBoxCollider(new Transform(new Vector2D(0, 0)), 50, 50));
        Collider other = mock(Collider.class);

        collider.onCollisionEnter(other);
        verify(collider).onCollisionEnter(other);
    }

    @Test
    void testOnCollisionExit() {
        DynamicBoxCollider collider = spy(new DynamicBoxCollider(new Transform(new Vector2D(0, 0)), 50, 50));
        Collider other = mock(Collider.class);

        collider.onCollisionExit(other);
        verify(collider).onCollisionExit(other);
    }

    @Test
    void testResolveCollisionWithStaticCollider() {
        Transform transform = new Transform(new Vector2D(0, 0));
        DynamicBoxCollider dynamicCollider = new DynamicBoxCollider(transform, 50, 50);

        StaticCollider staticCollider = mock(StaticCollider.class);
        when(staticCollider.isColliding(dynamicCollider)).thenReturn(true);

        dynamicCollider.resolveCollision(staticCollider);
        // Verify collision handling logic if applicable
    }
}
