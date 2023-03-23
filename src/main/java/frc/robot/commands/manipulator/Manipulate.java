package frc.robot.commands.manipulator;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Manipulator;

public class Manipulate extends CommandBase {
    
    private final Manipulator manipulator;
    private final DoubleSupplier armSupplier;

    public Manipulate (Manipulator manipulator, DoubleSupplier armSupplier) {

        this.manipulator = manipulator;
        this.armSupplier = armSupplier;
        this.addRequirements(this.manipulator);
    }

    @Override
    public void execute () { this.manipulator.setArm(this.deadband(this.armSupplier.getAsDouble())); }

    private double deadband (double value) {

        if (Math.abs(value) >= Constants.ControllerConstants.CONTROLLER_DEADBAND) {
    
            if (value > 0.0) { return (value - Constants.ControllerConstants.CONTROLLER_DEADBAND) / (1.0 - Constants.ControllerConstants.CONTROLLER_DEADBAND); }
            else { return (value + Constants.ControllerConstants.CONTROLLER_DEADBAND) / (1.0 - Constants.ControllerConstants.CONTROLLER_DEADBAND); }
        }
    
        return 0.0;
    }

    @Override
    public void end (boolean interrupted) { this.manipulator.setArm(0.0); }
}
