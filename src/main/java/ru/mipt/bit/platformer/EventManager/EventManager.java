package ru.mipt.bit.platformer.EventManager;

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

    public void notify(GameObj gameObj, EventType type) {
        for (Listener listener : listeners) {
            listener.update(gameObj, type);
        }
    }
    
}
