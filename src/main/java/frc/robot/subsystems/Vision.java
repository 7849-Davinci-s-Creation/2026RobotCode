package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lib.NiceSubsytem;

public final class Vision extends SubsystemBase implements NiceSubsytem {
    private static Vision instance;

    public static Vision getInstance() {
        if (instance == null) {
            instance = new Vision();
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
