package springrender.engine.core;

import springrender.engine.graphics.Sprite;
import springrender.engine.graphics.SpriteRender;
import springrender.engine.graphics.Renderable;
import springrender.engine.rendering.GamePanel;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;

public class Tile implements Renderable {

    private Transform transform;
    SpriteRender spriteRender;
    Sprite sprite;

    boolean isCollidable;

    public Tile(Sprite sprite, String state, boolean isCollidable) {
        this.sprite = sprite;

        spriteRender = new SpriteRender(sprite);

        this.isCollidable = isCollidable;
        sprite.setState(state);
    }


    public boolean isCollidable() {
        return isCollidable;
    }

    public void draw(Graphics2D graphics2D, int x, int y) {
        BufferedImage image = spriteRender.getCurrentImage();
        graphics2D.drawImage(image, x, y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    }


    @Override
    public void draw(Graphics2D graphics2D) {
        draw(graphics2D, 0, 0);
    }

    @Override
    public int getLayer() {
        return 0;
    }


}
