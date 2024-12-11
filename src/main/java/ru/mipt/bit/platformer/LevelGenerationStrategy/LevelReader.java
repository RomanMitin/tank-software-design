package ru.mipt.bit.platformer.LevelGenerationStrategy;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.GameObjects.Level;

public class LevelReader implements Strategy {
    private String path_to_level;

    public LevelReader(String path_to_level) {
        this.path_to_level = path_to_level;
    }

    public LevelReader() {
    }

    private Array<GridPoint2> readTrees(JSONArray arr) {
        Array<GridPoint2> result = new Array<>();
        for(int i = 0; i < arr.length(); i++){
            JSONArray treeCoord = arr.getJSONArray(i);
            result.add(new GridPoint2(treeCoord.getInt(0), treeCoord.getInt(1)));
        }     

        return result;
    }

    @Override
    public Level generateLevel() {
        Level level = new Level();
        String fileName = "/home/roman/code/tank-software-design/src/main/resources/level.json"; 
        if (path_to_level != null && !path_to_level.isEmpty()) {
            fileName = path_to_level;
        }

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
}
