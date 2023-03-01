package frc.robot.commands.manipulator;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Manipulator;

public class RunManipulator extends CommandBase {
    
    private final Manipulator manipulator;
    private final DoubleSupplier directionSupplier;

    public RunManipulator (Manipulator manipulator, DoubleSupplier directionSupplier) {

        this.manipulator = manipulator;
        this.directionSupplier = directionSupplier;
        this.addRequirements(this.manipulator);
    }
    
    @Override
    public void execute () { this.manipulator.setRollers(this.directionSupplier.getAsDouble() * 0.8); }

    @Override
    public boolean isFinished () { return false; }

    @Override
    public void end (boolean interrupted) { this.manipulator.setRollers(0.0); }
}
