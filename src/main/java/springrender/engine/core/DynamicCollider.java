package springrender.engine.core;

public interface DynamicCollider extends Collider {
    public void resolveCollision(Collider other);
}
