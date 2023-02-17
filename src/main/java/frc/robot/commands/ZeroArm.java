package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Manipulator;

public class ZeroArm extends CommandBase {
    
    private Manipulator manipulator;

    public ZeroArm (Manipulator manipulator) {

        this.manipulator = manipulator;
        this.addRequirements(this.manipulator);
    }

    @Override
    public void initialize () {

        this.manipulator.zeroArm();
    }

    @Override
    public boolean isFinished () {

        return true;
    }
}
