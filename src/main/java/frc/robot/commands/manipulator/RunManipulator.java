package frc.robot.commands.manipulator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Manipulator;

public class RunManipulator extends CommandBase {
    
    private final Manipulator manipulator;
    private final double direction;

    public RunManipulator (Manipulator manipulator, double direction) {

        this.manipulator = manipulator;
        this.direction = direction;
        this.addRequirements(this.manipulator);
    }
    
    @Override
    public void execute () { this.manipulator.setRollers(this.direction * 0.8); }

    @Override
    public boolean isFinished () { 

        if (this.direction > 0.0) { return !this.manipulator.isHoldingCone() && this.manipulator.isHoldingCube(); }
        if (this.direction < 0.0) { return this.manipulator.isHoldingCone() && !this.manipulator.isHoldingCube() ;}

        return true;
    }

    @Override
    public void end (boolean interrupted) { this.manipulator.setRollers(0.0); }
}
