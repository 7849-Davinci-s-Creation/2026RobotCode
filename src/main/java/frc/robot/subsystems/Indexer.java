package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import lib.NiceSubsytem;

public final class Indexer extends SubsystemBase implements NiceSubsytem {
    private static Indexer instance = null;

    public static Indexer getInstance() {
        if (instance == null) {
            instance = new Indexer();
        }

        return instance;
    }

    private final WPI_VictorSPX stage1;
    private final WPI_VictorSPX stage2;

    private Indexer() {
        stage1 = new WPI_VictorSPX(Constants.Indexer.STAGE1_MOTOR_PORT);
        stage2 = new WPI_VictorSPX(Constants.Indexer.STAGE2_MOTOR_PORT);
    }

    public void stage1On() {
        stage1.set(1);
    }

    public void stage2On() {
        stage2.set(1);
    }

    public void stage1Off() {
        stage1.set(0);
    }

    public void stage2Off() {
        stage2.set(0);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void periodic() {

    }
}
