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
    private int currentFrame;
    private double frameDuration; // Duration of each frame in seconds
    private double timeSinceLastFrame;

    public Sprite() {
        animations = new HashMap<>();
        currentState = "";
        currentFrame = 0;
        frameDuration = 0.2;
        timeSinceLastFrame = 0.0;
    }

    /**
     * Loads an image and associates it with a state and frame index.
     *
     * @param state    The animation state (e.g., "walk_up").
     * @param frame    The frame index for the state.
     * @param filePath The path to the image resource.
     */
    public void loadImage(String state, int frame, String filePath) {
        try {
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream(filePath));
            animations.computeIfAbsent(state, k -> new ArrayList<>()).add(frame, img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the current animation state.
     *
     * @param state The state to switch to.
     */
    public void setState(String state) {
        if (!state.equals(currentState)) {
            currentState = state;
            currentFrame = 0;
            timeSinceLastFrame = 0.0;
        }
    }

    /**
     * Updates the animation based on the elapsed time.
     *
     * @param dt The delta time since the last update.
     */
    public void update(double dt) {
        if (animations.containsKey(currentState)) {
            timeSinceLastFrame += dt;
            if (timeSinceLastFrame >= frameDuration) {
                timeSinceLastFrame -= frameDuration;
                currentFrame = (currentFrame + 1) % animations.get(currentState).size();
            }
        }
    }

    /**
     * Retrieves the current image based on the animation state and frame.
     *
     * @return The current BufferedImage.
     */
    public BufferedImage getCurrentImage() {
        if (animations.containsKey(currentState) && !animations.get(currentState).isEmpty()) {
            return animations.get(currentState).get(currentFrame);
        }
        return null; // Or a default image
    }
}
