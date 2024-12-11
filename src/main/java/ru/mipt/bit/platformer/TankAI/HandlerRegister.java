package ru.mipt.bit.platformer.TankAI;

import ru.mipt.bit.platformer.GameObjects.GameObj;
import ru.mipt.bit.platformer.GameObjects.GameObjType;
import ru.mipt.bit.platformer.GameObjects.Level;
import ru.mipt.bit.platformer.GameObjects.ShootingTank;

public class HandlerRegister {
        public static void registerAITanksActions(Level level, AITanksHandler aiTanksActions) {
        for (GameObj gameObj : level.getGameObjects()) {
            if (gameObj.getType() == GameObjType.AITank) {
                ShootingTank shootingObj = (ShootingTank)gameObj;
                aiTanksActions.registerAction(TankAI.getDefaultAITankAction(shootingObj, level));
            }
        }
    }
}
