package ru.mipt.bit.platformer.InputsHandlers;

import java.util.concurrent.Callable;


import ru.mipt.bit.platformer.Visualizer.DrawableHealthDecoratorController;

public class DrawableHealthDecoratorHandlerGenerator {
        static Callable<Integer> getHandler() {
        return () -> {
            DrawableHealthDecoratorController.revertStateIfPossible();
                
            return 0;
        };
    }
}
