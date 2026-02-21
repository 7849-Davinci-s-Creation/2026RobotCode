package frc.robot.subsystems;

import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
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
        left = new TalonFX(Constants.Shooter.LEFT_KRAKEN_CANID);
        right = new TalonFX(Constants.Shooter.RIGHT_KRAKEN_CANID);

        final TalonFXConfiguration config = new TalonFXConfiguration()
                .withMotorOutput(
                        new MotorOutputConfigs().withNeutralMode(NeutralModeValue.Coast));

        final Slot0Configs shooterConfigs = new Slot0Configs()
                .withKP(Constants.Shooter.P)
                .withKI(Constants.Shooter.I)
                .withKD(Constants.Shooter.D)
                .withKS(Constants.Shooter.S)
                .withKV(Constants.Shooter.V)
                .withKA(Constants.Shooter.A);

        left.clearStickyFaults();
        right.clearStickyFaults();

        left.getConfigurator().apply(config);
        right.getConfigurator().apply(config);

        left.getConfigurator().apply(shooterConfigs);
        right.getConfigurator().apply(shooterConfigs);
    }

    public void setVelocity(double rpm) {
        final VelocityVoltage request = new VelocityVoltage(rpm)
                .withSlot(0);

        left.setControl(request);
        right.setControl(request);
    }

    public void stop() {
        left.stopMotor();
        right.stopMotor();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left Velocity: ", left.getVelocity().getValueAsDouble());
        SmartDashboard.putNumber("Right Velocity: ", right.getVelocity().getValueAsDouble());
    }
}
