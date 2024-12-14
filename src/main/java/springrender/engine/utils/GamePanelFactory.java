package springrender.engine.utils;

import springrender.engine.rendering.GamePanel;

import java.awt.Color;
import java.awt.Dimension;

public class GamePanelFactory {

    private static final int DEFAULT_WIDTH = 768;

    private static final int DEFAULT_HEIGHT = 576;
    private int width;
    private int height;
    private Color backgroundColor = Color.darkGray;

    public GamePanelFactory() {
        this.width = DEFAULT_WIDTH;
        this.height = DEFAULT_HEIGHT;
    }

    public GamePanelFactory setSize(int width, int height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public GamePanelFactory setBackgroundColor(Color color) {
        this.backgroundColor = color;
        return this;
    }

    public GamePanel create() {
        GamePanel gamePanel = new GamePanel();
        gamePanel.setWidth(width);
        gamePanel.setHeight(width);
        gamePanel.setPreferredSize(new Dimension(width, height));
        gamePanel.setBackground(backgroundColor);
        return gamePanel;
    }
}
