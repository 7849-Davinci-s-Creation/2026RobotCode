package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lib.NiceSubsytem;

public final class Shooter extends SubsystemBase implements NiceSubsytem {
    private static Shooter instance;

    public static Shooter getInstance() {
        if (instance == null) {
            instance = new Shooter();
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
