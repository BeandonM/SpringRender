package springrender.engine.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Sprite {
    private Map<String, BufferedImage> images;
    private BufferedImage currentImage;

    public Sprite() {
        images = new HashMap<>();
    }

    /**
     * Loads an image and associates it with a key.
     *
     * @param key      The identifier for the image (e.g., "up1", "down1").
     * @param filePath The path to the image resource.
     */
    public void loadImage(String key, String filePath) {
        try {
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream(filePath));
            images.put(key, img);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the current image based on the provided key.
     *
     * @param key The identifier for the image to set as current.
     */
    public void setCurrentImage(String key) {
        currentImage = images.get(key);
    }

    /**
     * Retrieves the current image.
     *
     * @return The current BufferedImage.
     */
    public BufferedImage getCurrentImage() {
        return currentImage;
    }
}
