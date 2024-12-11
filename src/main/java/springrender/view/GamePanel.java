package springrender.view;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

    private static final int originalTileSize = 16;
    private static final int scale = 3;
    private int tileSize = originalTileSize * scale;
    private int maxScreenWidthMulti = 16;
    private int maxScreenHeightMulti = 12;
    private int screenWidth = tileSize * maxScreenWidthMulti;
    private int screenHeight = tileSize * maxScreenHeightMulti;

}
