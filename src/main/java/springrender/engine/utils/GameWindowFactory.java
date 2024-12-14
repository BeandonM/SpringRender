package springrender.engine.utils;

import springrender.engine.rendering.GamePanel;
import springrender.engine.rendering.GameWindow;

import javax.swing.JFrame;

public class GameWindowFactory {

    private String title = "Game Window";
    private boolean resizable = true;
    private int defaultCloseOperation = JFrame.EXIT_ON_CLOSE;
    private int width = 800;
    private int height = 600;

    public GameWindowFactory setTitle(String title) {
        this.title = title;
        return this;
    }

    public GameWindowFactory setResizable(boolean resizable) {
        this.resizable = resizable;
        return this;
    }

    public GameWindowFactory setDefaultCloseOperation(int operation) {
        this.defaultCloseOperation = operation;
        return this;
    }

    public GameWindowFactory setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public GameWindow create(GamePanel gamePanel) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(defaultCloseOperation);
        frame.setResizable(resizable);
        frame.setSize(width, height);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return new GameWindow(frame);
    }
}