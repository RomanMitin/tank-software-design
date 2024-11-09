package ru.mipt.bit.platformer.util;


import com.badlogic.gdx.utils.Array;

import ru.mipt.bit.platformer.GameObjects.GameObj;

public class EventManager {
    private Array<Listener> listeners = new Array<>();

    public void subscribe(Listener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(Listener listener) {
        listeners.removeValue(listener, false);
    }

    public void notify(GameObj gameObj, boolean is_removed) {
        for (Listener listener : listeners) {
            listener.update(gameObj, is_removed);
        }
    }
    
}
