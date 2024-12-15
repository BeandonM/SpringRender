package springrender.engine.core;

import java.util.ArrayList;
import java.util.List;

public class CollisionManager {
    private List<StaticCollider> staticColliders;

    private List<DynamicCollider> dynamicColliders;

    public CollisionManager() {
        staticColliders = new ArrayList<>();
        dynamicColliders = new ArrayList<>();
    }

    public void addStaticCollider(StaticCollider collider) {
        staticColliders.add(collider);
    }

    public void addDynamicCollider(DynamicCollider collider) {
        dynamicColliders.add(collider);
    }

    public void removeStaticCollider(StaticCollider collider) {
        staticColliders.remove(collider);
    }

    public void removeDynamicCollider(DynamicCollider collider) {
        dynamicColliders.remove(collider);
    }
}
