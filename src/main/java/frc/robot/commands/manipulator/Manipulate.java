package frc.robot.commands.manipulator;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
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
    public void execute () { this.manipulator.setArm(this.armSupplier.getAsDouble()); }

    @Override
    public void end (boolean interrupted) { this.manipulator.setArm(0.0); }
}
