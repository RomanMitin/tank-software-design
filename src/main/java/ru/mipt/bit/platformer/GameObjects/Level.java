package ru.mipt.bit.platformer.GameObjects;

import java.util.concurrent.Callable;

import static com.badlogic.gdx.Input.Keys.*;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.util.AITanksHandler;
import ru.mipt.bit.platformer.util.ButtonHandler;
import ru.mipt.bit.platformer.util.TankAI;

public class Level {
    private MovableObj playerTank;
    private Array<GameObj> gameObjects;

    public void registerPlayerTankHandlers(ButtonHandler buttonHandler) {
        Callable<Integer> handlerUp = () -> {
            playerTank.move(gameObjects, Direction.Up);
            return 0;
        };

        Callable<Integer> handlerLeft = () -> {
            playerTank.move(gameObjects, Direction.Left);
            return 0;
        };

        Callable<Integer> handlerDown = () -> {
            playerTank.move(gameObjects, Direction.Down);
            return 0;
        };

        Callable<Integer> handlerRight = () -> {
            playerTank.move(gameObjects, Direction.Right);
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
    }

    public Level() {
        this(new GridPoint2(1, 1), new Array<GridPoint2>(), new Array<GridPoint2>());
    }

    public Level(GridPoint2 playerTankPosition, Array<GridPoint2> treePositions, Array<GridPoint2> aiTanksPostions) {
        gameObjects = new Array<>();

        playerTank = new MovableObj(playerTankPosition, Direction.Left, GameObjType.PlayerTank);
        gameObjects.add(playerTank);

        for(GridPoint2 coord : treePositions) {
            gameObjects.add(new GameObj(coord, Direction.Left));
        }
        
        for(GridPoint2 coord : aiTanksPostions) {
            gameObjects.add(new MovableObj(coord, Direction.Right, GameObjType.AITank));
        }
    }

    public Array<GameObj> getGameObjects() {
        return gameObjects;
    }

    public void registerAITanksActions(AITanksHandler aiTanksActions) {
        for (GameObj gameObj : gameObjects) {
            if (gameObj.type == GameObjType.AITank) {
                MovableObj movableObj = (MovableObj)gameObj;
                Callable<Integer> aitankAction = () -> {
                    movableObj.move(gameObjects, TankAI.chooseDirection());
                    return 0;
                };

                // aiTanksActions.add
                aiTanksActions.registerAction(aitankAction);
            }
        }
    }
}
