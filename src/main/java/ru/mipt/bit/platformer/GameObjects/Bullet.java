package ru.mipt.bit.platformer.GameObjects;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

public class Bullet extends MovableObj {
    public boolean is_collided = false;
    static final public float bullet_damage = 20;
    
    public Bullet() {
        super();
    }
    
    public Bullet(GridPoint2 Coordinates, Direction direction, GameObjType type) {
        super(Coordinates, direction, type);
    }

    @Override
    public void move(Array<GameObj> obstacleCoordinates, Direction moveDirection) {
        if (this.isMoving() && !is_collided) {
            GridPoint2 destPoint = new GridPoint2(coordinates);
            destPoint.add(direction.getOffset());

            if (isCollide(obstacleCoordinates, destPoint)) {
                GameObj collided_obj = getCollidedObj(obstacleCoordinates, destPoint);
                if (collided_obj != null && collided_obj.getType() != GameObjType.Bullet) {
                    is_collided = true;
                    collided_obj.heath -= bullet_damage;
                }
            }
            destinationCoordinates = destPoint;
            MovementProgress = 0f;
        }
    }
}
