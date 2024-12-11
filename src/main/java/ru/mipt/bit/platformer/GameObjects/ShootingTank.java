package ru.mipt.bit.platformer.GameObjects;

import static ru.mipt.bit.platformer.Visualizer.GdxGameUtils.*;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.util.CollisionHandler;

public class ShootingTank extends MovableObj implements IShootingObj {
    float ShootingProgress;

    public ShootingTank(CollisionHandler collisionHanler) {
        super(collisionHanler);
    }

    public ShootingTank(CollisionHandler collisionHanler, GridPoint2 Coordinates, Direction direction, GameObjType type) {
        super(collisionHanler, Coordinates, direction, type);
    }

    private boolean isShooting() {
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
