package ru.mipt.bit.platformer.Configuration;

import ru.mipt.bit.platformer.GameObjects.Level;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ru.mipt.bit.platformer.GameObjects.ShootingObj;
import ru.mipt.bit.platformer.util.CollisionHandler;
import ru.mipt.bit.platformer.util.LevelInitializer;
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.Visualizer.LevelDrawable;


@Configuration
@ComponentScan  // (1)
public class MyApplicationContextConfiguration {  // (1)

    @Bean
    @Scope("prototype")
    public Level Level(boolean read_level) {
        if (read_level) {
            return LevelInitializer.readLevel();
        } else {
            return LevelInitializer.generateRandomLevel(5, 5);
        }
    }

    @Bean
    @Scope("prototype")
    public LevelDrawable LevelDrawable(Level level, Batch batch) {
       return new ru.mipt.bit.platformer.Visualizer.LevelDrawable(level, batch);
    }

}

