
package ru.mipt.bit.platformer.Visualizer;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.GameObjects.Direction;
import ru.mipt.bit.platformer.GameObjects.GameObj;

/**
 * DrawableHealthDecorator
 */
public class DrawableHealthDecorator extends DrawableDecorator {

    DrawableHealthDecorator(GameObjDrawable gameObjDrawable) {
        super((Drawable)gameObjDrawable);
    }

    @Override
    public void drawTexture(Batch batch, float deltaTime) {
        super.drawTexture(batch, deltaTime);

        if (DrawableHealthDecoratorController.needToDrawHealth(deltaTime)) {
            renderHealthBar(batch);
        }
    }
    
    private void renderHealthBar(Batch batch) {
        GameObjDrawable gameObjDrawable = (GameObjDrawable) super.impl;
        float health = gameObjDrawable.gameObject.getHeath();
        var healthbarTexture = getHealthbarTexture(health / GameObj.max_heath);
        var rectangle = new Rectangle(gameObjDrawable.getRectangle());
        rectangle.y += 90;
        GdxGameUtils.drawTextureRegionUnscaled(batch, healthbarTexture, rectangle, Direction.Left);
    }

    private TextureRegion getHealthbarTexture(float relativeHealth) {
        var pixmap = new Pixmap(90, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fillRectangle(0, 0, 90, 20);
        pixmap.setColor(Color.GREEN);
        pixmap.fillRectangle(0, 0, (int) (90 * relativeHealth), 20);
        var texture = new Texture(pixmap);
        // pixmap.dispose();
        return new TextureRegion(texture);
    }
}