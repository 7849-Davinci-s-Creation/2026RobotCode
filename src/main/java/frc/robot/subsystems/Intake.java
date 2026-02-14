package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import static frc.robot.Constants.Intake.INTAKEMOTOR1_CANID;
import static frc.robot.Constants.Intake.INTAKEMOTOR2_CANID;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import lib.NiceSubsytem;

public final class Intake extends SubsystemBase implements NiceSubsytem {
    private static Intake instance;
    private final SparkMax intakeMotor1;
    private final SparkMax intakeMotor2;

    private Intake() {
        intakeMotor1 = new SparkMax(INTAKEMOTOR1_CANID, MotorType.kBrushed);
        intakeMotor2 = new SparkMax(INTAKEMOTOR2_CANID, MotorType.kBrushless);
    };

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;
    }

    public Runnable intake() {
        return () -> {
            intakeMotor1.set(0.5);
            intakeMotor2.set(0.5);
        };
    }

    public Runnable outake() {
        return () -> {
            intakeMotor1.set(-0.15);
            intakeMotor2.set(-0.15);
        };
    }

    public Runnable stopIntake() {
        return () -> {
            intakeMotor1.set(0);
            intakeMotor2.set(0);
        };
    }

    @Override
    public void initialize() {

    }

    @Override
    public void periodic() {

    }
}
