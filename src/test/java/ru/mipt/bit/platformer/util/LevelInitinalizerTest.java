package ru.mipt.bit.platformer.util;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.GameObjects.GameObj;
import ru.mipt.bit.platformer.GameObjects.GameObjType;
import ru.mipt.bit.platformer.GameObjects.Level;
import ru.mipt.bit.platformer.LevelGenerationStrategy.RandomLevelGenerator;

import org.junit.jupiter.api.Assertions;

public class LevelInitinalizerTest {
    
    @Test
    public void emptyLevelTest() {        
        // Execution
        Level level = (new RandomLevelGenerator(0, 0)).generateLevel();
        Array<GameObj> gameObjects = level.getGameObjects();

        // Assertion
        Assertions.assertNotNull(level);
        Assertions.assertNotNull(gameObjects);
        Assertions.assertTrue(gameObjects.size == 1);
        Assertions.assertTrue(gameObjects.first().getType() == GameObjType.PlayerTank);
    }

    @Test
    public void SimpleLevelTest() {        
        // Execution
        Level level = (new RandomLevelGenerator(5, 2)).generateLevel();
        Array<GameObj> gameObjects = level.getGameObjects();

        // Assertion
        Assertions.assertNotNull(level);
        Assertions.assertNotNull(gameObjects);
        Assertions.assertTrue(gameObjects.size == 8);

        int aiTankCounter = 0;
        int treeCounter = 0;
        int playerTankCounter = 0;
        for (GameObj gameObj : gameObjects) {
            switch (gameObj.getType()) {
                case AITank:
                    aiTankCounter++;
                    break;
                case PlayerTank:
                    treeCounter++;
                    break;
                case Tree:
                playerTankCounter++;
                    break;
                default:
                    break;
            }
        }

        Assertions.assertEquals(2, aiTankCounter);
        Assertions.assertEquals(5, treeCounter);
        Assertions.assertEquals(1, playerTankCounter);
    }
}
