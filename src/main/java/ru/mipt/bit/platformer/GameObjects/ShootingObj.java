package ru.mipt.bit.platformer.GameObjects;

import static ru.mipt.bit.platformer.Visualizer.GdxGameUtils.*;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.util.CollisionHandler;

public class ShootingObj extends MovableObj {
    float ShootingProgress;

    public ShootingObj(CollisionHandler collisionHanler) {
        super(collisionHanler);
    }

    public ShootingObj(CollisionHandler collisionHanler, GridPoint2 Coordinates, Direction direction, GameObjType type) {
        super(collisionHanler, Coordinates, direction, type);
    }

    public boolean isShooting() {
        return ShootingProgress >= 1f;
    }

    public Bullet shoot() {
        if (this.isShooting()) {
            ShootingProgress = 0;
            Bullet bullet = new Bullet(collisionHanler, new GridPoint2(destinationCoordinates), direction, GameObjType.Bullet);
            return bullet;
        }

        return null;
    }

    @Override
    public void recalculate_state(float deltaTime) {
        ShootingProgress = continueProgress(ShootingProgress, deltaTime, timeBetweenMoves);
        super.recalculate_state(deltaTime);
    }
    
}
