package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lib.NiceSubsytem;

public final class Intake extends SubsystemBase implements NiceSubsytem {
    private static Intake instance;


    private Intake() {

    };

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;
    }

    public Runnable intake() {
        return () -> {

        };
    }

    public Runnable outake() {
        return () -> {

        };
    }

    public Runnable stopIntake() {
        return () -> {

        };
    }

    @Override
    public void initialize() {

    }

    @Override
    public void periodic() {

    }
}
