package springrender.engine.core;

public class Vector2D {
    private float positionX;
    private float positionY;


    public Vector2D() {
        positionX = 0f;
        positionY = 0f;
    }

    public Vector2D(float x, float y) {
        positionX = x;
        positionY = y;
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }

    public static Vector2D zeroVector() {
        return new Vector2D(0f, 0f);
    }

    public static Vector2D oneVector() {
        return new Vector2D(1f, 1f);
    }

    public static Vector2D upVector() {
        return new Vector2D(0f, -1f);
    }

    public static Vector2D downVector() {
        return new Vector2D(0f, 1f);
    }

    public static Vector2D leftVector() {
        return new Vector2D(-1f, 0f);
    }

    public static Vector2D rightVector() {
        return new Vector2D(1f, 0f);
    }
}
