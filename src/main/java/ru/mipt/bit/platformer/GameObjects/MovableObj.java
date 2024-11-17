package ru.mipt.bit.platformer.GameObjects;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.util.CollisionHandler;
import ru.mipt.bit.platformer.util.GameConstants;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.Visualizer.GdxGameUtils.*;

public class MovableObj extends GameObj{
  static public float timeBetweenMoves = 0.4f;

  GridPoint2 destinationCoordinates;
  float MovementProgress;
  CollisionHandler collisionHanler;
  boolean have_destination = false;

  public MovableObj(CollisionHandler collisionHanler) {
    this(collisionHanler, new GridPoint2(1, 1), Direction.Left, GameObjType.PlayerTank);
  }  

  public MovableObj(CollisionHandler collisionHanler, GridPoint2 Coordinates, Direction direction, GameObjType type) {
    super(Coordinates, direction, type);
    this.collisionHanler = collisionHanler;
    destinationCoordinates = new GridPoint2(Coordinates);
    MovementProgress = 1f;
  }

  public boolean isMoving() {
    return isEqual(MovementProgress, 1f);
  }

  public void move(Direction moveDirection) {
    if (!have_destination) {
      GridPoint2 destPoint = new GridPoint2(coordinates);
      destPoint.add(moveDirection.getOffset());

      if (!collisionHanler.isCollide(destPoint)) {
        destinationCoordinates = destPoint;
        have_destination = true;
        MovementProgress = 0;
      }
      direction = moveDirection;
    }
  }

  public void recalculate_position(float deltaTime) {
    if (have_destination) {
      MovementProgress = continueProgress(MovementProgress, deltaTime, timeBetweenMoves);
      if (this.isMoving()) {
        coordinates.set(destinationCoordinates);
        MovementProgress = 0f;
        have_destination = false;
      }
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
