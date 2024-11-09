package ru.mipt.bit.platformer.util;

import java.util.HashMap;
import java.util.concurrent.Callable;

import com.badlogic.gdx.Gdx;

public class ButtonHandler {
    java.util.Map<Integer, Callable<Integer>> button2actionMap;

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
                    System.out.println("EXception occurs in handleButtonInputs");
                }
            }
        }
    }
}
