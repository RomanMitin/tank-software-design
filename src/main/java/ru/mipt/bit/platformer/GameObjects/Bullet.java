package ru.mipt.bit.platformer.GameObjects;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.util.CollisionHandler;

public class Bullet extends MovableObj {
    public boolean is_collided = false;
    static final public float bullet_damage = 20;
    
    public Bullet(CollisionHandler collisionHanler) {
        super(collisionHanler);
    }
    
    public Bullet(CollisionHandler collisionHanler, GridPoint2 Coordinates, Direction direction, GameObjType type) {
        super(collisionHanler, Coordinates, direction, type);
    }

    @Override
    public void move(Direction moveDirection) {
    }

    @Override
    public void recalculate_state(float deltaTime) {
        if (!is_collided) {
            GridPoint2 destPoint = new GridPoint2(coordinates);
            destPoint.add(direction.getOffset());

            if (collisionHanler.isCollide(destPoint)) {
                GameObj collided_obj = collisionHanler.getCollidedObj(destPoint);
                if (collided_obj != null && collided_obj.getType() != GameObjType.Bullet) {
                    is_collided = true;
                    heath = 0;
                    collided_obj.takeDamage(bullet_damage);
                }
            }
            destinationCoordinates = destPoint;
            have_destination = true;
            super.recalculate_state(deltaTime);
        }
    }
}
