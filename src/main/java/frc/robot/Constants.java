package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
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
        // set these
        public static final int LEFT_KRAKEN_CANID = 0;
        public static final int RIGHT_KRAKEN_CANID = 0;

        // find these
        public static final double P = 0;
        public static final double I = 0;
        public static final double D = 0;
        public static final double S = 0;
        public static final double V = 0;
        public static final double FF = 0;
    }

    public static final class Climber {

    }

    public static final class Indexer {
        public static final int STAGE1_MOTOR_PORT = 0;
        public static final int STAGE2_MOTOR_PORT = 0;
    }

    public static final class Intake {
        public static final int INTAKE_MOTOR_PORT = 0;
        public static final int PIVOT_MOTOR_PORT = 0;

        public static final double P = 0;
        public static final double I = 0;
        public static final double D = 0;
        public static final double S = 0;
        public static final double V = 0;
    }

    public static final class Vision {
        // set and find these
        public static final String CAMERA_NAME = "CHANGE_ME";
        public static final double CAMERA_HEIGHT_METERS = Units.inchesToMeters(0);
        public static final double CAMERA_PITCH_RADIANS = Units.degreesToRadians(0);
    }

    public static final class FieldConstants {
        // BLUE HUB APRIL TAGS
        public static final int BLUE_RIGHT_HUB_TARGET_ID = 27;
        public static final int BLUE_CENTER_HUB_TARGET_ID = 26;
        public static final int BLUE_OFF_CENTER_HUB_TARGET_ID = 25;
        public static final int BLUE_LEFT_HUB_TARGET_ID = 24;

        // RED HUB APRIL TAGS
        public static final int RED_LEFT_HUB_TARGET_ID = 8;
        public static final int RED_OFF_CENTER_HUB_TARGET_ID = 9;
        public static final int RED_CENTER_HUB_TARGET_ID = 10;
        public static final int RED_RIGHT_HUB_TARGET_ID = 11;

        public static final double APRILTAG_HUB_HEIGHTS = 0;
    }
}
