package frc.robot.cmds.autos;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Intake;
import lib.Commands.TimedCommand;

public class IntakeTimed extends TimedCommand {
    private final Intake intake;
    private final Indexer indexer;

    public IntakeTimed(Intake intake, Indexer indexer, double seconds, Subsystem... requirements) {
        super(seconds, requirements);

        this.intake = intake;
        this.indexer = indexer;
    }


    @Override
    public void init() {

    }

    @Override
    public void exec() {
        intake.intake().run();
        indexer.oscillateStage1().run();
    }

    @Override
    public void end() {
        intake.stopIntake();
        indexer.stage1Off();
    }
    
}
