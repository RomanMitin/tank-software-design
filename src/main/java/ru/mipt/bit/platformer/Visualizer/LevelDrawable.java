package ru.mipt.bit.platformer.Visualizer;

import com.badlogic.gdx.graphics.Texture;

// import java.util.logging.Level;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.GameObjects.GameObj;
import ru.mipt.bit.platformer.GameObjects.GameObjType;
import ru.mipt.bit.platformer.GameObjects.Level;
import ru.mipt.bit.platformer.GameObjects.MovableObj;
import ru.mipt.bit.platformer.util.TileMovement;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

public class LevelDrawable implements Drawable {
    private Level level;
    private MapRenderer levelRenderer;
    private Array<Drawable> drawableObjects;

    private void InitializeDrawableObjects(Array<GameObj> gameObjects, TileMovement tileMovement, TiledMapTileLayer groundLayer) {
        drawableObjects = new Array<>();

        for(GameObj obj : gameObjects) {
            if (obj.getType() == GameObjType.PlayerTank) {
                Texture blueTankTexture = new Texture("images/tank_blue.png");
                MovableObj tank = (MovableObj)obj;
                MovableObjDrawable tankDrawable = new MovableObjDrawable(tank, blueTankTexture, tileMovement);
                drawableObjects.add((Drawable) tankDrawable);
                moveRectangleAtTileCenter(groundLayer, tankDrawable.getRectangle(), tank.getCoordinates());
                
            } else if (obj.getType() == GameObjType.Tree) {
                GameObj tree = (GameObj)obj;
                Texture greenTreeTexture = new Texture("images/greenTree.png");
                GameObjDrawable treeDrawable = new GameObjDrawable(tree, greenTreeTexture);
                drawableObjects.add((Drawable) treeDrawable);
                moveRectangleAtTileCenter(groundLayer, treeDrawable.getRectangle(), tree.getCoordinates());
            }
        }


    }

    public LevelDrawable(Level level, Batch batch) {
        this.level = level;
        levelRenderer = createSingleLayerMapRenderer(level.getTiledMap(), batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level.getTiledMap());
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
        level.getTiledMap().dispose();
    }
    
}
