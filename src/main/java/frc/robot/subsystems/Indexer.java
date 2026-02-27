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

        stage1.setInverted(true);
        stage2.setInverted(false);
    }

    public Runnable stage1On() {
        return () -> stage1.set(1);
    }

    public Runnable stage2On() {
        return () -> stage2.set(1);
    }

    public Runnable stage1Off() {
        return () -> stage1.set(0);
    }

    public Runnable stage2Off() {
        return () -> stage2.set(0);
    }

    public Runnable bothOn() {
        return () -> {
            stage1.set(1);
            stage2.set(1);
        };
    }

    public Runnable bothOff() {
        return () -> {
            stage1.set(0);
            stage2.set(0);
        };
    }

    @Override
    public void initialize() {

    }

    @Override
    public void periodic() {

    }
}
