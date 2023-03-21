package frc.robot.commands.manipulator;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Manipulator;

public class ZeroArm extends InstantCommand {
    
    private Manipulator manipulator;

    public ZeroArm (Manipulator manipulator) {

        this.manipulator = manipulator;
        this.addRequirements(this.manipulator);
    }

    @Override
    public void initialize () { this.manipulator.zeroArm(); }
}
