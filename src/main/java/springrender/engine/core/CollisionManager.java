package springrender.engine.core;

import java.util.*;

public class CollisionManager {

    private Map<DynamicCollider, Set<Collider>> activeCollisions;

    private List<StaticCollider> staticColliders;

    private List<DynamicCollider> dynamicColliders;

    public CollisionManager() {
        activeCollisions = new HashMap<>();
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

    public void checkCollisions() {
        Map<DynamicCollider, Set<Collider>> newCollisions = new HashMap<>();

        for (DynamicCollider dynamic : dynamicColliders) {
            for (StaticCollider staticCollider : staticColliders) {
                if (dynamic.isColliding(staticCollider)) {
                    newCollisions.
                            computeIfAbsent(dynamic, k -> new HashSet<>())
                            .add(staticCollider);

                }
                if (!activeCollisions.containsKey(dynamic) ||
                        !activeCollisions.get(dynamic).contains(staticCollider)) {
                    dynamic.onCollisionEnter(staticCollider);
                }
            }
        }

        for (int i = 0; i < dynamicColliders.size(); i++) {
            for (int j = i + 1; j < dynamicColliders.size(); j++) {
                DynamicCollider a = dynamicColliders.get(i);
                DynamicCollider b = dynamicColliders.get(j);
                if (a.isColliding(b)) {
                    newCollisions
                            .computeIfAbsent(a, k -> new HashSet<>())
                            .add(b);
                    if (!activeCollisions.containsKey(a) ||
                            !activeCollisions.get(a).contains(b)) {
                        a.onCollisionEnter(b);
                    }
                }
            }
        }
        // Check for exited collisions
        for (DynamicCollider dynamic : activeCollisions.keySet()) {
            for (Collider other : activeCollisions.get(dynamic)) {
                if (!newCollisions.containsKey(dynamic) ||
                        !newCollisions.get(dynamic).contains(other)) {
                    dynamic.onCollisionExit(other);
                }
            }
        }

        // Update active collisions
        activeCollisions = newCollisions;
    }
}
