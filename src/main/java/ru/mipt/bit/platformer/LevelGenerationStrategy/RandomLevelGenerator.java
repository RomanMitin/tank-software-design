package ru.mipt.bit.platformer.LevelGenerationStrategy;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.IntStream;

import com.badlogic.gdx.math.GridPoint2;

import com.badlogic.gdx.utils.Array;
import ru.mipt.bit.platformer.GameObjects.Level;
import ru.mipt.bit.platformer.util.GameConstants;

public class RandomLevelGenerator implements Strategy{
    private int treeNum;
    private int tankNum;

    public RandomLevelGenerator(int treeNum, int tankNum) {
        this.treeNum = treeNum;
        this.tankNum = tankNum;
    }

    private static boolean CheckCollision(Array<GridPoint2> treePostions, GridPoint2 newPosition) {
        for (GridPoint2 treePos : treePostions) {
            if (treePos.equals(newPosition)) {
                return true;
            }
        }

        return false;
    }

    private static Array<GridPoint2> generateGridArray(int num_item) {
        Random random = new Random();
        IntStream stream = random.ints(0, GameConstants.levelWidth * GameConstants.levelHight);
        var it = stream.iterator();

        Array<GridPoint2> positions = new Array<>();
        int numGenerated = 0;
        while (numGenerated < num_item) {
            GridPoint2 newPosition = new GridPoint2(it.next() % GameConstants.levelWidth, it.next() % GameConstants.levelHight);
            if (!CheckCollision(positions, newPosition)) {
                positions.add(newPosition);
                ++numGenerated;
            }
        }

        return positions;
    }

    @Override
    public Level generateLevel() {
        Array<GridPoint2> generatedPositions = generateGridArray(1 + treeNum + tankNum);
        Array<GridPoint2> treePositions  = new Array<>();
        for (int i = 0; i < treeNum; i++) {
            treePositions.add(generatedPositions.get(i + 1));
        }
        Array<GridPoint2> aiTanksPositions  = new Array<>();
        for (int i = 0; i < tankNum; i++) {
            aiTanksPositions.add(generatedPositions.get(i + 1 + treeNum));
        }
        return new Level(generatedPositions.first(), treePositions, aiTanksPositions);
    }
}
