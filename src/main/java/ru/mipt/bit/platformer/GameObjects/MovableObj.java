package ru.mipt.bit.platformer.GameObjects;

import com.badlogic.gdx.math.GridPoint2;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;
import static com.badlogic.gdx.math.MathUtils.isEqual;

public class MovableObj extends GameObj{
  static final float timeBetweenMoves = 0.4f;

  GridPoint2 DestinationCoordinates;
  float MovementProgress;

  public MovableObj() {
    DestinationCoordinates = new GridPoint2(1, 1);
    direction = Direction.Left;
    MovementProgress = 1f;
  }  

  public MovableObj(GridPoint2 treeObstacleCoordinates, Direction direction) {
    super(treeObstacleCoordinates, direction);
  }

  public boolean isMoving() {
    return isEqual(MovementProgress, 1f);
  }

  public void moveUp(GridPoint2 obstacleCoordinate) {
    if (!obstacleCoordinate.equals(incrementedY(coordinates))) {
      DestinationCoordinates.y++;
      MovementProgress = 0f;
    }
    direction = Direction.Up;
  }

  public void moveLeft(GridPoint2 obstacleCoordinate) {
    if (!obstacleCoordinate.equals(decrementedX(coordinates))) {
      DestinationCoordinates.x--;
      MovementProgress = 0f;
    }
    direction = Direction.Left;
  }

  public void moveDown(GridPoint2 obstacleCoordinate) {
    if (!obstacleCoordinate.equals(decrementedY(coordinates))) {
      DestinationCoordinates.y--;
      MovementProgress = 0f;
    }
    direction = Direction.Down;
  }

  public void moveRight(GridPoint2 obstacleCoordinate) {
    if (!obstacleCoordinate.equals(incrementedX(coordinates))) {
      DestinationCoordinates.x++;
      MovementProgress = 0f;
    }
    direction = Direction.Right;

  }

  public void recalcualte_position(float deltaTime) {
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
