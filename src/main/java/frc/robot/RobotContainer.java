// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.Supplier;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Drive;
import frc.robot.commands.Manipulate;
import frc.robot.commands.ResetGyro;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private final Joystick leftJoystick = new Joystick(Constants.ControllerConstants.LEFT_JOYSTICK);
  private final Joystick rightJoystick = new Joystick(Constants.ControllerConstants.RIGHT_JOYSTICK);
  private final XboxController driverTwo = new XboxController(Constants.ControllerConstants.DRIVER_TWO);

  private final AHRS navX = new AHRS(SPI.Port.kMXP);
  private final WestCoastDrive westCoastDrive = new WestCoastDrive(navX);
  private final Manipulator manipulator = new Manipulator();

  private final SendableChooser<Supplier<Command>> autonomousChooser = new SendableChooser<>();

  public RobotContainer () {

    this.westCoastDrive.setDefaultCommand(
      new Drive(
        this.westCoastDrive, 
        () -> (-this.leftJoystick.getY()),
        () -> (this.rightJoystick.getX())
      )
    );

    this.manipulator.setDefaultCommand(
      new Manipulate(
        this.manipulator, 
        () -> (this.driverTwo.getRightY()), 
        () -> (this.driverTwo.getRightTriggerAxis() - this.driverTwo.getLeftTriggerAxis())
      )
    );

    configureButtonBindings();
    configureAutonomousChooser();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings () {

    SmartDashboard.putData("Reset Gyro", new ResetGyro(this.navX));
  }

  private void configureAutonomousChooser () {

    this.autonomousChooser.setDefaultOption("Do Nothing", () -> new WaitCommand(1.0));

    SmartDashboard.putData("Autonomous Chooser", this.autonomousChooser);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand () {

    return this.autonomousChooser.getSelected().get();
  }
}
