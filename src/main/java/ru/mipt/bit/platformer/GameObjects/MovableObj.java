package ru.mipt.bit.platformer.GameObjects;

import com.badlogic.gdx.math.GridPoint2;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;

public class MovableObj extends GameObj{
  static public final float timeBetweenMoves = 0.4f;

  GridPoint2 DestinationCoordinates;
  float MovementProgress;

  public MovableObj() {
    DestinationCoordinates = new GridPoint2(1, 1);
    direction = Direction.Left;
    MovementProgress = 1f;
  }  

  public MovableObj(GridPoint2 Coordinates, Direction direction) {
    super(Coordinates, direction);
    DestinationCoordinates = new GridPoint2(1, 1);
    MovementProgress = 1f;
  }

  public boolean isMoving() {
    return isEqual(MovementProgress, 1f);
  }

  public void move(GridPoint2 obstacleCoordinate, Direction moveDirection) {
    if (this.isMoving()) {
      GridPoint2 destPoint = new GridPoint2(coordinates);
      destPoint.add(moveDirection.getOffset());

      if (!obstacleCoordinate.equals(destPoint)) {
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

  public GridPoint2 getCoordinates() {
    return coordinates;
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
