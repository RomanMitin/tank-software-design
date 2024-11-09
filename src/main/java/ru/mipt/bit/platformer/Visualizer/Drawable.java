package ru.mipt.bit.platformer.Visualizer;

import com.badlogic.gdx.graphics.g2d.Batch;

import ru.mipt.bit.platformer.GameObjects.GameObj;

public interface Drawable {
    void drawTexture(Batch batch, float deltaTime);

    default boolean isGameObj(GameObj gameObj) {
            return false;
    };

    void dispose();
}