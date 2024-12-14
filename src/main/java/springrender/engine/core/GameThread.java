package springrender.engine.core;

public interface GameThread extends Runnable {
    public abstract void start();

    public abstract void stop();
}
