// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.Supplier;

import com.kauailabs.navx.frc.AHRS;

import org.photonvision.PhotonCamera;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.Drive;
import frc.robot.commands.ResetGyro;
import frc.robot.commands.SetLEDs;
import frc.robot.commands.ZeroArm;
import frc.robot.commands.autonomous.DriveAprilTagDistance;
import frc.robot.commands.autonomous.modes.Auto1GP_Taxi;
import frc.robot.commands.indexer.Index;
import frc.robot.commands.indexer.ZeroIndexer;
import frc.robot.commands.manipulator.HoldArmPosition;
import frc.robot.commands.manipulator.Manipulate;
import frc.robot.commands.manipulator.ResetArmPosition;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.Photonvision;
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
  private final Photonvision photonvision = new Photonvision(new PhotonCamera("OV5647"));
  private final WestCoastDrive westCoastDrive = new WestCoastDrive(navX);
  
  private final Manipulator manipulator = new Manipulator();
  private final Indexer indexer = new Indexer();

  public final LEDs leds = new LEDs();

  private final SendableChooser<Supplier<Command>> autonomousChooser = new SendableChooser<>();

  public RobotContainer () {

    this.westCoastDrive.setDefaultCommand(
      new Drive(
        this.westCoastDrive, 
        () -> (-this.leftJoystick.getY()),
        () -> (-this.rightJoystick.getX())
      )
    );

    this.indexer.setDefaultCommand(
      new Index(
        this.indexer,
        () -> (-this.driverTwo.getLeftY())
      )
    );

    this.manipulator.setDefaultCommand(
      new Manipulate(
        this.manipulator, 
        () -> (-this.driverTwo.getRightY()), 
        () -> (this.driverTwo.getLeftTriggerAxis() - this.driverTwo.getRightTriggerAxis())
      )
    );

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

    SmartDashboard.putData("Reset Gyro", new ResetGyro(this.navX));
    SmartDashboard.putData("Zero Indexer", new ZeroIndexer(this.indexer));
    SmartDashboard.putData("Zero Arm", new ZeroArm(this.manipulator));
    
    this.driverTwo.x().onTrue(new ResetArmPosition(this.manipulator, 0.75));
    this.driverTwo.x().onTrue(new SetLEDs(this.leds, Constants.ElectronicConstants.LED_COLORS.RAINBOW));

    this.driverTwo.a().whileTrue(new HoldArmPosition(this.manipulator, Constants.ManipulatorConstants.ARM_POSITIONS.STATION));
    this.driverTwo.b().whileTrue(new HoldArmPosition(this.manipulator, Constants.ManipulatorConstants.ARM_POSITIONS.MIDDLE));
    this.driverTwo.y().whileTrue(new HoldArmPosition(this.manipulator, Constants.ManipulatorConstants.ARM_POSITIONS.TOP));

    new JoystickButton(this.leftJoystick, 1).onTrue(new SetLEDs(this.leds, Constants.ElectronicConstants.LED_COLORS.CONE));
    new JoystickButton(this.rightJoystick, 1).onTrue(new SetLEDs(this.leds, Constants.ElectronicConstants.LED_COLORS.CUBE));
  }

  private void configureAutonomousChooser () {

    this.autonomousChooser.setDefaultOption("Do Nothing", () -> new WaitCommand(1.0));
    this.autonomousChooser.addOption("1 GP + Taxi", () -> new Auto1GP_Taxi(this.westCoastDrive, this.manipulator, this.photonvision));

    SmartDashboard.putData("Autonomous Chooser", this.autonomousChooser);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand () { return this.autonomousChooser.getSelected().get(); }
}
