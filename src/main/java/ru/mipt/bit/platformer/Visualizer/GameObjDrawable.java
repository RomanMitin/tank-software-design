package ru.mipt.bit.platformer.Visualizer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.GameObjects.GameObj;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class GameObjDrawable implements Drawable{
    private GameObj gameObject;
    private Texture texture;
    private TextureRegion textureRegion;
    private Rectangle rectangle;

    public GameObjDrawable(GameObj gameObject, Texture texture) {
        this.gameObject = gameObject;
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
    }

    @Override
    public void drawTexture(Batch batch) {

        drawTextureRegionUnscaled(batch, textureRegion, rectangle, gameObject.getDirection());
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Texture getTexture() {
        return texture;
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
}
