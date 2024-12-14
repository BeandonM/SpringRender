package springrender.engine.rendering;

import springrender.engine.core.TileManager;

import javax.swing.*;

public class GameWindow {

    public GameWindow() {
        JFrame gameWindow = new JFrame();
        gameWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameWindow.setResizable(true);
        gameWindow.setTitle("Test Title");
        GamePanel gamePanel = new GamePanel();
        gameWindow.add(gamePanel);
        TileManager tileManager = new TileManager(gamePanel);
        gameWindow.pack();
        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);

    }

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
    }
}
