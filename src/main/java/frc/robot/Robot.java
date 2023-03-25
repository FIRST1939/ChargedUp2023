// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class Robot extends TimedRobot {

  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
 
  @Override
  public void robotInit () {

    m_robotContainer = new RobotContainer();

    CommandScheduler.getInstance().onCommandInitialize(command -> System.out.println("<INITIALIZING " + command.getName() + ">"));
    CommandScheduler.getInstance().onCommandFinish(command -> System.out.println("<ENDING " + command.getName() + ">"));

    /**
    Shuffleboard.getTab("Competition")
      .add("Intake Camera", CameraServer.startAutomaticCapture(0))
      .withWidget(BuiltInWidgets.kCameraStream)
      .withProperties(Map.of("SHOW CROSSHAIR", false, "CROSSHAIR COLOR", "white", "SHOW CONTROLS", false, "ROTATION", "NONE"))
      .withPosition(4, 0)
      .withSize(3, 4);
    */

    Shuffleboard.getTab("Competition")
      .add("Manipulator Camera", CameraServer.startAutomaticCapture(0))
      .withWidget(BuiltInWidgets.kCameraStream)
      .withProperties(Map.of("SHOW CROSSHAIR", false, "CROSSHAIR COLOR", "white", "SHOW CONTROLS", false, "ROTATION", "NONE"))
      .withPosition(7, 0)
      .withSize(3, 4);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before Shuffleboard integrated updating.
   */
  @Override
  public void robotPeriodic () {

    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit () {}

  @Override
  public void disabledPeriodic () {}

  @Override
  public void autonomousInit () {

    new WaitCommand(m_robotContainer.getAutonomousWaitTime()).schedule();

    m_autonomousCommand = m_robotContainer.getAutonomousCommand();
    if (m_autonomousCommand != null) { m_autonomousCommand.schedule(); }
  }

  @Override
  public void autonomousPeriodic () {}

  @Override
  public void teleopInit () {

    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) { m_autonomousCommand.cancel(); }
    if (m_robotContainer.getLEDs() == Constants.ElectronicConstants.LED_COLORS.RAINBOW) { m_robotContainer.setLEDs(Constants.ElectronicConstants.LED_COLORS.UNSELECTED); }
  }

  @Override
  public void teleopPeriodic () {}

  @Override
  public void testInit () { CommandScheduler.getInstance().cancelAll(); }

  @Override
  public void testPeriodic () {}
}
