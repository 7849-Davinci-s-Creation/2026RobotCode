package frc.robot.cmds;

import com.ctre.phoenix6.controls.PositionVoltage;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Intake.IntakeState;

public class PivotIntake extends Command {
    private final Intake.IntakeState currentIntakeState;
    private final Intake intake;

    private final Intake.IntakeState initialState;

    public PivotIntake(Intake.IntakeState currentIntakeState, Intake intake) {
        this.currentIntakeState = currentIntakeState;
        this.intake = intake;

        this.initialState = currentIntakeState;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        final PositionVoltage request;

        if (currentIntakeState.equals(Intake.IntakeState.IN)) {
            request = new PositionVoltage(Constants.Intake.intakeOutRotations).withSlot(0);

            intake.setPivotPositionControl(request);
        } else {
            request = new PositionVoltage(Constants.Intake.intakeInRotations).withSlot(0);

            intake.setPivotPositionControl(request);
        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        intake.stopPivot().run();

        if (initialState.equals(IntakeState.IN)) {
            intake.setCurrentState(IntakeState.OUT);
        } else {
            intake.setCurrentState(IntakeState.IN);
        }

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (initialState.equals(IntakeState.IN)) {
            return intake.getPivotPosition() >= Constants.Intake.intakeOutRotations;
        } else {
            return intake.getPivotPosition() <= Constants.Intake.intakeInRotations;
        }
    }
}
