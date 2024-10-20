package ru.mipt.bit.platformer.util;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.IntStream;

import com.badlogic.gdx.math.GridPoint2;

import org.json.JSONArray;
import org.json.JSONObject;

import com.badlogic.gdx.utils.Array;
import ru.mipt.bit.platformer.GameObjects.Level;

public class LevelInitializer {
    
    private static Array<GridPoint2> readTrees(JSONArray arr) {
        Array<GridPoint2> result = new Array<>();
        for(int i = 0; i < arr.length(); i++){
            JSONArray treeCoord = arr.getJSONArray(i);
            result.add(new GridPoint2(treeCoord.getInt(0), treeCoord.getInt(1)));
        }     

        return result;
    }

    public static Level readLevel() {
        Level level = new Level();
        String fileName = "/home/roman/code/tank-software-design/src/main/resources/level.json"; 
        try {
            String text = new String(Files.readAllBytes(Paths.get(fileName)), StandardCharsets.UTF_8);
            JSONObject obj = new JSONObject(text);
            JSONArray treesArr = obj.getJSONArray("trees");
            Array<GridPoint2> treePositions = readTrees(treesArr);
            
            JSONArray playerCoord = obj.getJSONArray("player");
            GridPoint2 tankPositions = new GridPoint2(playerCoord.getInt(0), playerCoord.getInt(1));

            level = new Level(tankPositions, treePositions, new Array<>());

        } catch (Exception e) {
            System.err.println("Error when reading level: " + e.getMessage());
            System.exit(-1);
        }

        return level;
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

    public static Level generateRandomLevel(int treeNum, int tankNum) {
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
