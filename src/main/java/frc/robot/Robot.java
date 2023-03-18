// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.SetLEDs;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;

public class Robot extends TimedRobot {

  private Command m_autonomousCommand;
  private RobotContainer m_robotContainer;
  private RobotContainer m_TestrobotContainer;
 

  @Override
  public void robotInit () {

    m_robotContainer = new RobotContainer();

    CameraServer.startAutomaticCapture("Intaker Camera", 0);
    CameraServer.startAutomaticCapture("Manipulator Camera", 1);

    PortForwarder.add(5800, "photonvision.local", 5800);
   
    this.m_robotContainer.leds.setHue(Constants.ElectronicConstants.LED_COLORS.RAINBOW);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic () {

    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();

    if (!DriverStation.getJoystickIsXbox(Constants.ControllerConstants.DRIVER_TWO)) { 

      DriverStation.reportError("RESCAN USB DEVICES (F1)", false);
      new SetLEDs(this.m_robotContainer.leds, Constants.ElectronicConstants.LED_COLORS.ERROR).schedule();
    }
    SmartDashboard.putData(m_robotContainer.compressor);
    SmartDashboard.putNumber("CompressorPSI",m_robotContainer.compressor.getPressure());
   // m_robotContainer.compressor.enableAnalog(Constants.ElectronicConstants.PNEUMATICS_MINIMUM_PRESSURE, Constants.ElectronicConstants.PNEUMATICS_MAXIMUM_PRESSURE);

    //  Compressor compressor = new Compressor(Constants.ElectronicConstants.PNEUMATICS_HUB, PneumaticsModuleType.REVPH);
   /// SmartDashboard.putData(compressor);
   // SmartDashboard.putNumber("Compressor(PSI)", compressor.getPressure());
  }

  @Override
  public void disabledInit () {}

  @Override
  public void disabledPeriodic () {}

  @Override
  public void autonomousInit () {

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
    if (m_autonomousCommand != null) {

      m_autonomousCommand.cancel();
    }
  }

  @Override
  public void teleopPeriodic () {}

  @Override
  public void testInit () {
    
    CommandScheduler.getInstance().cancelAll();

    

    // Cancels all running commands at the start of test mode.
    
   // CommandXboxController driverTwo = new CommandXboxController(Constants.ControllerConstants.DRIVER_TWO);
   // driverTwo.leftBumper().whileTrue(()->{SmartDashboard.putString("Test", "Test");});


  }

  @Override
  public void testPeriodic () {}
}
