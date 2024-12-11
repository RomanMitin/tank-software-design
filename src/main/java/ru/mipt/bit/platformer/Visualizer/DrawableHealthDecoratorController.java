package ru.mipt.bit.platformer.Visualizer;

public class DrawableHealthDecoratorController {
    static public boolean revert_health_visible = false;
    static public boolean is_ready_for_revert = true;
    static public final float time_between_react = 0.5f;

    private static float elapsed_time = 0;
    private static boolean old_health_visible = false;

    public static boolean needToDrawHealth(float deltaTime) {
        elapsed_time += deltaTime;
        if (elapsed_time > time_between_react) {
            is_ready_for_revert = true;
            elapsed_time = 0;
        }
        if (revert_health_visible && is_ready_for_revert) {
            revert_health_visible = false;
            is_ready_for_revert = false;
            old_health_visible = !old_health_visible;
        }

        return old_health_visible;
    }

    public static void revertStateIfPossible() {
        if (is_ready_for_revert) {
            DrawableHealthDecoratorController.revert_health_visible = true;
        }
    }
}
