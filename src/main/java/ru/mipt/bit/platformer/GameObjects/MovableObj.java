package ru.mipt.bit.platformer.GameObjects;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class MovableObj extends GameObj{
  static public final float timeBetweenMoves = 0.4f;

  GridPoint2 DestinationCoordinates;
  float MovementProgress;

  public MovableObj() {
    this(new GridPoint2(1, 1), Direction.Left);
  }  

  public MovableObj(GridPoint2 Coordinates, Direction direction) {
    super(Coordinates, direction, GameObjType.PlayerTank);
    DestinationCoordinates = new GridPoint2(Coordinates);
    MovementProgress = 1f;
  }

  public boolean isMoving() {
    return isEqual(MovementProgress, 1f);
  }

  private boolean isCollide(Array<GameObj> obstacleCoordinate, GridPoint2 point) {
    for (GameObj obj : obstacleCoordinate) {
      if (obj.getCoordinates().equals(point)) {
        return true;
      }
    }

    return false;
  }

  public void move(Array<GameObj> obstacleCoordinates, Direction moveDirection) {
    if (this.isMoving()) {
      GridPoint2 destPoint = new GridPoint2(coordinates);
      destPoint.add(moveDirection.getOffset());

      if (!isCollide(obstacleCoordinates, destPoint)) {
        DestinationCoordinates = destPoint;
        MovementProgress = 0f;
      }
      direction = moveDirection;
    }
  }

  public void recalculate_position(float deltaTime) {
    MovementProgress = continueProgress(MovementProgress, deltaTime, timeBetweenMoves);
    if (isEqual(MovementProgress, 1f)) {
      coordinates.set(DestinationCoordinates);
    }
  }

  public GridPoint2 getDestinationCoordinates() {
    return DestinationCoordinates;
  }

  public float getMovementProgress() {
    return MovementProgress;
  }

  public Direction getDirection() {
    return direction;
  }
}
