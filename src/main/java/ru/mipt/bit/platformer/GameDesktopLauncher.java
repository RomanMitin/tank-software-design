package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.util.ButtonHandler;
import ru.mipt.bit.platformer.util.TileMovement;
import ru.mipt.bit.platformer.GameObjects.MovableObj;
import ru.mipt.bit.platformer.Visualizer.GameObjDrawable;
import ru.mipt.bit.platformer.Visualizer.MovableObjDrawable;
import ru.mipt.bit.platformer.GameObjects.Direction;
import ru.mipt.bit.platformer.GameObjects.GameObj;
import ru.mipt.bit.platformer.Visualizer.Drawable;

import static com.badlogic.gdx.Input.Keys.*;
import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.*;

import java.util.concurrent.Callable;

public class GameDesktopLauncher implements ApplicationListener {

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;

    private Array<GameObj> gameObjects;
    private Array<Drawable> drawableObjects;

    private ButtonHandler buttonHandler;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        TileMovement tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        MovableObj tank = new MovableObj();
        GameObj tree = new GameObj();
        
        gameObjects = new Array<>();
        gameObjects.add(tank, tree);

        // Texture decodes an image file and loads it into GPU memory, it represents a
        // native resource
        Texture blueTankTexture = new Texture("images/tank_blue.png");

        MovableObjDrawable tankDrawable = new MovableObjDrawable(tank, blueTankTexture, tileMovement);

        Texture greenTreeTexture = new Texture("images/greenTree.png");
        GameObjDrawable treeDrawable = new GameObjDrawable(tree, greenTreeTexture);

        drawableObjects = new Array<>();
        drawableObjects.add((Drawable)tankDrawable, (Drawable)treeDrawable);

        moveRectangleAtTileCenter(groundLayer, treeDrawable.getRectangle(), tree.getCoordinates());

        buttonHandler = new ButtonHandler();

        Callable<Integer> handlerUp = () -> {
            tank.move(tree.getCoordinates(), Direction.Up);
            return null;
        };

        Callable<Integer> handlerLeft = () -> {
            tank.move(tree.getCoordinates(), Direction.Left);
            return null;
        };

        Callable<Integer> handlerDown = () -> {
            tank.move(tree.getCoordinates(), Direction.Down);
            return null;
        };

        Callable<Integer> handlerRight = () -> {
            tank.move(tree.getCoordinates(), Direction.Right);
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

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        buttonHandler.handleButtonInputs();

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
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        for(Drawable drawable : drawableObjects) {
            drawable.dispose();
        }
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
