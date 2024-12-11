package ru.mipt.bit.platformer.LevelGenerationStrategy;

import ru.mipt.bit.platformer.GameObjects.Level;

public interface Strategy {
    Level generateLevel();
}
