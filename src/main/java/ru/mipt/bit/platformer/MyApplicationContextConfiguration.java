package ru.mipt.bit.platformer;

import ru.mipt.bit.platformer.GameObjects.Level;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ru.mipt.bit.platformer.LevelGenerationStrategy.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.Visualizer.LevelDrawable;


@Configuration
@ComponentScan 
public class MyApplicationContextConfiguration { 

    @Bean
    @Scope("prototype")
    public Level Level(boolean read_level) {
        var ctx = new ru.mipt.bit.platformer.LevelGenerationStrategy.Context();

        if (read_level) {
            ctx.setStrategy(new LevelReader());
        } else {
            ctx.setStrategy(new RandomLevelGenerator(5, 5));
        }

        return ctx.generateLevel();
    }

    @Bean
    @Scope("prototype")
    public LevelDrawable LevelDrawable(Level level, Batch batch) {
       return new ru.mipt.bit.platformer.Visualizer.LevelDrawable(level, batch);
    }

}

