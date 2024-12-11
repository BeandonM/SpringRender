package springrender.view;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;

public class GamePanel extends JPanel {

    private static final int originalTileSize = 16;
    private static final int scale = 3;
    private int tileSize = originalTileSize * scale;
    private int maxScreenWidthMulti = 16;
    private int maxScreenHeightMulti = 12;
    private int screenWidth = tileSize * maxScreenWidthMulti;
    private int screenHeight = tileSize * maxScreenHeightMulti;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
    }
}
