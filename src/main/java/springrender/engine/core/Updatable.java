package springrender.engine.core;

public interface Updatable {

    /**
     * Updates the object's state
     *
     * @param deltaTime Fixed time step in seconds
     */
    public void update(double deltaTime);
}
