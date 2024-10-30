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

import java.util.concurrent.Callable;

import ru.mipt.bit.platformer.GameObjects.Direction;
import ru.mipt.bit.platformer.GameObjects.GameObj;
import ru.mipt.bit.platformer.GameObjects.Level;
import ru.mipt.bit.platformer.GameObjects.MovableObj;
import ru.mipt.bit.platformer.util.ButtonHandler;
import ru.mipt.bit.platformer.util.TileMovement;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class LevelDrawable implements Drawable {
    private Level level;
    private MapRenderer levelRenderer;
    private Array<Drawable> drawableObjects;
    private TiledMap tiledMap;


    private void InitializeDrawableObjects(Array<GameObj> gameObjects, TileMovement tileMovement, TiledMapTileLayer groundLayer) {
        drawableObjects = new Array<>();

        for(GameObj obj : gameObjects) {
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
                default:
                    System.err.println("Unknown object type");
                    break;
            }
        }


    }

    public LevelDrawable(Level level, Batch batch) {
        this.level = level;
        tiledMap = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(tiledMap, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);


        InitializeDrawableObjects(level.getGameObjects(), tileMovement, groundLayer);

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

    public void registerPlayerTankHandlers(ButtonHandler buttonHandler) {
        Callable<Integer> ToggleHealthBarHandler = () -> {
            if (DrawableHealthDecorator.is_ready_for_revert)
                DrawableHealthDecorator.revert_health_visible = true;
            System.err.println("hello");
            return 0;
        };


        buttonHandler.registerButtonHandler(L, ToggleHealthBarHandler);
    }
    
}
