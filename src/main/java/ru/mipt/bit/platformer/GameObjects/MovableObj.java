package ru.mipt.bit.platformer.GameObjects;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.util.GameConstants;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

import static com.badlogic.gdx.math.MathUtils.isEqual;

public class MovableObj extends GameObj{
  static public float timeBetweenMoves = 0.4f;

  GridPoint2 destinationCoordinates;
  float MovementProgress;

  public MovableObj() {
    this(new GridPoint2(1, 1), Direction.Left, GameObjType.PlayerTank);
  }  

  public MovableObj(GridPoint2 Coordinates, Direction direction, GameObjType type) {
    super(Coordinates, direction, type);
    destinationCoordinates = new GridPoint2(Coordinates);
    MovementProgress = 1f;
  }

  public boolean isMoving() {
    return isEqual(MovementProgress, 1f);
  }

  protected GameObj getCollidedObj(Array<GameObj> obstacleCoordinate, GridPoint2 point) {
    for (GameObj obj : obstacleCoordinate) {
      if (obj.getType() != GameObjType.Tree) {
        MovableObj movableObj = (MovableObj)obj;
        if (movableObj.destinationCoordinates.equals(point)) {
          return movableObj;
        }
      }
      if (obj.getCoordinates().equals(point)) {
        return obj;
      }
    }

    return null;
  }

  protected boolean isCollide(Array<GameObj> obstacleCoordinate, GridPoint2 point) {
    if (point.x >= GameConstants.levelWidth || point.x < 0)
      return true;
    if (point.y >= GameConstants.levelHight || point.y < 0)
      return true;

    return getCollidedObj(obstacleCoordinate, point) != null;
  }

  public void move(Array<GameObj> obstacleCoordinates, Direction moveDirection) {
    if (this.isMoving()) {
      GridPoint2 destPoint = new GridPoint2(coordinates);
      destPoint.add(moveDirection.getOffset());

      if (!isCollide(obstacleCoordinates, destPoint)) {
        destinationCoordinates = destPoint;
        MovementProgress = 0f;
      }
      direction = moveDirection;
    }
  }

  public void recalculate_position(float deltaTime) {
    MovementProgress = continueProgress(MovementProgress, deltaTime, timeBetweenMoves);
    if (isEqual(MovementProgress, 1f)) {
      coordinates.set(destinationCoordinates);
    }
  }

  public GridPoint2 getDestinationCoordinates() {
    return destinationCoordinates;
  }

  public float getMovementProgress() {
    return MovementProgress;
  }

  public Direction getDirection() {
    return direction;
  }
}
