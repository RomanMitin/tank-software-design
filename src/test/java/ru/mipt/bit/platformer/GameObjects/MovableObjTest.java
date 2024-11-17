package ru.mipt.bit.platformer.GameObjects;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.EventManager.EventManager;
import ru.mipt.bit.platformer.util.CollisionHandler;

import org.junit.jupiter.api.Assertions;

public class MovableObjTest {
    @Test
    public void defaultCtorTest() {
        // Setup
        EventManager eventManager = new EventManager();
        CollisionHandler collisionHanler = new CollisionHandler(eventManager);
        MovableObj obj = new MovableObj(collisionHanler);

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
        EventManager eventManager = new EventManager();
        CollisionHandler collisionHanler = new CollisionHandler(eventManager);
        Direction expected_dir = Direction.Up;
        GridPoint2 expected_coord = new GridPoint2(1, 3);
        MovableObj obj = new MovableObj(collisionHanler, expected_coord, expected_dir, GameObjType.PlayerTank);

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
        EventManager eventManager = new EventManager();
        CollisionHandler collisionHanler = new CollisionHandler(eventManager);
        Direction expected_dir = Direction.Up;
        GridPoint2 expected_coord = new GridPoint2(1, 1);
        MovableObj obj = new MovableObj(collisionHanler, expected_coord, expected_dir, GameObjType.PlayerTank);

        // Execution
        obj.move(Direction.Up);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(new GridPoint2(1, 2), obj.getCoordinates());

        obj.move(Direction.Right);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(new GridPoint2(2, 2), obj.getCoordinates());

        obj.move(Direction.Down);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(new GridPoint2(2, 1), obj.getCoordinates());

        obj.move(Direction.Left);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(new GridPoint2(1, 1), obj.getCoordinates());

        // Assertion
        Assertions.assertEquals(1f, obj.getMovementProgress());
        Assertions.assertTrue(obj.isMoving());
    }

    @Test
    public void MoveWithObstacles() {
        // Setup
        EventManager eventManager = new EventManager();
        CollisionHandler collisionHanler = new CollisionHandler(eventManager);
        Direction expected_dir = Direction.Up;
        GridPoint2 expected_coord = new GridPoint2(1, 1);
        MovableObj obj = new MovableObj(collisionHanler, expected_coord, expected_dir, GameObjType.PlayerTank);

        // Execution
        Array<GameObj> arrayObjects = new Array<GameObj>();
        arrayObjects.add(new GameObj(new GridPoint2(1, 2), Direction.Down));
        obj.move(Direction.Up);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(expected_coord, obj.getCoordinates());

        arrayObjects = new Array<GameObj>();
        arrayObjects.add(new GameObj(new GridPoint2(2, 1), Direction.Down));
        obj.move(Direction.Right);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(expected_coord, obj.getCoordinates());

        arrayObjects = new Array<GameObj>();
        arrayObjects.add(new GameObj(new GridPoint2(1, 0), Direction.Down));
        obj.move(Direction.Down);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(expected_coord, obj.getCoordinates());

        arrayObjects = new Array<GameObj>();
        arrayObjects.add(new GameObj(new GridPoint2(0, 1), Direction.Down));
        obj.move(Direction.Left);
        obj.recalculate_position(MovableObj.timeBetweenMoves);
        Assertions.assertEquals(expected_coord, obj.getCoordinates());

        // Assertion
        Assertions.assertEquals(1f, obj.getMovementProgress());
        Assertions.assertTrue(obj.isMoving());
    }
}
