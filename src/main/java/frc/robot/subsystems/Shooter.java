package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;

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

    private final TalonFX left;
    private final TalonFX right;
    
    private Shooter() {
        left = new TalonFX(0);
        right = new TalonFX(0);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void periodic() {

    }
}
