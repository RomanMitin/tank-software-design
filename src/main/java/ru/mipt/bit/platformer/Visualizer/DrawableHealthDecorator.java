
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

    static public boolean revert_health_visible = false;
    static public boolean is_ready_for_revert = true;
    static public final float time_between_react = 0.5f;

    private static float elapsed_time = 0;
    private static boolean old_health_visible = false;
    private static boolean first_object_flag = true;
    private boolean is_main_object = false;

    DrawableHealthDecorator(GameObjDrawable gameObjDrawable) {
        super((Drawable)gameObjDrawable);
        if (first_object_flag) {
            first_object_flag = false;
            is_main_object = true;
        }
    }

    @Override
    public void drawTexture(Batch batch, float deltaTime) {
        super.drawTexture(batch, deltaTime);
        if (is_main_object) {
            elapsed_time += deltaTime;
            if (elapsed_time > time_between_react) {
                is_ready_for_revert = true;
                elapsed_time = 0;
            }
        }
        if (revert_health_visible && is_ready_for_revert) {
            revert_health_visible = false;
            is_ready_for_revert = false;
            old_health_visible = !old_health_visible;
        }

        if (old_health_visible) {
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