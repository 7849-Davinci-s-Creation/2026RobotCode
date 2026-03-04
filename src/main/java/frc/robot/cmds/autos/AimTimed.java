package frc.robot.cmds.autos;

import java.text.Normalizer;

import com.ctre.phoenix6.swerve.SwerveRequest.FieldCentric;
import com.ctre.phoenix6.swerve.SwerveRequest.FieldCentricFacingAngle;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.Vision;
import lib.Commands.TimedCommand;

public class AimTimed extends TimedCommand {
    private final CommandSwerveDrivetrain drivetrain;
    private final FieldCentricFacingAngle cmd;
    private final FieldCentric normal;
    private final Vision vision;

    public AimTimed(CommandSwerveDrivetrain drivetrain, Vision vision, FieldCentricFacingAngle cmd, FieldCentric normal,
            double seconds,
            Subsystem... requirements) {
        super(seconds, requirements);

        this.drivetrain = drivetrain;
        this.vision = vision;

        this.cmd = cmd;
        this.normal = normal;
    }

    @Override
    public void init() {

    }

    @Override
    public void exec() {
        final Rotation2d target = vision
                .calculateRobotOffsetToTargetCenter(
                        drivetrain.getState().Pose.getRotation());

        drivetrain.setControl(cmd.withTargetDirection(target));
    }

    @Override
    public void end() {
        drivetrain.setControl(normal);
    }

}
