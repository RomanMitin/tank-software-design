package ru.mipt.bit.platformer.GameObjects;

import com.badlogic.gdx.math.GridPoint2;

public class GameObj {
    protected GridPoint2 coordinates;
    protected Direction direction;
    protected GameObjType type;
    protected float heath;

    static public float max_heath = 100;

    public GameObj(GameObjType type) {
        this(new GridPoint2(1, 5), Direction.Left, type);
    }

    public GameObj(GridPoint2 coordinates, Direction direction) {
        this(coordinates, direction, GameObjType.Tree);
    }

    public GameObj(GridPoint2 coordinates, Direction direction, GameObjType type) {
        this.coordinates = coordinates;
        this.direction = direction;
        this.type = type;
        this.heath = 80;
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    public Direction getDirection() {
        return direction;
    }

    public GameObjType getType() {
        return type;
    }

    public float getHeath() {
        return heath;
    }
}