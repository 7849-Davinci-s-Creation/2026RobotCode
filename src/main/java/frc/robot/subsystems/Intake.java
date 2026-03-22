package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix6.configs.MotorOutputConfigs;
import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import lib.NiceSubsytem;

public final class Intake extends SubsystemBase implements NiceSubsytem {
    private static Intake instance;

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;
    }

    public IntakeState currentState;

    private final TalonFX leftPivotMotor;
    private final TalonFX rightPivotMotor;
    private final WPI_VictorSPX intakeMotor;

    private Intake() {
        this.leftPivotMotor = new TalonFX(Constants.Intake.RIGHT_PIVOT_MOTOR_PORT);
        this.rightPivotMotor = new TalonFX(Constants.Intake.RIGHT_PIVOT_MOTOR_PORT);
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

        leftPivotMotor.getConfigurator().apply(
                configs.withMotorOutput(new MotorOutputConfigs().withInverted(InvertedValue.Clockwise_Positive)));
        rightPivotMotor.getConfigurator().apply(configs);
    };

    public Runnable intake() {
        return () -> intakeMotor.set(1);
    }

    public Runnable outake() {
        return () -> intakeMotor.set(-1);
    }

    public Runnable stopIntake() {
        return () -> intakeMotor.set(0);
    }

    public Runnable zeroPivot() {
        return () -> leftPivotMotor.setPosition(0);
    }

    public Runnable setPivotPositionControl(PositionVoltage request) {
        return () -> {
            leftPivotMotor.setControl(request);
            rightPivotMotor.setControl(request);
        };
    }

    public Runnable runPivotRawOut() {
        return () -> {
            leftPivotMotor.set(0.09);
            rightPivotMotor.set(0.09);
            currentState = IntakeState.OUT;
        };
    }

    public Runnable runPivotRawIn() {
        return () -> {
            leftPivotMotor.set(-0.09);
            rightPivotMotor.set(-0.09);
            currentState = IntakeState.IN;
        };
    }

    public Runnable stopPivot() {
        return () -> {
            leftPivotMotor.stopMotor();
            rightPivotMotor.stopMotor();
        };
    }

    public Runnable runLeftAlone() {
        return () -> leftPivotMotor.set(0.09);
    }

    public Runnable runRightAlone() {
        return () -> rightPivotMotor.set(0.09);
    }

    public void setCurrentState(IntakeState state) {
        currentState = state;
    }
    public IntakeState getState() {
        return currentState;
    }

    public double getPivotPosition() {
        return leftPivotMotor.getPosition().getValueAsDouble();
    }

    @Override
    public void initialize() {

    }

    @Override
    public void periodic() {
        SmartDashboard.putString("Intake State", currentState.toString());

        SmartDashboard.putNumber("Intake Position", leftPivotMotor.getPosition().getValueAsDouble());
    }

    public IntakeState getInType() {
        return IntakeState.IN;
    }

    public IntakeState getOutType() {
        return IntakeState.OUT;
    }

    public enum IntakeState {
        OUT,
        IN
    }
}
