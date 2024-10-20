package ru.mipt.bit.platformer.util;

import java.util.Random;

import ru.mipt.bit.platformer.GameObjects.Direction;


public class TankAI {
    static Random random = new Random();

    public static Direction chooseDirection() {
        int x = random.nextInt(Direction.values().length);
        return Direction.values()[x];
    }
}
