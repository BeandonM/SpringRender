package springrender.engine.rendering;

import springrender.engine.core.TileManager;

import javax.swing.*;

public class GameWindow {

    JFrame gameWindow;

    public GameWindow() {
        gameWindow = new JFrame();
        /*
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

         */

    }

    public JFrame getGameWindow() {
        return gameWindow;
    }

    public static void main(String[] args) {
        GameWindow gameWindow = new GameWindow();
    }
}
