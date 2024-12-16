package springrender.engine.core;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2DTest {
    @Test
    void testAddition() {
        Vector2D v1 = new Vector2D(2, 3);
        Vector2D v2 = new Vector2D(1, 1);
        Vector2D result = v1.add(v2);

        assertEquals(3, result.getX(), "X component of addition is incorrect");
        assertEquals(4, result.getY(), "Y component of addition is incorrect");
    }

    @Test
    void testSubtraction() {
        Vector2D v1 = new Vector2D(5, 5);
        Vector2D v2 = new Vector2D(2, 3);
        Vector2D result = v1.subtract(v2);

        assertEquals(3, result.getX(), "X component of subtraction is incorrect");
        assertEquals(2, result.getY(), "Y component of subtraction is incorrect");
    }

    @Test
    void testNormalization() {
        Vector2D v = new Vector2D(3, 4);
        Vector2D normalized = v.normalize();

        assertEquals(1.0, normalized.getMagnitude(), 1e-6, "Magnitude of normalized vector should be 1");
        assertEquals(0.6, normalized.getX(), 1e-6, "X component of normalized vector is incorrect");
        assertEquals(0.8, normalized.getY(), 1e-6, "Y component of normalized vector is incorrect");
    }

    @Test
    void testDotProduct() {
        Vector2D v1 = new Vector2D(1, 2);
        Vector2D v2 = new Vector2D(3, 4);
        float dot = v1.dotProduct(v2);

        assertEquals(11, dot, "Dot product calculation is incorrect");
    }

    @Test
    void testDistance() {
        Vector2D v1 = new Vector2D(0, 0);
        Vector2D v2 = new Vector2D(3, 4);
        float distance = Vector2D.distance(v1, v2);

        assertEquals(5, distance, 1e-6, "Distance calculation is incorrect");
    }

    @Test
    void testRotation() {
        Vector2D v = new Vector2D(1, 0);
        Vector2D rotated = v.rotate((float) Math.PI / 2); // 90 degrees

        assertEquals(0, rotated.getX(), 1e-6, "X component of rotated vector is incorrect");
        assertEquals(1, rotated.getY(), 1e-6, "Y component of rotated vector is incorrect");
    }

    @Test
    void testDivisionByZero() {
        Vector2D v = new Vector2D(1, 1);
        assertThrows(IllegalArgumentException.class, () -> v.divide(0), "Division by zero should throw IllegalArgumentException");
    }

    @Test
    void testEquality() {
        Vector2D v1 = new Vector2D(1, 1);
        Vector2D v2 = new Vector2D(1, 1);
        Vector2D v3 = new Vector2D(0, 0);

        assertEquals(v1, v2, "Equal vectors should be considered equal");
        assertNotEquals(v1, v3, "Different vectors should not be considered equal");
    }
}
