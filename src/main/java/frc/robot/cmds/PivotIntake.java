package frc.robot.cmds;

import com.ctre.phoenix6.controls.PositionVoltage;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;

public class PivotIntake extends Command {
    private final Intake intake;
    private Intake.IntakeState initialState;

    public PivotIntake(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        this.initialState = intake.getState();
    }

    @Override
    public void execute() {
        final PositionVoltage request;

        if (initialState.equals(Intake.IntakeState.IN)) {
            request = new PositionVoltage(Constants.Intake.intakeOutRotations).withSlot(0);
        } else {
            request = new PositionVoltage(Constants.Intake.intakeInRotations).withSlot(0);
        }

        intake.setPivotPositionControl(request).run();
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopPivot().run();
    }

    @Override
    public boolean isFinished() {
        if (initialState.equals(Intake.IntakeState.IN)) {
            return intake.getPivotPosition() >= Constants.Intake.intakeOutRotations;
        } else {
            return intake.getPivotPosition() <= 4;
        }
    }
}