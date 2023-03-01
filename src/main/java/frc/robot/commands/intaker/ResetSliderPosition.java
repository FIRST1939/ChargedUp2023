package frc.robot.commands.intaker;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intaker;

public class ResetSliderPosition extends CommandBase {
    
    private final Intaker intaker;
    private final double power;
    private double direction;

    public ResetSliderPosition (Intaker intaker, double power) {

        this.intaker = intaker;
        this.power = power;
    
        this.addRequirements(this.intaker);
    }

    @Override
    public void initialize () { this.direction = -Math.signum(this.intaker.getSliderPosition()); }

    @Override
    public void execute () { this.intaker.setSlider(this.direction * this.power); }

    @Override
    public boolean isFinished () { return this.intaker.getSliderPosition() <= 500; }

    @Override
    public void end (boolean interrupted) { this.intaker.setSlider(0.0); }
}
