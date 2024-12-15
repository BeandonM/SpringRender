package springrender.engine.core;

import java.io.InputStream;

public interface ResourceLoader {
    public abstract void load(InputStream inputStream);
}
