package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.StateTracker;
import lib.NiceSubsytem;

public final class Intake extends SubsystemBase implements NiceSubsytem {
    private static Intake instance;

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;
    }

    private final TalonFX pivotMotor;
    private final WPI_VictorSPX intakeMotor;

    private Intake() {
        this.pivotMotor = new TalonFX(Constants.Intake.PIVOT_MOTOR_PORT);
        this.intakeMotor = new WPI_VictorSPX(Constants.Intake.INTAKE_MOTOR_PORT);

        final TalonFXConfiguration configs = new TalonFXConfiguration()
                .withMotorOutput(
                        new MotorOutputConfigs()
                                .withNeutralMode(NeutralModeValue.Brake)
                )
                .withSlot0(
                        new Slot0Configs()
                                .withKP(Constants.Intake.P)
                                .withKI(Constants.Intake.I)
                                .withKD(Constants.Intake.D)
                                .withKS(Constants.Intake.S)
                                .withKV(Constants.Intake.V)
                );

        pivotMotor.getConfigurator().apply(configs);
    };

    public void intake() {
        intakeMotor.set(0.5);
    }

    public void outake() {
        intakeMotor.set(-0.5);
    }

    public void stopIntake() {
        intakeMotor.set(0);
    }

    public void deploy() {
        if (StateTracker.currentIntakeState == StateTracker.IntakeState.EXTENDED) {
            return;
        }

        final PositionVoltage voltage = new PositionVoltage(1).withSlot(0);
        pivotMotor.setControl(voltage.withPosition(0));
    }

    public void retract() {
        if (StateTracker.currentIntakeState == StateTracker.IntakeState.STORED) {
            return;
        }

        final PositionVoltage voltage = new PositionVoltage(1).withSlot(0);
        pivotMotor.setControl(voltage.withPosition(0));
    }

    @Override
    public void initialize() {

    }

    @Override
    public void periodic() {

    }
}
