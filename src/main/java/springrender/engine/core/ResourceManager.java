package springrender.engine.core;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager {
    private static ResourceManager instance;

    private Map<String, Object> resources;

    private ResourceManager() {
        resources = new HashMap<>();
    }

    public static synchronized ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }

    public Object getResource(String key) {
        return resources.get(key);
    }

    public void addResource(String key, Object resource) {
        resources.put(key, resource);
    }

    public void removeResource(String key) {
        resources.remove(key);
    }
}