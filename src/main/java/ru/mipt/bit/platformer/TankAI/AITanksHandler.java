package ru.mipt.bit.platformer.TankAI;

import com.badlogic.gdx.utils.Array;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Component;

@Component
public class AITanksHandler {
    private Array<Callable<Integer>> aiTanksActions = new Array<>();

    public void registerAction(Callable<Integer> action) {
        aiTanksActions.add(action);
    }

    public void handleActions() {
        for (Callable<Integer> action : aiTanksActions) {
            try {
                action.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
