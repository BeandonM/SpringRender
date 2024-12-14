package springrender.engine.core;

public interface GameThread extends Runnable {
    public abstract void start();

    public abstract void stop();

    public abstract void update();

    public abstract void render();
}
