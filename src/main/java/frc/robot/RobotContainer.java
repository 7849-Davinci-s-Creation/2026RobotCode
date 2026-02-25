// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static frc.robot.Constants.DriveTrain.AIM_D;
import static frc.robot.Constants.DriveTrain.AIM_DEADBAND;
import static frc.robot.Constants.DriveTrain.AIM_I;
import static frc.robot.Constants.DriveTrain.AIM_P;
import static frc.robot.Constants.DriveTrain.MAX_ANGULAR_RATE;
import static frc.robot.Constants.DriveTrain.MAX_SPEED;
import static frc.robot.Constants.Operator.DRIVER_CONTROLLER_PORT;
import static frc.robot.Constants.Operator.MAJOR_CREEP_NERF_DRIVE;
import static frc.robot.Constants.Operator.MAJOR_CREEP_NERF_ROTATE;
import static frc.robot.Constants.Operator.OPERATOR_CONTROLLER_PORT;
import static frc.robot.Constants.Operator.SLIGHT_CREEP_NERF_DRIVE;
import static frc.robot.Constants.Operator.SLIGHT_CREEP_NERF_ROTATE;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest.FieldCentricFacingAngle;
import com.ctre.phoenix6.swerve.SwerveRequest;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.*;
import lib.RobotMethods;

public final class RobotContainer implements RobotMethods {
        private final CommandSwerveDrivetrain drivetrain;
        private final Climber climber;
        private final Indexer indexer;
        private final Intake intake;
        private final Vision vision;
        private final Shooter shooter;

        private final CommandXboxController joystick;
        private final CommandXboxController operator;

        /* Setting up bindings for necessary control of the swerve drive platform */
        private final SwerveRequest.FieldCentric drive; // Use open-loop control for drive
                                                        // motors

        private final SwerveRequest.FieldCentricFacingAngle aiming;

        private final SendableChooser<Command> autoChooser;

        private final Timer timer = new Timer();

        public RobotContainer() {
                drivetrain = TunerConstants.createDrivetrain();
                climber = Climber.getInstance();
                indexer = Indexer.getInstance();
                intake = Intake.getInstance();
                vision = Vision.getInstance();
                shooter = Shooter.getInstance();

                drivetrain.initialize();
                climber.initialize();
                indexer.initialize();
                intake.initialize();
                vision.initialize();
                shooter.initialize();

                joystick = new CommandXboxController(DRIVER_CONTROLLER_PORT);
                operator = new CommandXboxController(OPERATOR_CONTROLLER_PORT);

                /* Setting up bindings for necessary control of the swerve drive platform */
                drive = new SwerveRequest.FieldCentric()
                                .withDeadband(MAX_SPEED * 0.1).withRotationalDeadband(MAX_ANGULAR_RATE * 0.1) // Add a
                                                                                                              // 10%
                                                                                                              // deadband
                                .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for
                                                                                         // drive
                                                                                         // motors

                aiming = new FieldCentricFacingAngle()
                                .withHeadingPID(AIM_P, AIM_I, AIM_D)
                                .withRotationalDeadband(AIM_DEADBAND);

                configureDefault();
                configureBindings();
                registerNamedCommands();

                autoChooser = AutoBuilder.buildAutoChooser();
                SmartDashboard.putData("Auto", autoChooser);

                // clean up garbage after initialization
                timer.start();
        }

        private void configureDefault() {
                // Note that X is defined as forward according to WPILib convention,
                // and Y is defined as to the left according to WPILib convention.
                drivetrain.setDefaultCommand(
                                // Drivetrain will execute this command periodically
                                drivetrain.applyRequest(() -> drive.withVelocityX(-joystick.getLeftY() * MAX_SPEED) // Drive
                                                                                                                    // forward
                                                                                                                    // with
                                                // negative Y
                                                // (forward)
                                                .withVelocityY(-joystick.getLeftX() * MAX_SPEED) // Drive left with
                                                                                                 // negative X (left)
                                                .withRotationalRate(-joystick.getRightX() * MAX_ANGULAR_RATE) // Drive
                                                                                                              // counterclockwise
                                                                                                              // with
                                // negative X (left)
                                ));

                // Idle while the robot is disabled. This ensures the configured
                // neutral mode is applied to the drive motors while disabled.
                final var idle = new SwerveRequest.Idle();
                RobotModeTriggers.disabled().whileTrue(
                                drivetrain.applyRequest(() -> idle).ignoringDisable(true));
        }

        private void configureBindings() {
                // Reset the field-centric heading on left bumper press.
                joystick.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

                joystick.leftTrigger().whileTrue(
                                drivetrain.applyRequest(
                                                () -> drive.withVelocityX(-joystick.getLeftY() * MAX_SPEED
                                                                * SLIGHT_CREEP_NERF_DRIVE)
                                                                .withVelocityY(-joystick.getLeftX() * MAX_SPEED
                                                                                * SLIGHT_CREEP_NERF_DRIVE)
                                                                .withRotationalRate(-joystick.getRightX()
                                                                                * MAX_ANGULAR_RATE
                                                                                * SLIGHT_CREEP_NERF_ROTATE))
                );

                joystick.rightTrigger().whileTrue(
                                drivetrain.applyRequest(
                                                () -> drive.withVelocityX(-joystick.getLeftY() * MAX_SPEED
                                                                * MAJOR_CREEP_NERF_DRIVE)
                                                                .withVelocityY(-joystick.getLeftX() * MAX_SPEED
                                                                                * MAJOR_CREEP_NERF_DRIVE)
                                                                .withRotationalRate(-joystick.getRightX()
                                                                                * MAX_ANGULAR_RATE
                                                                                * MAJOR_CREEP_NERF_ROTATE))
                );

                // aim the drivetrain at the hubs
                joystick.a().whileTrue(drivetrain.applyRequest(
                                                () -> {
                                                        final Rotation2d target = vision
                                                                        .calculateRobotOffsetToTargetCenter(
                                                                                        drivetrain.getState().Pose.getRotation());

                                                        return aiming.withVelocityX(Math.abs(joystick.getLeftY()) > 0.1
                                                                        ? -joystick.getLeftY()* Constants.DriveTrain.MAX_SPEED : 0)
                                                                     .withVelocityY(Math.abs(joystick.getLeftX()) > 0.1
                                                                        ? -joystick.getLeftX()* Constants.DriveTrain.MAX_SPEED : 0)
                                                                      .withTargetDirection(target); }))
                                .onFalse(drivetrain.applyRequest(() -> drive
                                                                .withVelocityX(-joystick.getLeftY() * MAX_SPEED)
                                                                .withVelocityY(-joystick.getLeftX() * MAX_SPEED)
                                                                .withRotationalRate(-joystick.getRightX()
                                                                                * MAX_ANGULAR_RATE)));

                // aim and shoot at calculated velocity
                operator.a().whileTrue(
                                Commands.run(shooter.setVelocity(45))).onFalse(
                                                Commands.run(
                                                                shooter.stop()));

                // shoot at full speed / full field rpm
                operator.leftBumper().whileTrue(
                        Commands.run(shooter.setVelocity(Constants.Shooter.SHOOTER_MAX_RPS))
                ).onFalse(
                        Commands.run(shooter.stop())
                );

                // shoot at half field rpm
                operator.rightBumper().whileTrue(
                        Commands.run(shooter.setVelocity(Constants.Shooter.HALF_FIELD_RPS))
                ).onFalse(
                        Commands.run(shooter.stop())
                );
        }

        public Command getAutonomousCommand() {
                return autoChooser.getSelected();
        }

        public void registerNamedCommands() {

        }

        @Override
        public void robotPeriodic() {
                // periodically call garbage collector
                if (timer.advanceIfElapsed(5)) {
                        System.gc();
                }
        }

        @Override
        public void disabledInit() {

        }

        @Override
        public void disabledPeriodic() {

        }

        @Override
        public void disabledExit() {

        }

        @Override
        public void autonomousInit() {

        }

        @Override
        public void autonomousPeriodic() {

        }

        @Override
        public void autonomousExit() {

        }

        @Override
        public void teleopInit() {

        }

        @Override
        public void teleopPeriodic() {

        }

        @Override
        public void teleopExit() {

        }

        @Override
        public void testInit() {

        }

        @Override
        public void testPeriodic() {

        }

        @Override
        public void testExit() {

        }

        @Override
        public void simulationPeriodic() {

        }
}
