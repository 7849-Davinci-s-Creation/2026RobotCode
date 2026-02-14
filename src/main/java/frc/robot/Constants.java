package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.generated.TunerConstants;

import static edu.wpi.first.units.Units.*;

public final class Constants {
    public static final class DriveTrain {
        public static final double MAX_SPEED = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts
                                                                                                   // desired top
        // speed
        public static final double MAX_ANGULAR_RATE = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a
                                                                                                        // rotation per
                                                                                                        // second
        // max angular velocity

        /* Blue alliance sees forward as 0 degrees (toward red alliance wall) */
        public static final Rotation2d BLUE_ALLIANCE_PERSPECTIVE_ROTATION = Rotation2d.kZero;
        /* Red alliance sees forward as 180 degrees (toward blue alliance wall) */
        public static final Rotation2d RED_ALLIANCE_PERSPECTIVE_ROTATION = Rotation2d.k180deg;
    }

    public static final class Operator {
        public static final int DRIVER_CONTROLLER_PORT = 1;

        public static final double SLIGHT_CREEP_NERF_DRIVE = 1;
        public static final double SLIGHT_CREEP_NERF_ROTATE = 1;
        public static final double MAJOR_CREEP_NERF_DRIVE = 0.5;
        public static final double MAJOR_CREEP_NERF_ROTATE = 0.5;
    }

    public static final class Shooter {

    }

    public static final class Climber {

    }

    public static final class Indexer {

    }

    public static final class Intake {

    }
}
