package springrender.engine.graphics;

import springrender.engine.core.Updatable;
import springrender.engine.core.UpdateManager;

import java.awt.image.BufferedImage;
import java.util.List;

public class SpriteRender implements Updatable {
    private Sprite sprite;
    private int currentFrame;
    private double timeSinceLastFrame;

    private UpdateManager updateManager;

    /**
     * Constructs a new SpriteRender and registers it with the provided UpdateManager.
     *
     * @param sprite        The Sprite to render and update.
     * @param updateManager The UpdateManager to register with.
     */
    public SpriteRender(Sprite sprite, UpdateManager updateManager) {
        this.sprite = sprite;
        this.currentFrame = 0;
        this.timeSinceLastFrame = 0.0;
        this.updateManager = updateManager;
        this.updateManager.addUpdatable(this);
    }

    /**
     * Updates the current frame based on the elapsed time.
     *
     * @param dt The delta time since the last update (in seconds).
     */
    @Override
    public void update(double dt) {
        List<BufferedImage> frames = sprite.getCurrentAnimationFrames();
        if (frames == null || frames.isEmpty()) {
            return; // No frames to update
        }

        timeSinceLastFrame += dt;
        double frameDuration = sprite.getFrameDuration();

        while (timeSinceLastFrame >= frameDuration) {
            timeSinceLastFrame -= frameDuration;
            currentFrame = (currentFrame + 1) % frames.size();
        }
    }

    /**
     * Retrieves the current image to render.
     *
     * @return The current BufferedImage.
     */
    public BufferedImage getCurrentImage() {
        List<BufferedImage> frames = sprite.getCurrentAnimationFrames();
        if (frames == null || frames.isEmpty()) {
            return null; // Or a default image
        }
        return frames.get(currentFrame);
    }

    /**
     * Resets the animation to the first frame.
     */
    public void resetAnimation() {
        this.currentFrame = 0;
        this.timeSinceLastFrame = 0.0;
    }

    /**
     * Sets the current frame manually.
     *
     * @param frameIndex The index of the frame to set.
     */
    public void setCurrentFrame(int frameIndex) {
        List<BufferedImage> frames = sprite.getCurrentAnimationFrames();
        if (frames != null && frameIndex >= 0 && frameIndex < frames.size()) {
            this.currentFrame = frameIndex;
            this.timeSinceLastFrame = 0.0;
        }
    }

    /**
     * Gets the current frame index.
     *
     * @return The current frame index.
     */
    public int getCurrentFrame() {
        return currentFrame;
    }
}
