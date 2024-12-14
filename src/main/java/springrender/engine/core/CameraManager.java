package springrender.engine.core;

import springrender.engine.rendering.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class CameraManager {
    private final List<Camera> cameras;
    private Camera activeCamera;

    public CameraManager() {
        this.cameras = new ArrayList<>();
    }

    public void addCamera(Camera camera) {
        if (camera == null) throw new IllegalArgumentException("Camera cannot be null");
        cameras.add(camera);

        // Automatically set the first added camera as active
        if (activeCamera == null) {
            activeCamera = camera;
        }
    }

    public void removeCamera(Camera camera) {
        cameras.remove(camera);
        if (activeCamera == camera) {
            activeCamera = cameras.isEmpty() ? null : cameras.get(0);
        }
    }

    public void setActiveCamera(Camera camera) {
        if (!cameras.contains(camera)) {
            throw new IllegalArgumentException("Camera is not managed by CameraManager");
        }
        activeCamera = camera;
    }

    public Camera getActiveCamera() {
        return activeCamera;
    }

    public void updateAll(double dt) {
        for (Camera camera : cameras) {
            camera.update(dt);
        }
    }

    public void renderAll(GamePanel gamePanel) {
        for (Camera camera : cameras) {
            gamePanel.setCamera(camera);
            // Render logic specific to each camera
        }
    }

    public List<Camera> getCameras() {
        return new ArrayList<>(cameras);
    }
}
