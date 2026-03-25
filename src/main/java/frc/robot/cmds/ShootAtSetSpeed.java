package frc.robot.cmds;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class ShootAtSetSpeed extends Command {
    private final Shooter shooter;
    private final Indexer indexer;
    private final double rps;

    public ShootAtSetSpeed(Shooter shooter, Indexer indexer, double rps) {
        this.shooter = shooter;
        this.indexer = indexer;
        this.rps = rps;

        addRequirements(shooter, indexer);
    }

    @Override
    public void initialize() {

    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        shooter.setVelocity(rps).run();

        if (shooter.getRPS() >= rps) {
            indexer.stage1On().run();
            indexer.runFeeder(40).run();
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

