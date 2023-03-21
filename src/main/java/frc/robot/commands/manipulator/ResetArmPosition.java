package frc.robot.commands.manipulator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Manipulator;

public class ResetArmPosition extends CommandBase {
    
    private final Manipulator manipulator;
    private final double power;
    private double direction;

    public ResetArmPosition (Manipulator manipulator, double power) {

        this.manipulator = manipulator;
        this.power = power;
    
        this.addRequirements(this.manipulator);
    }

    @Override
    public void initialize () { this.direction = -Math.signum(this.manipulator.getArmPosition()); }

    @Override
    public void execute () { this.manipulator.setArm(this.direction * this.power); }

    @Override
    public boolean isFinished () { return this.manipulator.armLimitSwitch.get(); }

    @Override
    public void end (boolean interrupted) { 
        
        this.manipulator.setArm(0.0); 
        this.manipulator.setAirLock(true);
    }
}
