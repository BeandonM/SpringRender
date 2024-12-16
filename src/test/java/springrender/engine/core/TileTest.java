package springrender.engine.core;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import springrender.engine.graphics.Sprite;
import springrender.engine.graphics.SpriteRender;
import springrender.engine.rendering.GamePanel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TileTest {
    @Test
    void testIsCollidable() {
        Sprite sprite = Mockito.mock(Sprite.class);
        Tile collidableTile = new Tile(sprite, "state", true);
        Tile nonCollidableTile = new Tile(sprite, "state", false);

        assertTrue(collidableTile.isCollidable(), "Tile should be collidable");
        assertFalse(nonCollidableTile.isCollidable(), "Tile should not be collidable");
    }

    @Test
    void testDraw() {
        Sprite sprite = Mockito.mock(Sprite.class);
        SpriteRender spriteRender = Mockito.mock(SpriteRender.class);
        BufferedImage mockImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        Mockito.when(spriteRender.getCurrentImage()).thenReturn(mockImage);

        Tile tile = new Tile(sprite, "state", true);
        tile.spriteRender = spriteRender;

        var graphics2D = Mockito.mock(Graphics2D.class);
        tile.draw(graphics2D, 50, 100);

        Mockito.verify(graphics2D).drawImage(
                mockImage,
                50,
                100,
                GamePanel.TILE_SIZE,
                GamePanel.TILE_SIZE,
                null
        );
    }

    @Test
    void testStateInitialization() {
        Sprite sprite = Mockito.mock(Sprite.class);
        Tile tile = new Tile(sprite, "defaultState", false);

        Mockito.verify(sprite).setState("defaultState");
    }
}
