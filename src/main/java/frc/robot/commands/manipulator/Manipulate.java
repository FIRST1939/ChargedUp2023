package frc.robot.commands.manipulator;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Manipulator;

public class Manipulate extends CommandBase {
    
    private final Manipulator manipulator;
    private final DoubleSupplier armSupplier;
    private final DoubleSupplier rollerSupplier;

    public Manipulate (Manipulator manipulator, DoubleSupplier armSupplier, DoubleSupplier rollerSupplier) {

        this.manipulator = manipulator;
        this.armSupplier = armSupplier;
        this.rollerSupplier = rollerSupplier;
    
        this.addRequirements(this.manipulator);
    }

    @Override
    public void execute () {

        this.manipulator.setArm(this.armSupplier.getAsDouble() / 20000.0);
        this.manipulator.setRollers(this.rollerSupplier.getAsDouble());
    }

    @Override
    public void end (boolean interrupted) { 

        this.manipulator.setArm(0.0);
        this.manipulator.setRollers(0.0);
    }
}
