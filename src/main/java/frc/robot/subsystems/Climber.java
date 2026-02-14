package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lib.NiceSubsytem;

public final class Climber extends SubsystemBase implements NiceSubsytem {
    private static Climber instance = null;

    public static Climber getInstance() {
        if (instance == null) {
            instance = new Climber();
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
