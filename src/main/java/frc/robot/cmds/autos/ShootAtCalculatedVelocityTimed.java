package frc.robot.cmds.autos;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.cmds.ShootAtCalculatedVelocity;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Vision;
import lib.Commands.TimedCommand;

public class ShootAtCalculatedVelocityTimed extends TimedCommand {
    private final ShootAtCalculatedVelocity shootAtCalculatedVelocity;

    public ShootAtCalculatedVelocityTimed(Shooter shooter, Indexer indexer, Vision vision, double seconds, Subsystem... requirments) {
        super(seconds, requirments);

        this.shootAtCalculatedVelocity = new ShootAtCalculatedVelocity(shooter, indexer, vision);
    }

    @Override
    public void init() {

    }

    @Override
    public void exec() {
        shootAtCalculatedVelocity.execute();
    }

    @Override
    public void end() {
        
    }
    
}
