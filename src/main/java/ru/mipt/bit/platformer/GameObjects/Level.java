package ru.mipt.bit.platformer.GameObjects;

import java.util.concurrent.Callable;

import static com.badlogic.gdx.Input.Keys.*;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.util.AITanksHandler;
import ru.mipt.bit.platformer.util.ButtonHandler;
import ru.mipt.bit.platformer.util.EventManager;
import ru.mipt.bit.platformer.util.TankAI;

public class Level {
    private ShootingObj playerTank;
    private Array<GameObj> gameObjects;
    AITanksHandler aiTanksActions;
    public EventManager eventManager;

    public void registerPlayerTankHandlers(ButtonHandler buttonHandler) {
        Callable<Integer> handlerUp = () -> {
            if (playerTank != null)
                playerTank.move(gameObjects, Direction.Up);
            return 0;
        };

        Callable<Integer> handlerLeft = () -> {
            if (playerTank != null)
                playerTank.move(gameObjects, Direction.Left);
            return 0;
        };

        Callable<Integer> handlerDown = () -> {
            if (playerTank != null)
                playerTank.move(gameObjects, Direction.Down);
            return 0;
        };

        Callable<Integer> handlerRight = () -> {
            if (playerTank != null)
                playerTank.move(gameObjects, Direction.Right);
            return 0;
        };

        Callable<Integer> handlerShoot = () -> {
            Bullet bullet = playerTank.shoot(gameObjects);
            if (bullet != null) {
                gameObjects.add(bullet);
                eventManager.notify(bullet, false);
                Callable<Integer> bulletAction = () -> {
                    bullet.move(gameObjects, null);
                    if (bullet.is_collided) {
                        eventManager.notify(bullet, true);
                        gameObjects.removeValue(bullet, false);
                    }
                    return 0;
                };
                aiTanksActions.registerAction(bulletAction);
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

        playerTank = new ShootingObj(playerTankPosition, Direction.Left, GameObjType.PlayerTank);
        gameObjects.add(playerTank);
        
        for(GridPoint2 coord : treePositions) {
            gameObjects.add(new GameObj(coord, Direction.Left));
        }
        
        for(GridPoint2 coord : aiTanksPostions) {
            gameObjects.add(new ShootingObj(coord, Direction.Right, GameObjType.AITank));
        }
        registerAITanksActions();
    }

    public void gameLogicTick() {
        aiTanksActions.handleActions();

        Array<GameObj> val_to_remove = new Array<>();
        for (GameObj gameObj : gameObjects) {
            if (gameObj.heath <= 0) {
                if (gameObj.type == GameObjType.PlayerTank)
                    playerTank = null;
                val_to_remove.add(gameObj);
                eventManager.notify(gameObj, true);
            }
        }
        gameObjects.removeAll(val_to_remove, false);
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
                    System.err.println(time % tank_number);
                    if (time % tank_number == 0) {
                        Bullet bullet = shootingObj.shoot(gameObjects);
                        if (bullet != null) {
                            gameObjects.add(bullet);
                            eventManager.notify(bullet, false);
                            Callable<Integer> bulletAction = () -> {
                                bullet.move(gameObjects, null);
                                if (bullet.is_collided) {
                                    eventManager.notify(bullet, true);
                                    gameObjects.removeValue(bullet, false);
                                }
                                return 0;
                            };
                            aiTanksActions.registerAction(bulletAction);
                        }
                    }
                    else if (time % tank_number == 53)
                        shootingObj.move(gameObjects, TankAI.chooseDirection());

                    return 0;
                };
                aiTanksActions.registerAction(aitankAction);
            }
        }
    }
}
