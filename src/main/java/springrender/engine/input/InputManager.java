package springrender.engine.input;

import javax.swing.JComponent;

public class InputManager {
    private InputHandler inputHandler;

    public InputManager(JComponent component) {
        this.inputHandler = new InputHandler(component);
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }
}