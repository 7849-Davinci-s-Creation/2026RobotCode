// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.HootAutoReplay;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public final class Robot extends TimedRobot {
    private Command autonomousCommand;

    private Command currentCommand;

    private final RobotContainer robotContainer;

    /* log and replay timestamp and joystick data */
    // private final HootAutoReplay timeAndJoystickReplay = new HootAutoReplay()
    // .withTimestampReplay()
    // .withJoystickReplay();

    public Robot() {
        robotContainer = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        // timeAndJoystickReplay.update();
        CommandScheduler.getInstance().run();

        robotContainer.robotPeriodic();
    }

    @Override
    public void disabledInit() {
        robotContainer.disabledInit();

        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void disabledPeriodic() {
        robotContainer.disabledPeriodic();
    }

    @Override
    public void disabledExit() {
        robotContainer.disabledExit();
    }

    @Override
    public void autonomousInit() {
        CommandScheduler.getInstance().cancelAll();

        autonomousCommand = robotContainer.getAutonomousCommand();

        if (autonomousCommand != null) {
            CommandScheduler.getInstance().schedule(autonomousCommand);
        }

        robotContainer.autonomousInit();
    }

    @Override
    public void autonomousPeriodic() {
        robotContainer.autonomousPeriodic();
    }

    @Override
    public void autonomousExit() {
        robotContainer.autonomousExit();

        if (autonomousCommand != null) {
            CommandScheduler.getInstance().cancel(autonomousCommand);
        }
    }

    @Override
    public void teleopInit() {
        CommandScheduler.getInstance().cancelAll();

        if (autonomousCommand != null) {
            CommandScheduler.getInstance().cancel(autonomousCommand);
        }

        robotContainer.teleopInit();
    }

    @Override
    public void teleopPeriodic() {
        robotContainer.teleopPeriodic();
    }

    @Override
    public void teleopExit() {
        robotContainer.teleopExit();

        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();
        robotContainer.testInit();

        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void testPeriodic() {
        robotContainer.testPeriodic();
    }

    @Override
    public void testExit() {
        robotContainer.testExit();

        CommandScheduler.getInstance().cancelAll();
    }

    @Override
    public void simulationPeriodic() {
        robotContainer.simulationPeriodic();
    }
}
