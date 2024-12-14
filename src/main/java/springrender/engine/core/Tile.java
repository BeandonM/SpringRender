package springrender.engine.core;

import springrender.engine.graphics.Sprite;
import springrender.engine.graphics.SpriteRender;
import springrender.engine.rendering.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Tile implements Renderable {
    SpriteRender spriteRender;
    Sprite sprite;

    public Tile(Sprite sprite, String state) {
        this.sprite = sprite;

        spriteRender = new SpriteRender(sprite);

        sprite.setState(state);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        BufferedImage image = spriteRender.getCurrentImage();
        graphics2D.drawImage(image, 0, 0, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    }

    @Override
    public int getLayer() {
        return 0;
    }


}
