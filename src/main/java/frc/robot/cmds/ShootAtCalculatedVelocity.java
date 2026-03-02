package frc.robot.cmds;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;

public class ShootAtCalculatedVelocity extends Command {
    private final Shooter shooter;
    private final Indexer indexer;
    private final Vision vision;

    public ShootAtCalculatedVelocity(Shooter shooter, Indexer indexer, Vision vision) {
        this.shooter = shooter;
        this.indexer = indexer;
        this.vision = vision;

        addRequirements(shooter, vision, indexer);
    }

    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        double distance = vision.calculateDistanceFromHubTarget();
        SmartDashboard.putNumber("Read Tag Distance", distance);
        
        final double wantedRPS = vision.getVelocityFromTagDistance(distance);
        SmartDashboard.putNumber("Wanted RPS", wantedRPS);

        shooter.setVelocity(wantedRPS).run();

        if (shooter.getRPS() >= wantedRPS) {
            indexer.bothOscillate().run();
        }

    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

}
