package ru.mipt.bit.platformer.EventManager;

import ru.mipt.bit.platformer.GameObjects.GameObj;

public interface Listener {
    void update(GameObj gameObj, EventType type);
}
