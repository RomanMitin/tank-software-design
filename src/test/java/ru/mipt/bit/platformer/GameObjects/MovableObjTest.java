package ru.mipt.bit.platformer.GameObjects;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

import org.junit.jupiter.api.Assertions;

public class MovableObjTest {

    @Test
    public void defaultCtorTest() {
        // Setup
        MovableObj obj = new MovableObj();

        // Execution
        Direction dir = obj.getDirection();
        GridPoint2 coord = obj.getCoordinates();
        GridPoint2 dest_coord = obj.getDestinationCoordinates();
        Float move_progress = obj.getMovementProgress();

        // Assertion
        Assertions.assertNotNull(dir);
        Assertions.assertNotNull(coord);
        Assertions.assertNotNull(dest_coord);
        Assertions.assertEquals(1f, move_progress);
        Assertions.assertTrue(obj.isMoving());
    }

    @Test
    public void ParamCtorTestCtorTest() {
        // Setup
        Direction expected_dir = Direction.Up;
        GridPoint2 expected_coord = new GridPoint2(1, 3);
        MovableObj obj = new MovableObj(expected_coord, expected_dir);

        // Execution
        Direction dir = obj.getDirection();
        GridPoint2 coord = obj.getCoordinates();
        GridPoint2 dest_coord = obj.getDestinationCoordinates();
        Float move_progress = obj.getMovementProgress();

        // Assertion
        Assertions.assertEquals(expected_dir, dir);
        Assertions.assertEquals(expected_coord, coord);
        Assertions.assertNotNull(dest_coord);
        Assertions.assertEquals(1f, move_progress);
        Assertions.assertTrue(obj.isMoving());
    }

    @Test
    public void MoveWithoutObstacles() {
        // Setup
        Direction expected_dir = Direction.Up;
        GridPoint2 expected_coord = new GridPoint2(1, 1);
        MovableObj obj = new MovableObj(expected_coord, expected_dir);

        // Execution
        obj.move(new Array<>(), Direction.Up);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(new GridPoint2(1, 2), obj.getCoordinates());

        obj.move(new Array<>(), Direction.Right);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(new GridPoint2(2, 2), obj.getCoordinates());

        obj.move(new Array<>(), Direction.Down);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(new GridPoint2(2, 1), obj.getCoordinates());

        obj.move(new Array<>(), Direction.Left);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(new GridPoint2(1, 1), obj.getCoordinates());

        // Assertion
        Assertions.assertEquals(1f, obj.getMovementProgress());
        Assertions.assertTrue(obj.isMoving());
    }

    @Test
    public void MoveWithObstacles() {
        // Setup
        Direction expected_dir = Direction.Up;
        GridPoint2 expected_coord = new GridPoint2(1, 1);
        MovableObj obj = new MovableObj(expected_coord, expected_dir);

        // Execution
        Array<GameObj> arrayObjects = new Array<GameObj>();
        arrayObjects.add(new GameObj(new GridPoint2(1, 2), Direction.Down));
        obj.move(arrayObjects, Direction.Up);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(expected_coord, obj.getCoordinates());

        arrayObjects = new Array<GameObj>();
        arrayObjects.add(new GameObj(new GridPoint2(2, 1), Direction.Down));
        obj.move(arrayObjects, Direction.Right);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(expected_coord, obj.getCoordinates());

        arrayObjects = new Array<GameObj>();
        arrayObjects.add(new GameObj(new GridPoint2(1, 0), Direction.Down));
        obj.move(arrayObjects, Direction.Down);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(expected_coord, obj.getCoordinates());

        arrayObjects = new Array<GameObj>();
        arrayObjects.add(new GameObj(new GridPoint2(0, 1), Direction.Down));
        obj.move(arrayObjects, Direction.Left);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(expected_coord, obj.getCoordinates());

        // Assertion
        Assertions.assertEquals(1f, obj.getMovementProgress());
        Assertions.assertTrue(obj.isMoving());
    }
}
