package springrender.engine.core;

import java.awt.Graphics2D;

public interface Renderable {

    /**
     * Draws the object to the screen
     *
     * @param graphics2D
     */
    void draw(Graphics2D graphics2D);

    int getLayer();
}
