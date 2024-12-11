package ru.mipt.bit.platformer.Visualizer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

// import java.util.logging.Level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import static com.badlogic.gdx.Input.Keys.*;
import static ru.mipt.bit.platformer.Visualizer.GdxGameUtils.*;

import java.util.concurrent.Callable;

import ru.mipt.bit.platformer.EventManager.Listener;
import ru.mipt.bit.platformer.EventManager.EventType;
import ru.mipt.bit.platformer.GameObjects.Direction;
import ru.mipt.bit.platformer.GameObjects.GameObj;
import ru.mipt.bit.platformer.GameObjects.GameObjType;
import ru.mipt.bit.platformer.GameObjects.Level;
import ru.mipt.bit.platformer.GameObjects.MovableObj;
import ru.mipt.bit.platformer.InputsHandlers.ButtonHandler;
import ru.mipt.bit.platformer.util.TileMovement;

public class LevelDrawable implements Drawable, Listener {
    private Level level;
    private MapRenderer levelRenderer;
    private Array<Drawable> drawableObjects;
    private TiledMap tiledMap;
    TiledMapTileLayer groundLayer;
    TileMovement tileMovement;

    public LevelDrawable(Level level, Batch batch) {
        this.level = level;
        level.eventManager.subscribe(this);
        tiledMap = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(tiledMap, batch);
        groundLayer = getSingleLayer(tiledMap);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
        drawableObjects = new Array<>();
    }

    @Override
    public void drawTexture(Batch batch, float deltaTime) {
        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        for (Drawable drawable : drawableObjects) {
            drawable.drawTexture(batch, deltaTime);
        }

        // submit all drawing requests
        batch.end();
    }

    @Override
    public void dispose() {
        for(Drawable drawable : drawableObjects) {
            drawable.dispose();
        }
        tiledMap.dispose();
    }

    private void handle_obj_creation(GameObj obj) {
        switch (obj.getType()) {
            case PlayerTank:
                Texture blueTankTexture = new Texture("images/tank_blue.png");
                MovableObj tank = (MovableObj) obj;
                MovableObjDrawable tankDrawable = new MovableObjDrawable(tank, blueTankTexture, tileMovement);
                drawableObjects.add((Drawable) new DrawableHealthDecorator(tankDrawable));
                moveRectangleAtTileCenter(groundLayer, tankDrawable.getRectangle(), tank.getCoordinates());
                break;
            case AITank:
                Texture aiTankTexture = new Texture("images/tank_blue.png");
                MovableObj aiTank = (MovableObj) obj;
                MovableObjDrawable aiTankDrawable = new MovableObjDrawable(aiTank, aiTankTexture, tileMovement);
                drawableObjects.add((Drawable) new DrawableHealthDecorator(aiTankDrawable));
                moveRectangleAtTileCenter(groundLayer, aiTankDrawable.getRectangle(), aiTank.getCoordinates());
                break;
            case Tree:
                GameObj tree = (GameObj) obj;
                Texture greenTreeTexture = new Texture("images/greenTree.png");
                GameObjDrawable treeDrawable = new GameObjDrawable(tree, greenTreeTexture);
                drawableObjects.add((Drawable) treeDrawable);                    
                moveRectangleAtTileCenter(groundLayer, treeDrawable.getRectangle(), tree.getCoordinates());
                break;
            case Bullet:
                Texture bulletTexture = new Texture("images/bullet.png");
                MovableObj bullet = (MovableObj) obj;
                MovableObjDrawable bulletDrawable = new MovableObjDrawable(bullet, bulletTexture, tileMovement);
                drawableObjects.add((Drawable) bulletDrawable);
                moveRectangleAtTileCenter(groundLayer, bulletDrawable.getRectangle(), bullet.getCoordinates());
                break;
            default:
                System.err.println("Unknown object type");
                break;
        }
    }

    @Override
    public void update(GameObj gameObj, EventType type) {
        switch (type) {
            case objCreation:
                handle_obj_creation(gameObj);
                break;
            case objRemoval:
            for (Drawable drawable : drawableObjects) {
                if (drawable.isGameObj(gameObj)) {
                    drawable.dispose();
                    drawableObjects.removeValue(drawable, false);
                    break;
                }
            }
            break;
            default:
                System.err.println("Unknown Event Type");
                break;
        }
    }

}
