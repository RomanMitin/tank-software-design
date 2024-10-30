package ru.mipt.bit.platformer.Visualizer;

import com.badlogic.gdx.graphics.g2d.Batch;

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
    public void dispose() {
        impl.dispose();
    }
}
