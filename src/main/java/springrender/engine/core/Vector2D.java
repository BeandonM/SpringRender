package springrender.engine.core;

import java.util.Objects;

/**
 * Represents a 2D vector with x and y components.
 */
public final class Vector2D {
    private final float x;
    private final float y;
    private transient Float cachedMagnitude = -1f;
    public static final Vector2D ZERO = new Vector2D(0f, 0f);
    public static final Vector2D ONE = new Vector2D(1f, 1f);
    public static final Vector2D UP = new Vector2D(0f, -1f);
    public static final Vector2D DOWN = new Vector2D(0f, 1f);
    public static final Vector2D LEFT = new Vector2D(-1f, 0f);
    public static final Vector2D RIGHT = new Vector2D(1f, 0f);

    /**
     * Constructs a zero vector (0,0).
     */
    public Vector2D() {
        this.x = 0f;
        this.y = 0f;
    }

    /**
     * Constructs a vector with specified x and y components.
     *
     * @param x The x-component.
     * @param y The y-component.
     */
    public Vector2D(int x, int y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    /**
     * Constructs a vector with specified x and y components.
     *
     * @param x The x-component.
     * @param y The y-component.
     */
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return The x-component of the vector.
     */
    public float getX() {
        return x;
    }

    /**
     * @return The y-component of the vector.
     */
    public float getY() {
        return y;
    }

    /**
     * Calculates the magnitude of the vector.
     *
     * @return The magnitude.
     */
    public float getMagnitude() {
        if (cachedMagnitude < 0f) {
            cachedMagnitude = (float) Math.sqrt(x * x + y * y);
        }
        return cachedMagnitude;
    }

    /**
     * Returns a normalized (unit length) vector.
     *
     * @return The normalized vector.
     */
    public Vector2D normalize() {
        float magnitude = getMagnitude();
        if (magnitude < 1e-6f) return ZERO;
        return new Vector2D(x / magnitude, y / magnitude);
    }

    /**
     * Adds this vector to another vector.
     *
     * @param other The vector to add.
     * @return A new Vector2D instance representing the sum.
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * Subtracts another vector from this vector.
     *
     * @param other The vector to subtract.
     * @return A new Vector2D instance representing the difference.
     */
    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     * Calculates the dot product of this vector with another.
     *
     * @param other The other vector.
     * @return The dot product.
     */
    public float dotProduct(Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     * Multiplies this vector by a scalar.
     *
     * @param scalar The scalar to multiply by.
     * @return A new Vector2D instance representing the scaled vector.
     */
    public Vector2D multiply(float scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Divides this vector by a scalar.
     *
     * @param scalar The scalar to divide by.
     * @return A new Vector2D instance representing the scaled vector.
     * @throws IllegalArgumentException If scalar is zero.
     */
    public Vector2D divide(float scalar) {
        if (scalar == 0) throw new IllegalArgumentException("Cannot divide by zero.");
        return new Vector2D(this.x / scalar, this.y / scalar);
    }

    public float angleBetween(Vector2D other) {
        float dot = this.dotProduct(other);
        float mags = this.getMagnitude() * other.getMagnitude();
        return (float) Math.acos(dot / mags);
    }

    /**
     * Rotates this vector by the specified angle in radians.
     *
     * @param angleRadians The angle to rotate by, in radians.
     * @return A new Vector2D instance representing the rotated vector.
     */
    public Vector2D rotate(float angleRadians) {
        float cos = (float) Math.cos(angleRadians);
        float sin = (float) Math.sin(angleRadians);
        return new Vector2D(
                x * cos - y * sin,
                x * sin + y * cos
        );
    }

    /**
     * Calculates the distance between this vector and another vector.
     *
     * @param other The other vector.
     * @return The Euclidean distance between the two vectors.
     */
    public float distanceTo(Vector2D other) {
        float deltaX = this.x - other.x;
        float deltaY = this.y - other.y;
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /**
     * Calculates the distance between two vectors.
     *
     * @param v1 The first vector.
     * @param v2 The second vector.
     * @return The Euclidean distance between v1 and v2.
     */
    public static float distance(Vector2D v1, Vector2D v2) {
        float deltaX = v1.x - v2.x;
        float deltaY = v1.y - v2.y;
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector2D)) return false;
        Vector2D other = (Vector2D) obj;
        return Float.compare(x, other.x) == 0 &&
                Float.compare(y, other.y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
