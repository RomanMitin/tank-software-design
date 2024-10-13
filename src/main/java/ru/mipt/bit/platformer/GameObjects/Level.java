package ru.mipt.bit.platformer.GameObjects;

import java.util.concurrent.Callable;

import com.badlogic.gdx.maps.tiled.TiledMap;
import static com.badlogic.gdx.Input.Keys.*;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.util.ButtonHandler;

public class Level {
    private TiledMap tiledMap;
    private MovableObj tank;
    private Array<GameObj> gameObjects;

    public void registerPlayerTankHandlers(ButtonHandler buttonHandler) {
        Callable<Integer> handlerUp = () -> {
            tank.move(gameObjects, Direction.Up);
            return null;
        };

        Callable<Integer> handlerLeft = () -> {
            tank.move(gameObjects, Direction.Left);
            return null;
        };

        Callable<Integer> handlerDown = () -> {
            tank.move(gameObjects, Direction.Down);
            return null;
        };

        Callable<Integer> handlerRight = () -> {
            tank.move(gameObjects, Direction.Right);
            return null;
        };

        buttonHandler.registerButtonHandler(UP, handlerUp);
        buttonHandler.registerButtonHandler(W, handlerUp);

        buttonHandler.registerButtonHandler(LEFT, handlerLeft);
        buttonHandler.registerButtonHandler(A, handlerLeft);

        buttonHandler.registerButtonHandler(DOWN, handlerDown);
        buttonHandler.registerButtonHandler(S, handlerDown);

        buttonHandler.registerButtonHandler(RIGHT, handlerRight);
        buttonHandler.registerButtonHandler(D, handlerRight);
    }

    public Level() {
        this(new GridPoint2(1, 1), new Array<GridPoint2>());
    }

    public Level(GridPoint2 playerTankPosition, Array<GridPoint2> treePositions) {
        tiledMap = new TmxMapLoader().load("level.tmx");
        gameObjects = new Array<>();

        tank = new MovableObj(playerTankPosition, Direction.Left);
        gameObjects.add(tank);

        for(GridPoint2 coord : treePositions) {
            gameObjects.add(new GameObj(coord, Direction.Left));
        }
    }
    
    public TiledMap getTiledMap() {
        return tiledMap;
    }

    public Array<GameObj> getGameObjects() {
        return gameObjects;
    }
}
