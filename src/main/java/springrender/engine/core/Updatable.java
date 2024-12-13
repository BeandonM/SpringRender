package springrender.engine.core;

public interface Updatable {

    /**
     * Updates the object's state
     *
     * @param dt Fixed time step in seconds
     */
    public void update(double dt);
}
