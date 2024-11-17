package ru.mipt.bit.platformer.GameObjects;

import java.util.concurrent.Callable;

import static com.badlogic.gdx.Input.Keys.*;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.EventManager.EventType;
import ru.mipt.bit.platformer.EventManager.EventManager;
import ru.mipt.bit.platformer.util.AITanksHandler;
import ru.mipt.bit.platformer.util.ButtonHandler;
import ru.mipt.bit.platformer.util.CollisionHandler;
import ru.mipt.bit.platformer.util.TankAI;

public class Level {
    private ShootingObj playerTank;
    private Array<GameObj> gameObjects;
    AITanksHandler aiTanksActions;
    CollisionHandler collisionHanler;
    public EventManager eventManager;

    private void handle_obj_creation(GameObj gameObj) {
        gameObjects.add(gameObj);
        eventManager.notify(gameObj, EventType.objCreation);
    }

    private void handle_obj_removal(GameObj gameObj) {
        gameObjects.removeValue(gameObj, false);
        eventManager.notify(gameObj, EventType.objRemoval);
    }

    public void registerPlayerTankHandlers(ButtonHandler buttonHandler) {
        Callable<Integer> handlerUp = () -> {
            if (playerTank != null)
                playerTank.move(Direction.Up);
            return 0;
        };

        Callable<Integer> handlerLeft = () -> {
            if (playerTank != null)
                playerTank.move(Direction.Left);
            return 0;
        };

        Callable<Integer> handlerDown = () -> {
            if (playerTank != null)
                playerTank.move(Direction.Down);
            return 0;
        };

        Callable<Integer> handlerRight = () -> {
            if (playerTank != null)
                playerTank.move(Direction.Right);
            return 0;
        };

        Callable<Integer> handlerShoot = () -> {
            if (playerTank != null) {
                Bullet bullet = playerTank.shoot(gameObjects);
                if (bullet != null) {
                    handle_obj_creation(bullet);
                }
            }
            return 0;
        };

        buttonHandler.registerButtonHandler(UP, handlerUp);
        buttonHandler.registerButtonHandler(W, handlerUp);

        buttonHandler.registerButtonHandler(LEFT, handlerLeft);
        buttonHandler.registerButtonHandler(A, handlerLeft);

        buttonHandler.registerButtonHandler(DOWN, handlerDown);
        buttonHandler.registerButtonHandler(S, handlerDown);

        buttonHandler.registerButtonHandler(RIGHT, handlerRight);
        buttonHandler.registerButtonHandler(D, handlerRight);

        buttonHandler.registerButtonHandler(SPACE, handlerShoot);
    }

    public Level() {
        this(new GridPoint2(1, 1), new Array<GridPoint2>(), new Array<GridPoint2>());
    }

    public Level(GridPoint2 playerTankPosition, Array<GridPoint2> treePositions, Array<GridPoint2> aiTanksPostions) {
        gameObjects = new Array<>();
        eventManager = new EventManager();
        aiTanksActions = new AITanksHandler();
        collisionHanler = new CollisionHandler(eventManager);

        playerTank = new ShootingObj(collisionHanler, playerTankPosition, Direction.Left, GameObjType.PlayerTank);
        gameObjects.add(playerTank);
        
        for(GridPoint2 coord : treePositions) {
            gameObjects.add(new GameObj(coord, Direction.Left));
        }
        
        for(GridPoint2 coord : aiTanksPostions) {
            gameObjects.add(new ShootingObj(collisionHanler, coord, Direction.Right, GameObjType.AITank));
        }
        registerAITanksActions();
    }

    public void notifyAboutAllObjects() {
        for (GameObj obj : gameObjects) {
            eventManager.notify(obj, EventType.objCreation);
        }
    }

    public void gameLogicTick() {
        aiTanksActions.handleActions();

        for (GameObj gameObj : gameObjects) {
            if (gameObj.heath <= 0) {
                if (gameObj.type == GameObjType.PlayerTank)
                    playerTank = null;
                handle_obj_removal(gameObj);
            }
        }
    }

    public Array<GameObj> getGameObjects() {
        return gameObjects;
    }

    private void registerAITanksActions() {
        int tank_number = 101;
        for (GameObj gameObj : gameObjects) {
            if (gameObj.type == GameObjType.AITank) {
                ShootingObj shootingObj = (ShootingObj)gameObj;
                Callable<Integer> aitankAction = () -> {
                    long time = System.currentTimeMillis();
                    if (time % tank_number == 0) {
                        Bullet bullet = shootingObj.shoot(gameObjects);
                        if (bullet != null) {
                            handle_obj_creation(bullet);
                        }
                    }
                    else if (time % tank_number == 53)
                        shootingObj.move(TankAI.chooseDirection());

                    return 0;
                };
                aiTanksActions.registerAction(aitankAction);
            }
        }
    }
}
