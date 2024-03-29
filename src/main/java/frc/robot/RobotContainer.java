// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;
import java.util.function.Supplier;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Drive;
import frc.robot.commands.ZeroGyro;
import frc.robot.commands.autonomous.drivetrain.DriveRampedDistance;
import frc.robot.commands.autonomous.modes.Auto1GP;
import frc.robot.commands.autonomous.modes.Auto1GP_Balance;
import frc.robot.commands.autonomous.modes.Auto2GP_Balance_NoBump;
import frc.robot.commands.autonomous.modes.Auto3GP_Bump;
import frc.robot.commands.autonomous.modes.Auto3GP_NoBump;
import frc.robot.commands.autonomous.modes.AutoLow_Balance;
import frc.robot.commands.autonomous.modes.BalanceChargingStation;
import frc.robot.commands.cubert.Cuber;
import frc.robot.commands.cubert.FeedToBeamBreak;
import frc.robot.commands.cubert.FeedToShooter;
import frc.robot.commands.cubert.RunCubert;
import frc.robot.commands.cubert.SetShot;
import frc.robot.commands.manipulator.HoldArmPosition;
import frc.robot.commands.manipulator.Manipulate;
import frc.robot.commands.manipulator.ObtainPlatform;
import frc.robot.commands.manipulator.ResetArmPosition;
import frc.robot.commands.manipulator.RunManipulator;
import frc.robot.commands.manipulator.SetGamePiece;
import frc.robot.commands.manipulator.ZeroArm;
import frc.robot.subsystems.Cubert;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.Pneumatics;
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
  private final CommandXboxController driverTwo = new CommandXboxController(Constants.ControllerConstants.DRIVER_TWO);

  private final AHRS navX = new AHRS(SPI.Port.kMXP);
  private final WestCoastDrive westCoastDrive = new WestCoastDrive(navX);

  private final Cubert cubert = Cubert.getInstance();
  private final Manipulator manipulator = Manipulator.getInstance();

  private final LEDs leds = new LEDs();
  private GenericEntry autonomousWaitTimeEntry;
  private final SendableChooser<Supplier<Command>> autonomousChooser = new SendableChooser<>();

  public RobotContainer () {

    this.westCoastDrive.setDefaultCommand(
      new Drive(
        this.westCoastDrive, 
        () -> (-this.leftJoystick.getY()),
        () -> (-this.rightJoystick.getX())
      )
    );

    this.cubert.setDefaultCommand(new Cuber(this.cubert, () -> this.driverTwo.getRightX()));
    this.manipulator.setDefaultCommand(new Manipulate(this.manipulator, () -> (-this.driverTwo.getLeftY() * 0.7)));

    new Pneumatics();
    configureTriggers();
    configureAutonomousChooser();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureTriggers () {

    Shuffleboard.getTab("Competition")
      .add("Zero Gyro", new ZeroGyro(this.navX))
      .withWidget(BuiltInWidgets.kCommand)
      .withPosition(0, 3)
      .withSize(2, 1);

    Shuffleboard.getTab("Competition")
      .add("Zero Arm", new ZeroArm(this.manipulator))
      .withWidget(BuiltInWidgets.kCommand)
      .withPosition(2, 3)
      .withSize(2, 1);

    this.driverTwo.leftBumper().whileTrue(new RunCubert(this.cubert, () -> -1.0, () -> -1.0));
    this.driverTwo.rightBumper().whileTrue(new RunCubert(this.cubert, () -> 1.0, () -> 1.0));
    this.driverTwo.start().onTrue(new FeedToBeamBreak(this.cubert, () -> 0.0, () -> 0.8));
    
    this.driverTwo.povLeft().onTrue(new SetShot(this.cubert, this.leds, Constants.CubertConstants.SHOTS.LEFT));
    this.driverTwo.povRight().onTrue(new SetShot(this.cubert, this.leds, Constants.CubertConstants.SHOTS.RIGHT));
    this.driverTwo.povUp().onTrue(new SetShot(this.cubert, this.leds, Constants.CubertConstants.SHOTS.UP));
    this.driverTwo.povDown().onTrue(new SetShot(this.cubert, this.leds, Constants.CubertConstants.SHOTS.DOWN));
    new JoystickButton(this.rightJoystick, 16).onTrue(new SetShot(this.cubert, this.leds, Constants.CubertConstants.SHOTS.CRAZY));

    this.driverTwo.leftTrigger().whileTrue(Commands.parallel(new RunManipulator(this.manipulator, () -> -this.manipulator.getGamePiece() * this.driverTwo.getLeftTriggerAxis() * 0.75), new FeedToShooter(this.cubert, this.leds, () -> (this.manipulator.getGamePiece() == 1 ? 1.0 : 0.0) * this.driverTwo.getLeftTriggerAxis() * 0.5, () -> (this.manipulator.getGamePiece() == 1 ? 1.0 : 0.0) * this.driverTwo.getLeftTriggerAxis())));
    this.driverTwo.rightTrigger().whileTrue(Commands.parallel(new RunManipulator(this.manipulator, () -> this.manipulator.getGamePiece() * this.driverTwo.getRightTriggerAxis()), new FeedToShooter(this.cubert, this.leds, () -> (this.manipulator.getGamePiece() == 1 ? 1.0 : 0.0) * this.driverTwo.getRightTriggerAxis() * 0.5, () -> (this.manipulator.getGamePiece() == 1 ? 1.0 : 0.0) * -this.driverTwo.getRightTriggerAxis())));

    this.driverTwo.x().whileTrue(new ResetArmPosition(this.manipulator));
    this.driverTwo.a().whileTrue(new ObtainPlatform(this.manipulator));
    this.driverTwo.b().whileTrue(new HoldArmPosition(this.manipulator, Constants.ManipulatorConstants.ARM_POSITIONS.MIDDLE));
    this.driverTwo.y().whileTrue(new HoldArmPosition(this.manipulator, Constants.ManipulatorConstants.ARM_POSITIONS.HIGH));
    new JoystickButton(this.rightJoystick, 11).onTrue(new ZeroArm(this.manipulator));

    new JoystickButton(this.leftJoystick, 1).onTrue(new SetGamePiece(this.manipulator, this.leds, -1));
    new JoystickButton(this.rightJoystick, 1).onTrue(new SetGamePiece(this.manipulator, this.leds, 1));
    new JoystickButton(this.leftJoystick, 2).onTrue(new SetGamePiece(this.manipulator, this.leds, 0));
    new JoystickButton(this.rightJoystick, 2).onTrue(new SetGamePiece(this.manipulator, this.leds, 0));
  }

  private void configureAutonomousChooser () {

    this.autonomousChooser.addOption("Do Nothing", () -> new WaitCommand(1.0));
    this.autonomousChooser.addOption("Taxi", () -> new DriveRampedDistance(this.westCoastDrive, -4.0));
    this.autonomousChooser.addOption("1 GP", () -> new Auto1GP(this.westCoastDrive, this.manipulator, this.leds));
    this.autonomousChooser.addOption("Balance", () -> new BalanceChargingStation(this.westCoastDrive, this.navX));
    this.autonomousChooser.addOption("Low + Balance", () -> new AutoLow_Balance(this.westCoastDrive, this.cubert, this.manipulator, this.navX, this.leds));
    this.autonomousChooser.addOption("1 GP + Balance", () -> new Auto1GP_Balance(this.westCoastDrive, this.manipulator, this.navX, this.leds));
    this.autonomousChooser.addOption("2 GP + Balance (No Bump)", () -> new Auto2GP_Balance_NoBump(this.westCoastDrive, this.navX, this.cubert, this.manipulator, this.leds));
    this.autonomousChooser.setDefaultOption("3 GP (Bump)", () -> new Auto3GP_Bump(this.westCoastDrive, this.cubert, this.manipulator, this.leds));
    this.autonomousChooser.addOption("3 GP (No Bump)", () -> new Auto3GP_NoBump(this.westCoastDrive, this.cubert, this.manipulator, this.leds));
    
    Shuffleboard.getTab("Competition")
      .add("Autonomous Chooser", this.autonomousChooser)
      .withWidget(BuiltInWidgets.kComboBoxChooser)
      .withPosition(0, 0)
      .withSize(2, 1);

    this.autonomousWaitTimeEntry = Shuffleboard.getTab("Competition")
      .add("Autonomous Wait Time", 0.0)
      .withWidget(BuiltInWidgets.kNumberSlider)
      .withProperties(Map.of("MIN", 0.0, "MAX", 10.0, "BLOCK INCREMENT", 0.25))
      .withPosition(0, 1)
      .withSize(2, 1)
      .getEntry();
  }
  
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand () { return this.autonomousChooser.getSelected().get(); }
  public double getAutonomousWaitTime () { return this.autonomousWaitTimeEntry.getDouble(0.0); }
  
  public void setLEDColor (Constants.ElectronicConstants.LED_COLORS ledColor) { this.leds.setColor(ledColor, true); }
  public Constants.ElectronicConstants.LED_COLORS getLEDColor () { return this.leds.ledColor; }

  public void setLEDMode (Constants.ElectronicConstants.LED_MODES ledMode) { this.leds.setMode(ledMode); }
  public Constants.ElectronicConstants.LED_MODES getLEDMode () { return this.leds.ledMode; }
}
