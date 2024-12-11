package ru.mipt.bit.platformer.LevelGenerationStrategy;

import ru.mipt.bit.platformer.GameObjects.Level;

public class Context {
 
    private Strategy strategy;
 
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public Level generateLevel() {
        return this.strategy.generateLevel();
    }
}
