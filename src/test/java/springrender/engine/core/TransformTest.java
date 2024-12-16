package springrender.engine.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformTest {

    @Test
    void testDefaultConstructor() {
        Transform transform = new Transform();

        assertEquals(Vector2D.ZERO, transform.getPosition(), "Default position should be ZERO");
        assertEquals(0f, transform.getRotation(), "Default rotation should be 0 degrees");
        assertEquals(Vector2D.ONE, transform.getScale(), "Default scale should be ONE");
    }

    @Test
    void testConstructorWithPosition() {
        Vector2D position = new Vector2D(10, 20);
        Transform transform = new Transform(position);

        assertEquals(position, transform.getPosition(), "Position should match constructor argument");
        assertEquals(0f, transform.getRotation(), "Rotation should default to 0 degrees");
        assertEquals(Vector2D.ONE, transform.getScale(), "Scale should default to ONE");
    }

    @Test
    void testConstructorWithPositionAndRotation() {
        Vector2D position = new Vector2D(15, 25);
        float rotation = 45f;
        Transform transform = new Transform(position, rotation);

        assertEquals(position, transform.getPosition(), "Position should match constructor argument");
        assertEquals(rotation, transform.getRotation(), "Rotation should match constructor argument");
        assertEquals(Vector2D.ONE, transform.getScale(), "Scale should default to ONE");
    }

    @Test
    void testConstructorWithAllParameters() {
        Vector2D position = new Vector2D(5, 10);
        float rotation = 90f;
        Vector2D scale = new Vector2D(2, 3);
        Transform transform = new Transform(position, rotation, scale);

        assertEquals(position, transform.getPosition(), "Position should match constructor argument");
        assertEquals(rotation, transform.getRotation(), "Rotation should match constructor argument");
        assertEquals(scale, transform.getScale(), "Scale should match constructor argument");
    }

    @Test
    void testSetPosition() {
        Transform transform = new Transform();
        Vector2D newPosition = new Vector2D(30, 40);
        transform.setPosition(newPosition);

        assertEquals(newPosition, transform.getPosition(), "Position should update correctly");
    }

    @Test
    void testTranslate() {
        Transform transform = new Transform(new Vector2D(10, 20));
        Vector2D translation = new Vector2D(5, -5);
        transform.translate(translation);

        assertEquals(new Vector2D(15, 15), transform.getPosition(), "Position should update after translation");
    }

    @Test
    void testSetRotation() {
        Transform transform = new Transform();
        transform.setRotation(370f);

        assertEquals(10f, transform.getRotation(), "Rotation should normalize to 10 degrees");

        transform.setRotation(-90f);
        assertEquals(270f, transform.getRotation(), "Rotation should normalize to 270 degrees");
    }

    @Test
    void testRotate() {
        Transform transform = new Transform();
        transform.rotate(45f);

        assertEquals(45f, transform.getRotation(), "Rotation should increment correctly");

        transform.rotate(400f);
        assertEquals(85f, transform.getRotation(), "Rotation should normalize correctly");
    }

    @Test
    void testSetScale() {
        Transform transform = new Transform();
        Vector2D newScale = new Vector2D(2, 3);
        transform.setScale(newScale);

        assertEquals(newScale, transform.getScale(), "Scale should update correctly");
    }

    @Test
    void testScale() {
        Transform transform = new Transform(new Vector2D(0, 0), 0f, new Vector2D(2, 2));
        Vector2D scaling = new Vector2D(1.5f, 0.5f);
        transform.scale(scaling);

        assertEquals(new Vector2D(3, 1), transform.getScale(), "Scale should multiply correctly");
    }

    @Test
    void testReset() {
        Transform transform = new Transform(new Vector2D(10, 20), 45f, new Vector2D(3, 4));
        transform.reset();

        assertEquals(Vector2D.ZERO, transform.getPosition(), "Position should reset to ZERO");
        assertEquals(0f, transform.getRotation(), "Rotation should reset to 0 degrees");
        assertEquals(Vector2D.ONE, transform.getScale(), "Scale should reset to ONE");
    }
}
