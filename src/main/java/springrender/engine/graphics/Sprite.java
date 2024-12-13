package springrender.engine.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class Sprite {
    private Map<String, List<BufferedImage>> animations;
    private String currentState;
    private double frameDuration; // Duration of each frame in seconds

    /**
     * Constructs a new Sprite with default settings.
     */
    public Sprite() {
        this.animations = new HashMap<>();
        this.currentState = "";
        this.frameDuration = 0.2;
    }

    /**
     * Loads an image and associates it with a state and frame index.
     *
     * @param state    The animation state (e.g., "walk_up").
     * @param filePath The path to the image resource.
     */
    public void loadImage(String state, String filePath) {
        try {
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream(filePath));
            animations.computeIfAbsent(state, k -> new ArrayList<>()).add(img);
        } catch (IOException e) {
            e.printStackTrace();
            // Consider handling this more gracefully or throwing a custom exception
        }
    }

    /**
     * Sets the current animation state.
     *
     * @param state The state to switch to.
     */
    public void setState(String state) {
        this.currentState = state;
    }

    /**
     * Retrieves the list of images for the current animation state.
     *
     * @return The list of BufferedImages for the current state, or null if the state doesn't exist.
     */
    public List<BufferedImage> getCurrentAnimationFrames() {
        return animations.get(currentState);
    }

    /**
     * Gets the frame duration.
     *
     * @return The duration of each frame in seconds.
     */
    public double getFrameDuration() {
        return frameDuration;
    }

    /**
     * Sets the frame duration.
     *
     * @param frameDuration The duration of each frame in seconds.
     */
    public void setFrameDuration(double frameDuration) {
        this.frameDuration = frameDuration;
    }

    /**
     * Gets the current animation state.
     *
     * @return The current animation state.
     */
    public String getCurrentState() {
        return currentState;
    }
}
