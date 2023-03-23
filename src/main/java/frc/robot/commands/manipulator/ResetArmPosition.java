package frc.robot.commands.manipulator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Manipulator;

/**
 * Models the arm's returning speed as the equation:
 * <p><code>y = a√x + bx³</code></p>
 * a = -0.00214471; b = -3.2873 * 10⁻¹⁷
 */
public class ResetArmPosition extends CommandBase {
    
    private final Manipulator manipulator;

    public ResetArmPosition (Manipulator manipulator) {

        this.manipulator = manipulator;
        this.addRequirements(this.manipulator);
    }

    @Override
    public void execute () { 
        
        double position = Math.abs(this.manipulator.getArmPosition());
        double power = (-0.00214471 * Math.sqrt(position)) + ((-3.2873 * Math.pow(10, -17)) * Math.pow(position, 3));

        if (power > -0.5) { power = -0.5; }
        this.manipulator.setArm(power); 
    }

    @Override
    public boolean isFinished () { return this.manipulator.startingArmLimitSwitch.get(); }

    @Override
    public void end (boolean interrupted) { 
        
        this.manipulator.setArm(0.0); 
        if (!interrupted) { this.manipulator.setAirLock(true); }
    }
}
