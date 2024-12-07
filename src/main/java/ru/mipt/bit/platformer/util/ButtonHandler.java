package ru.mipt.bit.platformer.util;

import java.util.HashMap;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.badlogic.gdx.Gdx;

import ru.mipt.bit.platformer.GameObjects.Bullet;
import ru.mipt.bit.platformer.GameObjects.Direction;
import ru.mipt.bit.platformer.GameObjects.Level;
import ru.mipt.bit.platformer.GameObjects.ShootingObj;
import static com.badlogic.gdx.Input.Keys.*;

@Component
public class ButtonHandler {
    java.util.Map<Integer, Callable<Integer>> button2actionMap;

    @Autowired
    public ButtonHandler() {
        button2actionMap = new HashMap<Integer,Callable<Integer>>();
    }

    public void registerButtonHandler(Integer button, Callable<Integer> handler) {
        button2actionMap.put(button, handler);
    }

    public void handleButtonInputs() {
        var it = button2actionMap.entrySet().iterator();

        while(it.hasNext()) {
            var entry = it.next();
            if (Gdx.input.isKeyPressed(entry.getKey())) {
                Callable<Integer> callable = entry.getValue();
                try {
                    callable.call();    
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void registerDefaultPlayerTankHandlers(Level level) {
        ShootingObj playerTank = level.getPlayerTank();

         Callable<Integer> handlerUp = () -> {
            if (playerTank != null)
                playerTank.move(Direction.Up);
            return 0;
        };

        Callable<Integer> handlerLeft = () -> {
            if (playerTank != null)
                playerTank.move(Direction.Left);
            return 0;
        };

        Callable<Integer> handlerDown = () -> {
            if (playerTank != null)
                playerTank.move(Direction.Down);
            return 0;
        };

        Callable<Integer> handlerRight = () -> {
            if (playerTank != null)
                playerTank.move(Direction.Right);
            return 0;
        };

        Callable<Integer> handlerShoot = () -> {
            if (playerTank != null) {
                Bullet bullet = playerTank.shoot();
                if (bullet != null) {
                    level.handle_obj_creation(bullet);
                }
            }
            return 0;
        };

        registerButtonHandler(UP, handlerUp);
        registerButtonHandler(W, handlerUp);

        registerButtonHandler(LEFT, handlerLeft);
        registerButtonHandler(A, handlerLeft);

        registerButtonHandler(DOWN, handlerDown);
        registerButtonHandler(S, handlerDown);

        registerButtonHandler(RIGHT, handlerRight);
        registerButtonHandler(D, handlerRight);

        registerButtonHandler(SPACE, handlerShoot);
    }
}
