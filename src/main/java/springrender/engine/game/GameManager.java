package springrender.engine.game;

import springrender.engine.input.InputHandler;
import springrender.engine.rendering.GamePanel;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private boolean running = false;
    private GamePanel gamePanel;
    private InputHandler inputHandler;
    private List<Updatable> updatables;
    private List<Renderable> renderables;

    public GameManager(GamePanel gamePanel, InputHandler inputHandler) {
        this.gamePanel = gamePanel;
        this.inputHandler = inputHandler;
        updatables = new ArrayList<>();
        renderables = new ArrayList<>();
    }
}
