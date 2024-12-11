package ru.mipt.bit.platformer.TankAI;

import java.util.Random;
import java.util.concurrent.Callable;

import ru.mipt.bit.platformer.GameObjects.Bullet;
import ru.mipt.bit.platformer.GameObjects.Direction;
import ru.mipt.bit.platformer.GameObjects.Level;
import ru.mipt.bit.platformer.GameObjects.ShootingTank;


public class TankAI {
    static Random random = new Random();

    public static Direction chooseDirection() {
        int x = random.nextInt(Direction.values().length);
        return Direction.values()[x];
    }

    public static Callable<Integer> getDefaultAITankAction(ShootingTank shootingObj, Level level) {
        final int numbers_of_ticks = 100;
        return () -> {
            if (shootingObj != null) {
                long time = System.currentTimeMillis();
                if (time % numbers_of_ticks == 0) {
                    Bullet bullet = shootingObj.shoot();
                    if (bullet != null) {
                        level.handle_obj_creation(bullet);
                    }
                } else if (time % numbers_of_ticks == 50)
                    shootingObj.move(TankAI.chooseDirection());
            }
            return 0;
        };
    }
}
