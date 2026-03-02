package frc.robot.cmds;

import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class InlineCommands {

    public static Runnable score(Shooter shooter, Indexer indexer) {
        return () -> {
            final double wantedRPS = 20;

            shooter.setVelocity(wantedRPS);

            if (shooter.getRPS() >= wantedRPS) {
                indexer.bothOn();
            }
        };
    }
}
