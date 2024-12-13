package springrender.engine.core;

import java.util.ArrayList;
import java.util.List;

public class UpdateManager {
    private List<Updatable> updatableList;

    public UpdateManager() {
        updatableList = new ArrayList<>();
    }

    public void addUpdatable(Updatable updatable) {
        updatableList.add(updatable);
    }

    public void removeUpdatable(Updatable updatable) {
        updatableList.remove(updatable);
    }

    public void updateAll(double dt) {
        for (Updatable updatable : updatableList) {
            updatable.update(dt);
        }
    }
}
