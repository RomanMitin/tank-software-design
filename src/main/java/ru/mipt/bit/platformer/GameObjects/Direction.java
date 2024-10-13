package ru.mipt.bit.platformer.GameObjects;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    Down(270f, 0, -1), Left(180f, -1, 0), Up(90, 0, 1), Right(0f, 1, 0);

    Direction(float angle, int x, int y) {
        this.angle = angle;
        this.offset = new GridPoint2(x, y);
    }

    public float getAnle() {
        return angle;
    }

    public GridPoint2 getOffset() {
        return offset;
    }

    float angle;
    GridPoint2 offset;
}
