package ru.mipt.bit.platformer.InputsHandlers;

import java.util.HashMap;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.badlogic.gdx.Gdx;

import ru.mipt.bit.platformer.GameObjects.Bullet;
import ru.mipt.bit.platformer.GameObjects.Direction;
import ru.mipt.bit.platformer.GameObjects.Level;
import ru.mipt.bit.platformer.GameObjects.ShootingTank;

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
}
