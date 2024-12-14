package springrender.engine.core;

import springrender.engine.graphics.Renderable;

import java.awt.Graphics2D;

public abstract class Entity implements Updatable, Renderable, Transformable {

    protected Transform transform = new Transform(Vector2D.ZERO);

    public abstract void update(double dt);

    public abstract void draw(Graphics2D graphics2D);

    public abstract void interpolate(double alpha);

}
