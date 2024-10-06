package ru.mipt.bit.platformer.GameObjects;

import com.badlogic.gdx.math.GridPoint2;

public class GameObj {
    protected GridPoint2 coordinates;
    protected Direction direction;

    public GameObj() {
        coordinates = new GridPoint2(1, 5);
        direction = Direction.Left;
    }

    public GameObj(GridPoint2 treeObstacleCoordinates, Direction direction) {
        this.coordinates = treeObstacleCoordinates;
        this.direction = direction;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public Direction getDirection() {
        return direction;
    }
}