package frc.robot.commands.intaker;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intaker;

public class RunSlider extends CommandBase {
    
    private final Intaker intaker;
    private final DoubleSupplier intakerSupplier;

    public RunSlider (Intaker intaker, DoubleSupplier intakerSupplier) {

        this.intaker = intaker;
        this.intakerSupplier = intakerSupplier;
        this.addRequirements(this.intaker);
    }

    @Override
    public void execute () { this.intaker.setSlider(this.intakerSupplier.getAsDouble() * 0.8); }

    @Override
    public boolean isFinished () { return false; }

    @Override
    public void end (boolean interrupted) { this.intaker.setSlider(0.0); }
}
