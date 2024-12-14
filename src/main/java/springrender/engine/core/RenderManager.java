package springrender.engine.core;

import springrender.engine.core.Renderable;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RenderManager {

    private List<Renderable> renderList;
    private boolean needsSorting;

    public RenderManager() {
        renderList = new ArrayList<>();
        needsSorting = false;
    }

    /**
     * Adds a renderable object to the manager.
     *
     * @param renderable The renderable object to add.
     */
    public void addRenderable(Renderable renderable) {
        renderList.add(renderable);
        needsSorting = true;
    }

    /**
     * Removes a renderable object from the manager.
     *
     * @param renderable The renderable object to remove.
     */
    public void removeRenderable(Renderable renderable) {
        renderList.remove(renderable);
        needsSorting = true;
    }

    /**
     * Sorts the renderables based on their layer if needed.
     */
    private void sortRenderables() {
        if (needsSorting) {
            Collections.sort(renderList, Comparator.comparingInt(Renderable::getLayer));
            needsSorting = false;
        }
    }

    /**
     * Renders all renderables onto the provided Graphics2D context.
     *
     * @param g The Graphics2D context to render onto.
     */
    public void render(Graphics2D g) {
        sortRenderables();
        for (Renderable renderable : renderList) {
            renderable.draw(g);
        }
    }

    /**
     * Marks the render manager to sort renderables on next render call.
     */
    public void markForSorting() {
        needsSorting = true;
    }
}
