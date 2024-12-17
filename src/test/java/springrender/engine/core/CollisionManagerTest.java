package springrender.engine.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;

import static org.mockito.Mockito.*;

class CollisionManagerTest {
    private CollisionManager collisionManager;
    private TileManager tileManager;

    @BeforeEach
    void setUP() {
        tileManager = mock(TileManager.class);
        collisionManager = new CollisionManager(tileManager);
    }

    @Test
    void testAddDynamicCollider() {
        DynamicCollider dynamicCollider = mock(DynamicCollider.class);

        when(dynamicCollider.getBoundingBox()).thenReturn(new Rectangle(10, 10, 50, 50));

        collisionManager.addDynamicCollider(dynamicCollider);

        collisionManager.checkCollisions();

        verify(dynamicCollider, atLeastOnce()).getBoundingBox();
    }

    @Test
    void testTileCollisions() {
        // Mock a DynamicCollider
        DynamicCollider dynamicCollider = mock(DynamicBoxCollider.class);

        // Stub the bounding box of the dynamic collider
        Rectangle boundingBox = new Rectangle(10, 10, 50, 50);
        when(dynamicCollider.getBoundingBox()).thenReturn(boundingBox);

        // Stub TileManager to simulate tile presence and collision
        when(tileManager.getTileAt(anyInt(), anyInt())).thenAnswer(invocation -> {
            int row = invocation.getArgument(0);
            int col = invocation.getArgument(1);
            if (row == 0 && col == 0) {
                Tile tile = mock(Tile.class);
                when(tile.isCollidable()).thenReturn(true);
                return tile;
            }
            return null;
        });

        when(dynamicCollider.isColliding(any(Collider.class))).thenReturn(true);
        // Add the dynamic collider to the CollisionManager
        collisionManager.addDynamicCollider(dynamicCollider);

        // Call checkCollisions to trigger the collision check
        collisionManager.checkCollisions();

        // Verify that the dynamic collider's resolveCollision method was called
        verify(dynamicCollider).resolveCollision(any(Collider.class));
    }
}
