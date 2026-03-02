package frc.robot.subsystems;

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

    private Rotation2d lastCalculatedRotation;

    private double cachedDistance = 0;

    private Vision() {
        camera = new PhotonCamera(Constants.Vision.CAMERA_NAME);
        lastCalculatedRotation = Rotation2d.fromDegrees(0);
    }

    public Rotation2d calculateRobotOffsetToTargetCenter(Rotation2d robotYaw) {
        var results = camera.getAllUnreadResults();
        if (results.isEmpty()) {
            return lastCalculatedRotation;
        }
        var result = results.get(results.size() - 1);
        if (!result.hasTargets()) {
            return lastCalculatedRotation;
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

        return lastCalculatedRotation;
    }

    private Rotation2d calculate(PhotonTrackedTarget target, Rotation2d robotYaw) {
        double targetYaw = robotYaw.getDegrees() - target.getYaw();

        targetYaw = ((targetYaw + 180) % 360) - 180;

        Rotation2d toAimAt = Rotation2d.fromDegrees(targetYaw);

        SmartDashboard.putString("Aiming to: ", getTargetNameFromID(target.getFiducialId()));

        lastCalculatedRotation = toAimAt;

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

        return Constants.Shooter.MIN_RPS;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void periodic() {
        var results = camera.getAllUnreadResults();
        if (!results.isEmpty()) {
            var result = results.get(results.size() - 1);
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
                                Units.degreesToRadians(target.getPitch()));
                        return;
                    }
                }
            }
        }

        SmartDashboard.putNumber("Cached Distances: ", cachedDistance);
    }
}
