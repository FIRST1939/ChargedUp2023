package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Manipulator;

public class RunManipulator extends CommandBase {
    
    private final Manipulator manipulator;
    private final double power;

    public RunManipulator (Manipulator manipulator, double power) {

        this.manipulator = manipulator;
        this.power = power;
        this.addRequirements(this.manipulator);
    }

    @Override
    public void execute () { this.manipulator.setRollers(this.power); }

    // TODO Beam Breaks
    @Override
    public boolean isFinished () { return false; }

    @Override
    public void end (boolean interrupted) { this.manipulator.setArm(0.0); }
}
