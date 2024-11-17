package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;


import ru.mipt.bit.platformer.EventManager.EventManager;
import ru.mipt.bit.platformer.EventManager.EventType;
import ru.mipt.bit.platformer.EventManager.Listener;
import ru.mipt.bit.platformer.GameObjects.GameObj;
import ru.mipt.bit.platformer.GameObjects.GameObjType;
import ru.mipt.bit.platformer.GameObjects.MovableObj;

public class CollisionHandler implements Listener {
    private Array<GameObj> gameObjects;

    public CollisionHandler(EventManager manager) {
        gameObjects = new Array<>();
        manager.subscribe(this);
    }

    @Override
    public void update(GameObj gameObj, EventType type) {
        switch (type) {
            case objCreation:
                gameObjects.add(gameObj);
                break;
            case objRemoval:
                gameObjects.removeValue(gameObj, false);
                break;
            default:
                System.err.println("Unknown Event Type");
                break;
        }
    }

    public GameObj getCollidedObj(GridPoint2 point) {
        for (GameObj obj : gameObjects) {
            if (obj.getType() != GameObjType.Tree) {
                MovableObj movableObj = (MovableObj) obj;
                if (movableObj.getDestinationCoordinates().equals(point)) {
                    return movableObj;
                }
            }
            if (obj.getCoordinates().equals(point)) {
                return obj;
            }
        }

        return null;
    }

    public boolean isCollide(GridPoint2 point) {
        if (point.x >= GameConstants.levelWidth || point.x < 0)
            return true;
        if (point.y >= GameConstants.levelHight || point.y < 0)
            return true;

        return getCollidedObj(point) != null;
    }

}
