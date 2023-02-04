package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.WestCoastDrive;

public class Drive extends CommandBase {
    
    private final WestCoastDrive westCoastDrive;
    private final DoubleSupplier speedSupplier;
    private final DoubleSupplier rotationSupplier;

    public Drive (WestCoastDrive westCoastDrive, DoubleSupplier speedSupplier, DoubleSupplier rotationSupplier) {

        this.westCoastDrive = westCoastDrive;
        this.speedSupplier = speedSupplier;
        this.rotationSupplier = rotationSupplier;
    
        this.addRequirements(this.westCoastDrive);
    }

    @Override
    public void execute () {

        // Get Joystick Inputs
        double speed = this.speedSupplier.getAsDouble();;
        double rotation = this.rotationSupplier.getAsDouble();

        // Apply Deadbands
        speed = this.deadband(speed);
        rotation = this.deadband(rotation);

        // Update Speeds
        this.westCoastDrive.drive(speed, rotation);
    }

    private double deadband (double value) {

        if (Math.abs(value) >= Constants.ControllerConstants.JOYSTICK_DEADBAND) {
    
          if (value > 0.0) {
    
            return (value - Constants.ControllerConstants.JOYSTICK_DEADBAND) / (1.0 - Constants.ControllerConstants.JOYSTICK_DEADBAND);
          } else {
    
            return (value + Constants.ControllerConstants.JOYSTICK_DEADBAND) / (1.0 - Constants.ControllerConstants.JOYSTICK_DEADBAND);
          }
        }
    
        return 0.0;
      }

    @Override
    public void end (boolean interrupted) { this.westCoastDrive.stop(); }
}
