package springrender.engine.core;

/**
 * Represents the spatial transformation of an entity in 2D space.
 * Includes position, rotation, and scale.
 */
public class Transform {
    private Vector2D position;
    private float rotation; // In degrees
    private Vector2D scale;

    /**
     * Constructs a Transform with default position (ZERO), rotation (0 degrees), and scale (ONE).
     */
    public Transform() {
        this.position = Vector2D.ZERO;
        this.rotation = 0f;
        this.scale = Vector2D.ONE;
    }

    /**
     * Constructs a Transform with specified position
     *
     * @param position The position vector.
     */
    public Transform(Vector2D position) {
        this.position = position != null ? position : Vector2D.ZERO;
        this.rotation = 0f;
        this.scale = Vector2D.ONE;
    }

    /**
     * Constructs a Transform with specified position, rotation
     *
     * @param position The position vector.
     * @param rotation The rotation in degrees.
     */
    public Transform(Vector2D position, float rotation) {
        this.position = position != null ? position : Vector2D.ZERO;
        this.rotation = rotation;
        this.scale = Vector2D.ONE;
    }

    /**
     * Constructs a Transform with specified position, rotation, and scale.
     *
     * @param position The position vector.
     * @param rotation The rotation in degrees.
     * @param scale    The scale vector.
     */
    public Transform(Vector2D position, float rotation, Vector2D scale) {
        this.position = position != null ? position : Vector2D.ZERO;
        this.rotation = rotation;
        this.scale = scale != null ? scale : Vector2D.ONE;
    }

    /**
     * Gets the position of the transform.
     *
     * @return The position vector.
     */
    public Vector2D getPosition() {
        return position;
    }

    /**
     * Sets the position of the transform.
     *
     * @param position The new position vector.
     */
    public void setPosition(Vector2D position) {
        this.position = position != null ? position : Vector2D.ZERO;
    }

    /**
     * Translates the position by the given vector.
     *
     * @param delta The vector to add to the current position.
     */
    public void translate(Vector2D delta) {
        if (delta != null) {
            this.position = this.position.add(delta);
        }
    }

    /**
     * Gets the rotation of the transform.
     *
     * @return The rotation in degrees.
     */
    public float getRotation() {
        return rotation;
    }

    /**
     * Sets the rotation of the transform.
     *
     * @param rotation The new rotation in degrees.
     */
    public void setRotation(float rotation) {
        this.rotation = normalizeAngle(rotation);
    }

    /**
     * Rotates the transform by the given angle.
     *
     * @param deltaRotation The angle to rotate by, in degrees.
     */
    public void rotate(float deltaRotation) {
        this.rotation = normalizeAngle(this.rotation + deltaRotation);
    }

    /**
     * Gets the scale of the transform.
     *
     * @return The scale vector.
     */
    public Vector2D getScale() {
        return scale;
    }

    /**
     * Sets the scale of the transform.
     *
     * @param scale The new scale vector.
     */
    public void setScale(Vector2D scale) {
        this.scale = scale != null ? scale : Vector2D.ONE;
    }

    /**
     * Scales the transform by the given vector.
     *
     * @param deltaScale The vector to multiply with the current scale.
     */
    public void scale(Vector2D deltaScale) {
        if (deltaScale != null) {
            this.scale = new Vector2D(
                    this.scale.getX() * deltaScale.getX(),
                    this.scale.getY() * deltaScale.getY()
            );
        }
    }

    /**
     * Resets the transform to default values (position ZERO, rotation 0, scale ONE).
     */
    public void reset() {
        this.position = Vector2D.ZERO;
        this.rotation = 0f;
        this.scale = Vector2D.ONE;
    }

    /**
     * Normalizes the angle to be within [0, 360) degrees.
     *
     * @param angle The angle to normalize.
     * @return The normalized angle.
     */
    private float normalizeAngle(float angle) {
        angle %= 360f;
        if (angle < 0f) {
            angle += 360f;
        }
        return angle;
    }

    @Override
    public String toString() {
        return String.format("Transform(Position: %s, Rotation: %.2f°, Scale: %s)",
                position, rotation, scale);
    }
}
