package frc.robot.subsystems;

import com.ctre.phoenix6.configs.CANdleConfiguration;
import com.ctre.phoenix6.configs.CANdleFeaturesConfigs;
import com.ctre.phoenix6.configs.LEDConfigs;
import com.ctre.phoenix6.controls.SolidColor;
import com.ctre.phoenix6.hardware.CANdle;
import com.ctre.phoenix6.signals.RGBWColor;
import com.ctre.phoenix6.signals.StatusLedWhenActiveValue;
import com.ctre.phoenix6.signals.StripTypeValue;
import com.ctre.phoenix6.signals.VBatOutputModeValue;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import lib.NiceSubsytem;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;

public final class Vision extends SubsystemBase implements NiceSubsytem {
    private static Vision instance;

    public static Vision getInstance() {
        if (instance == null) {
            instance = new Vision();
        }

        return instance;
    }

    private final PhotonCamera camera;

    private final CANdle statusLED;

    private Rotation2d cachedAngleToHubTargetCenter;

    private double cachedDistance = 0;

    private Vision() {
        camera = new PhotonCamera(Constants.Vision.CAMERA_NAME);

        statusLED = new CANdle(Constants.Vision.CANDLE_ID);

        final CANdleConfiguration lightcon = new CANdleConfiguration();
        lightcon.withCANdleFeatures(
                new CANdleFeaturesConfigs()
                        .withStatusLedWhenActive(StatusLedWhenActiveValue.Enabled)
                        .withVBatOutputMode(VBatOutputModeValue.Modulated)
                )
                        .withLED(
                                new LEDConfigs().withBrightnessScalar(
                                        1
                                ).withStripType(StripTypeValue.RGB)
                        );


        statusLED.getConfigurator().apply(lightcon);

        cachedAngleToHubTargetCenter = Rotation2d.fromDegrees(0);
    }

    public Rotation2d calculateRobotOffsetToTargetCenter(Rotation2d robotYaw) {
        var results = camera.getAllUnreadResults();
        if (results.isEmpty()) {
            return cachedAngleToHubTargetCenter;
        }
        var result = results.get(results.size() - 1);
        if (!result.hasTargets()) {
            return cachedAngleToHubTargetCenter;
        }

        for (PhotonTrackedTarget target : result.targets) {
            if (DriverStation.getAlliance().get().equals(DriverStation.Alliance.Blue)) {

                if (target.getFiducialId() == Constants.FieldConstants.BLUE_CENTER_HUB_TARGET_ID ||
                        target.getFiducialId() == Constants.FieldConstants.BLUE_RIGHT_HUB_TARGET_ID ||
                        target.getFiducialId() == Constants.FieldConstants.BLUE_LEFT_HUB_TARGET_ID) {

                    return calculate(target, robotYaw);

                }

            }

            if (DriverStation.getAlliance().get().equals(DriverStation.Alliance.Red)) {

                if (target.getFiducialId() == Constants.FieldConstants.RED_CENTER_HUB_TARGET_ID ||
                        target.getFiducialId() == Constants.FieldConstants.RED_LEFT_HUB_TARGET_ID ||
                        target.getFiducialId() == Constants.FieldConstants.RED_RIGHT_HUB_TARGET_ID) {

                    return calculate(target, robotYaw);

                }

            }
        }

        return cachedAngleToHubTargetCenter;
    }

    private Rotation2d calculate(PhotonTrackedTarget target, Rotation2d robotYaw) {
        double targetYaw = robotYaw.getDegrees() - target.getYaw();

        targetYaw = ((targetYaw + 180) % 360) - 180;

        Rotation2d toAimAt = Rotation2d.fromDegrees(targetYaw);

        SmartDashboard.putString("Aiming to: ", getTargetNameFromID(target.getFiducialId()));

        cachedAngleToHubTargetCenter = toAimAt;

        return toAimAt;
    }

    private String getTargetNameFromID(int id) {
        switch (id) {
            case Constants.FieldConstants.BLUE_CENTER_HUB_TARGET_ID -> {
                return "Blue Center";
            }

            case Constants.FieldConstants.BLUE_RIGHT_HUB_TARGET_ID -> {
                return "Blue Right";
            }

            case Constants.FieldConstants.BLUE_LEFT_HUB_TARGET_ID -> {
                return "Blue Left";
            }

            case Constants.FieldConstants.RED_CENTER_HUB_TARGET_ID -> {
                return "Red Center";
            }

            case Constants.FieldConstants.RED_RIGHT_HUB_TARGET_ID -> {
                return "Red Right";
            }

            case Constants.FieldConstants.RED_LEFT_HUB_TARGET_ID -> {
                return "Red Left";
            }
        }

        return "None";
    }

    public double calculateDistanceFromHubTarget() {
        return cachedDistance;
    }

    public double getVelocityFromTagDistance(double distance) {
        if (distance >= 0 && distance <= 0.30) {
            return 25;
        }

        if (distance > 0.30 && distance <= 0.36) {
            return 25.5;
        }

        if (distance > 0.36 && distance <= 0.38) {
            return 26;
        }

        if (distance > 0.38 && distance <= 0.42) {
            return 26.5;
        }

        if (distance > 0.42 && distance <= 0.46) {
            return 30;
        }

        if (distance > 0.46 && distance <= 0.50) {
            return 32;
        }

        if (distance > 0.50 && distance <= 0.53) {
            return 36;
        }

        if (distance > 0.53) {
            return 43;
        }

        return Constants.Shooter.DEFAULT_RPS;
    }

    private void setLEDToRed() {
        statusLED.setControl(
                new SolidColor(Constants.Vision.LED_START, Constants.Vision.LED_END)
                        .withColor(
                                new RGBWColor(255, 0, 0)
                        )
        );
    }

    private void setLEDToYellow() {
        statusLED.setControl(
                new SolidColor(Constants.Vision.LED_START, Constants.Vision.LED_END)
                        .withColor(
                                new RGBWColor(255, 255, 0)
                        )
        );
    }

    private void setLEDToGreen() {
        statusLED.setControl(
                new SolidColor(Constants.Vision.LED_START, Constants.Vision.LED_END)
                        .withColor(
                                new RGBWColor(0, 255, 0)
                        )
        );
    }

    @Override
    public void initialize() {

    }

    @Override
    public void periodic() {
        // if camera is not connect display red
        if (!camera.isConnected()) {
            setLEDToRed();
        }

        var results = camera.getAllUnreadResults();

        // if there aren't any results in the camera set to yellow
        if (results.isEmpty()) {
            setLEDToYellow();
        }

        if (!results.isEmpty()) {

            var result = results.get(results.size() - 1);

            // if there are results but no targets set to yellow
            if (!result.hasTargets()) {
                setLEDToYellow();
            }

            if (result.hasTargets()) {

                for (PhotonTrackedTarget target : result.targets) {

                    if (target.getFiducialId() == Constants.FieldConstants.RED_CENTER_HUB_TARGET_ID ||
                            target.getFiducialId() == Constants.FieldConstants.RED_LEFT_HUB_TARGET_ID ||
                            target.getFiducialId() == Constants.FieldConstants.RED_RIGHT_HUB_TARGET_ID ||

                            target.getFiducialId() == Constants.FieldConstants.BLUE_CENTER_HUB_TARGET_ID ||
                            target.getFiducialId() == Constants.FieldConstants.BLUE_RIGHT_HUB_TARGET_ID ||
                            target.getFiducialId() == Constants.FieldConstants.BLUE_LEFT_HUB_TARGET_ID) {

                        cachedDistance = PhotonUtils.calculateDistanceToTargetMeters(
                                Constants.Vision.CAMERA_HEIGHT_METERS,
                                Constants.FieldConstants.APRILTAG_HUB_HEIGHTS_METERS,
                                Constants.Vision.CAMERA_PITCH_RADIANS,
                                Units.degreesToRadians(target.getPitch())
                        );

                        // we see targets and therefore can work with them
                        // set LEDs to green
                        setLEDToGreen();

                        break;

                    } else {

                        // if we see the targets, but they aren't the ones we want set the status to yellow
                        setLEDToYellow();
                    }

                }

            }

        }

        SmartDashboard.putNumber("Cached Distances: ", cachedDistance);
    }
}
