package ru.mipt.bit.platformer.InputsHandlers;

import java.util.concurrent.Callable;

import ru.mipt.bit.platformer.GameObjects.Bullet;
import ru.mipt.bit.platformer.GameObjects.Direction;
import ru.mipt.bit.platformer.GameObjects.Level;
import ru.mipt.bit.platformer.GameObjects.ShootingTank;

public class LevelHandlerGenerator {
    private Level level;
    private ShootingTank playerTank;

    public LevelHandlerGenerator(Level level) {
        this.level = level;
        playerTank = level.getPlayerTank();
    }

    Callable<Integer> getUpHandler() {
        return () -> {
            if (playerTank != null)
                playerTank.move(Direction.Up);
            return 0;
        };
    }

    Callable<Integer> getLeftHandler() {
        return () -> {
            if (playerTank != null)
                playerTank.move(Direction.Left);
            return 0;
        };
    }

    Callable<Integer> getRightHandler() {
        return () -> {
            if (playerTank != null)
                playerTank.move(Direction.Right);
            return 0;
        };
    }

    Callable<Integer> getDownHandler() {
        return () -> {
            if (playerTank != null)
                playerTank.move(Direction.Down);
            return 0;
        };
    }

    Callable<Integer> getShootHandler() {
        return () -> {
            if (playerTank != null) {
                Bullet bullet = playerTank.shoot();
                if (bullet != null) {
                    level.handle_obj_creation(bullet);
                }
            }
            return 0;
        };
    }
}
