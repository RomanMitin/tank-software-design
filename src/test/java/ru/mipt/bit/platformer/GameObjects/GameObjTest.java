package ru.mipt.bit.platformer.GameObjects;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.GridPoint2;

import org.junit.jupiter.api.Assertions;

public class GameObjTest {
    
    @Test
    public void defaultCtorTest() {
        // Setup
        GameObj obj = new GameObj(GameObjType.Tree);

        // Execution
        Direction dir = obj.getDirection();
        GridPoint2 coord = obj.getCoordinates();

        // Assertion
        Assertions.assertNotNull(dir);
        Assertions.assertNotNull(coord);
    }

    @Test
    public void ParamCtorTest() {
        // Setup
        Direction expected_dir = Direction.Up;
        GridPoint2 expected_coord = new GridPoint2(1, 2);
        GameObj obj = new GameObj(expected_coord, expected_dir);

        // Execution
        Direction actual_dir = obj.getDirection();
        GridPoint2 actual_coord = obj.getCoordinates();

        // Assertion
        Assertions.assertEquals(expected_coord, actual_coord);
        Assertions.assertEquals(expected_dir, actual_dir);
    }
}
