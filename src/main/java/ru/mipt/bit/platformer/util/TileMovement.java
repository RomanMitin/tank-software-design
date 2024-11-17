package ru.mipt.bit.platformer.util;

import static ru.mipt.bit.platformer.Visualizer.GdxGameUtils.moveRectangleAtTileCenter;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;

import ru.mipt.bit.platformer.GameObjects.MovableObj;

public class TileMovement {

    private final TiledMapTileLayer tileLayer;
    private final Interpolation interpolation;

    public TileMovement(TiledMapTileLayer tileLayer, Interpolation interpolation) {
        this.tileLayer = tileLayer;
        this.interpolation = interpolation;
    }

    public Rectangle moveRectangleBetweenTileCenters(Rectangle rectangle, MovableObj obj) {
        moveRectangleAtTileCenter(tileLayer, rectangle, obj.getCoordinates());
        float fromTileBottomLeftX = rectangle.x;
        float fromTileBottomLeftY = rectangle.y;

        moveRectangleAtTileCenter(tileLayer, rectangle, obj.getDestinationCoordinates());
        float toTileBottomLeftX = rectangle.x;
        float toTileBottomLeftY = rectangle.y;

        float intermediateBottomLeftX = interpolation.apply(fromTileBottomLeftX, toTileBottomLeftX, obj.getMovementProgress());
        float intermediateBottomLeftY = interpolation.apply(fromTileBottomLeftY, toTileBottomLeftY, obj.getMovementProgress());

        return rectangle
                .setX(intermediateBottomLeftX)
                .setY(intermediateBottomLeftY);
    }
}
