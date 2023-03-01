package frc.robot.commands.intaker;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intaker;

public class Intake extends CommandBase {
    
    private final Intaker intaker;
    private final DoubleSupplier sliderSupplier;

    public Intake (Intaker intaker, DoubleSupplier sliderSupplier) {

        this.intaker = intaker;
        this.sliderSupplier = sliderSupplier;
    
        this.addRequirements(this.intaker);
    }

    @Override
    public void execute () { this.intaker.setSlider(this.sliderSupplier.getAsDouble()); }

    @Override
    public void end (boolean interrupted) { this.intaker.setSlider(0.0); }
}
