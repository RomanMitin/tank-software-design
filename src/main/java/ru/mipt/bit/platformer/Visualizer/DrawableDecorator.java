package ru.mipt.bit.platformer.Visualizer;

import com.badlogic.gdx.graphics.g2d.Batch;

import ru.mipt.bit.platformer.GameObjects.GameObj;

public abstract class DrawableDecorator implements Drawable{
    protected Drawable impl;

    DrawableDecorator(Drawable drawable) {
        impl = drawable;
    }

    @Override
    public void drawTexture(Batch batch, float deltaTime) {
        impl.drawTexture(batch, deltaTime);
    }

    @Override
    public
    boolean isGameObj(GameObj gameObj) {
            return impl.isGameObj(gameObj);
    };

    @Override
    public void dispose() {
        impl.dispose();
    }
}
