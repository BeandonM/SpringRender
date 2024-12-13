package springrender.engine.core;

import java.awt.Graphics2D;

public abstract class Entity implements Updatable, Renderable {

    public abstract void update(double dt);

    public abstract void draw(Graphics2D graphics2D);

    public abstract void interpolate(double alpha);

}
