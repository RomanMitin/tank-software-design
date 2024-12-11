package ru.mipt.bit.platformer.GameObjects;

import java.util.concurrent.Callable;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.EventManager.EventType;
import ru.mipt.bit.platformer.InputsHandlers.ButtonHandler;
import ru.mipt.bit.platformer.TankAI.AITanksHandler;
import ru.mipt.bit.platformer.TankAI.TankAI;
import ru.mipt.bit.platformer.EventManager.EventManager;
import ru.mipt.bit.platformer.util.CollisionHandler;

public class Level {
    private ShootingTank playerTank;
    private Array<GameObj> gameObjects;
    CollisionHandler collisionHanler;
    public EventManager eventManager;

    public void handle_obj_creation(GameObj gameObj) {
        gameObjects.add(gameObj);
        eventManager.notify(gameObj, EventType.objCreation);
    }

    public void handle_obj_removal(GameObj gameObj) {
        gameObjects.removeValue(gameObj, false);
        eventManager.notify(gameObj, EventType.objRemoval);
    }

    public Level() {
        this(new GridPoint2(1, 1), new Array<GridPoint2>(), new Array<GridPoint2>());
    }

    public Level(GridPoint2 playerTankPosition, Array<GridPoint2> treePositions, Array<GridPoint2> aiTanksPostions) {
        gameObjects = new Array<>();
        eventManager = new EventManager();
        collisionHanler = new CollisionHandler(eventManager);

        playerTank = new ShootingTank(collisionHanler, playerTankPosition, Direction.Left, GameObjType.PlayerTank);
        gameObjects.add(playerTank);
        
        for(GridPoint2 coord : treePositions) {
            gameObjects.add(new GameObj(coord, Direction.Left));
        }
        
        for(GridPoint2 coord : aiTanksPostions) {
            gameObjects.add(new ShootingTank(collisionHanler, coord, Direction.Right, GameObjType.AITank));
        }
    }

    public void notifyAboutAllObjects() {
        for (GameObj obj : gameObjects) {
            eventManager.notify(obj, EventType.objCreation);
        }
    }

    public void gameLogicTick(float deltaTime) {
        for (GameObj gameObj : gameObjects) {
            gameObj.recalculate_state(deltaTime);
            if (gameObj.getHeath() <= 0) {
                if (gameObj.getType() == GameObjType.PlayerTank)
                    playerTank = null;
                handle_obj_removal(gameObj);
            }
        }
    }

    public Array<GameObj> getGameObjects() {
        return gameObjects;
    }

    public ShootingTank getPlayerTank() {
        return playerTank;
    }
}
