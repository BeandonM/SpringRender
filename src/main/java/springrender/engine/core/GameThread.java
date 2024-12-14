package springrender.engine.core;

public interface GameThread extends Runnable, Updatable {
    public abstract void start();

    public abstract void stop();

    public abstract void render();
}
