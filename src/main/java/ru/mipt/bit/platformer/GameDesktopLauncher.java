package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ru.mipt.bit.platformer.util.ButtonHandler;
import ru.mipt.bit.platformer.Visualizer.LevelDrawable;
import ru.mipt.bit.platformer.GameObjects.Level;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class GameDesktopLauncher implements ApplicationListener {
    static ApplicationContext ctx;

    private Batch batch;

    private Level level;
    private LevelDrawable levelDrawable;

    @Autowired
    private ButtonHandler buttonHandler;

    @Override
    public void create() {
        batch = new SpriteBatch();
        
        level = ctx.getBean(Level.class, false);

        buttonHandler.registerDefaultPlayerTankHandlers(level);
        levelDrawable = ctx.getBean(LevelDrawable.class, level, batch);
        level.notifyAboutAllObjects();
        levelDrawable.registerPlayerTankHandlers(buttonHandler);
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        buttonHandler.handleButtonInputs();
        level.gameLogicTick(deltaTime);

        levelDrawable.drawTexture(batch, deltaTime);
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
        levelDrawable.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        ctx = new AnnotationConfigApplicationContext(MyApplicationContextConfiguration.class);
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(ctx.getBean(GameDesktopLauncher.class), config);
    }
}
