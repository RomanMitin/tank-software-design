package ru.mipt.bit.platformer.GameObjects;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class ShootingObj extends MovableObj {
    float ShootingProgress;

    public ShootingObj() {
        super();
    }

    public ShootingObj(GridPoint2 Coordinates, Direction direction, GameObjType type) {
        super(Coordinates, direction, type);
    }

    public boolean isShooting() {
        return ShootingProgress >= 0.3f;
    }

    public Bullet shoot(Array<GameObj> obstacleCoordinates) {
        if (this.isShooting()) {
            ShootingProgress = 0;
            GridPoint2 destPoint = new GridPoint2(coordinates);
            destPoint.add(direction.getOffset());

            if (!isCollide(obstacleCoordinates, destPoint)) {
                Bullet bullet = new Bullet(destPoint, direction, GameObjType.Bullet);
                return bullet;
            } else {
                GameObj collided_obj = getCollidedObj(obstacleCoordinates, destPoint);
                if (collided_obj != null) {
                    if (!collided_obj.equals(this)) {
                        collided_obj.heath -= Bullet.bullet_damage;
                    } else {
                        Bullet bullet = new Bullet(destPoint, direction, GameObjType.Bullet);
                        return bullet;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public void recalculate_position(float deltaTime) {
        ShootingProgress = continueProgress(ShootingProgress, deltaTime, timeBetweenMoves);
        super.recalculate_position(deltaTime);
    }
    
}
