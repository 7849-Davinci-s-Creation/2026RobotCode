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

    private Vision() {
        camera = new PhotonCamera(Constants.Vision.CAMERA_NAME);
        lastCalculatedRotation = Rotation2d.fromDegrees(180);
    }

    public Rotation2d calculateRobotOffsetToTargetCenter(Rotation2d robotYaw, DriverStation.Alliance robotAlliance) {
        var results = camera.getAllUnreadResults();
        if (results.isEmpty()) {
            return lastCalculatedRotation;
        }
        var result = results.get(results.size() - 1);
        if (!result.hasTargets()) {
            return lastCalculatedRotation;
        }

        for (PhotonTrackedTarget target : result.getTargets()) {
            if (target.getFiducialId() == Constants.FieldConstants.BLUE_CENTER_HUB_TARGET_ID) {
                double targetYaw = robotYaw.getDegrees() - target.getYaw();

                targetYaw = ((targetYaw + 180) % 360) - 180;

                Rotation2d toAimAt = Rotation2d.fromDegrees(targetYaw);

                SmartDashboard.putNumber("Aiming to: ", toAimAt.getDegrees());
                SmartDashboard.putNumber("Tag Yaw: ", target.getYaw());

                lastCalculatedRotation = toAimAt;

                return toAimAt;
            }
        }

        return lastCalculatedRotation;
    }

    public double calculateDistanceFromHubTarget(DriverStation.Alliance robotAlliance) {
        var results = camera.getAllUnreadResults();

        if (results.isEmpty()) {
            return 0;
        }

        var result = results.get(results.size() - 1);
        if (!result.hasTargets()) {
            return 0;
        }

        for (PhotonTrackedTarget target : result.getTargets()) {
            if (target.getFiducialId() == Constants.FieldConstants.BLUE_CENTER_HUB_TARGET_ID) {
                return PhotonUtils.calculateDistanceToTargetMeters(
                        Constants.Vision.CAMERA_HEIGHT_METERS, // camera height
                        Constants.FieldConstants.APRILTAG_HUB_HEIGHTS, // target height
                        Constants.Vision.CAMERA_PITCH_RADIANS,
                        Units.degreesToRadians(target.getPitch()));
            }

        }

        return 0;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void periodic() {

    }
}
