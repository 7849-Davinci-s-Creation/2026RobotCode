package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lib.NiceSubsytem;

public final class Indexer extends SubsystemBase implements NiceSubsytem {
    private static Indexer instance = null;

    public static Indexer getInstance() {
        if (instance == null) {
            instance = new Indexer();
        }

        return instance;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void periodic() {

    }
}
