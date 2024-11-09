package ru.mipt.bit.platformer.util;

import ru.mipt.bit.platformer.GameObjects.GameObj;

public interface Listener {
    void update(GameObj gameObj, boolean is_removed);
}
