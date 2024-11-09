package ru.mipt.bit.platformer.Visualizer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.GameObjects.GameObj;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class GameObjDrawable implements Drawable{
    protected GameObj gameObject;
    protected Rectangle rectangle;
    private Texture texture;
    private TextureRegion textureRegion;

    public GameObjDrawable(GameObj gameObject, Texture texture) {
        this.gameObject = gameObject;
        this.texture = texture;
        this.textureRegion = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(textureRegion);
    }

    @Override
    public void drawTexture(Batch batch, float deltaTime) {
        drawTextureRegionUnscaled(batch, textureRegion, rectangle, gameObject.getDirection());
    }

    @Override
    public
    boolean isGameObj(GameObj gameObj) {
        return this.gameObject == gameObj;
    };

    @Override
    public void dispose() {
        texture.dispose();
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
