package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import lib.NiceSubsytem;

import static edu.wpi.first.units.Units.Degrees;

public final class Intake extends SubsystemBase implements NiceSubsytem {
    private static Intake instance;

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;
    }

    public IntakeState currentState;

    private final TalonFX pivotMotor;
    private final WPI_VictorSPX intakeMotor;

    private Intake() {
        this.pivotMotor = new TalonFX(Constants.Intake.PIVOT_MOTOR_PORT);
        this.intakeMotor = new WPI_VictorSPX(Constants.Intake.INTAKE_MOTOR_PORT);

        this.currentState = IntakeState.IN;

        final TalonFXConfiguration configs = new TalonFXConfiguration()
                .withMotorOutput(
                        new MotorOutputConfigs()
                                .withNeutralMode(NeutralModeValue.Brake))
                .withSlot0(
                        new Slot0Configs()
                                .withKP(Constants.Intake.P)
                                .withKI(Constants.Intake.I)
                                .withKD(Constants.Intake.D)
                                .withKS(Constants.Intake.S)
                                .withKV(Constants.Intake.V));

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
        if (currentState == IntakeState.IN) {
            final PositionVoltage voltage = new PositionVoltage(1).withSlot(0).withPosition(Degrees.of(0));
            pivotMotor.setControl(voltage);

            currentState = IntakeState.OUT;
        }
    }

    public void retract() {
        if (currentState == IntakeState.OUT) {
            final PositionVoltage voltage = new PositionVoltage(1).withSlot(0).withPosition(Degrees.of(0));
            pivotMotor.setControl(voltage);

            currentState = IntakeState.IN;
        }
    }

    @Override
    public void initialize() {

    }

    @Override
    public void periodic() {
        SmartDashboard.putString("Intake State", currentState.toString());
    }

    public enum IntakeState {
        OUT,
        IN
    }
}
