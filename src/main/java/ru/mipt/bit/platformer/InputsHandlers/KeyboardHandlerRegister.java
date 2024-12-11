package ru.mipt.bit.platformer.InputsHandlers;

import java.util.concurrent.Callable;

import ru.mipt.bit.platformer.GameObjects.Level;

import static com.badlogic.gdx.Input.Keys.*;

public class KeyboardHandlerRegister {

    public static void registerHandlerForInput(Level level, ButtonHandler buttonHandler) {
        LevelHandlerGenerator levelInputHandler = new LevelHandlerGenerator(level);

        Callable<Integer> handlerUp = levelInputHandler.getUpHandler();

        buttonHandler.registerButtonHandler(UP, handlerUp);
        buttonHandler.registerButtonHandler(W, handlerUp);

        Callable<Integer> handlerLeft = levelInputHandler.getLeftHandler();

        buttonHandler.registerButtonHandler(LEFT, handlerLeft);
        buttonHandler.registerButtonHandler(A, handlerLeft);

        Callable<Integer> handlerDown = levelInputHandler.getDownHandler();

        buttonHandler.registerButtonHandler(DOWN, handlerDown);
        buttonHandler.registerButtonHandler(S, handlerDown);

        Callable<Integer> handlerRight = levelInputHandler.getRightHandler();

        buttonHandler.registerButtonHandler(RIGHT, handlerRight);
        buttonHandler.registerButtonHandler(D, handlerRight);

        Callable<Integer> handlerShoot = levelInputHandler.getShootHandler();

        buttonHandler.registerButtonHandler(SPACE, handlerShoot);

        Callable<Integer> ToggleHealthBarHandler = DrawableHealthDecoratorHandlerGenerator.getHandler();

        buttonHandler.registerButtonHandler(L, ToggleHealthBarHandler);
    }
    
}
