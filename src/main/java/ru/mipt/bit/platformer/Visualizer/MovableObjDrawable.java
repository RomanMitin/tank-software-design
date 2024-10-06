package ru.mipt.bit.platformer.Visualizer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import ru.mipt.bit.platformer.GameObjects.MovableObj;
import ru.mipt.bit.platformer.util.TileMovement;

public class MovableObjDrawable extends GameObjDrawable {
    private TileMovement tileMovement;

    public MovableObjDrawable(MovableObj gameObject, Texture texture, TileMovement tileMovement) {
        super(gameObject, texture);
        this.tileMovement = tileMovement;
    }

    @Override
    public void drawTexture(Batch batch, float deltaTime) {
        // calculate interpolated player screen coordinates
        MovableObj movableGameObj = (MovableObj)gameObject;
        movableGameObj.recalculate_position(deltaTime);
        tileMovement.moveRectangleBetweenTileCenters(rectangle, movableGameObj);
        super.drawTexture(batch, deltaTime);
    }
}
