package frc.robot.commands.intake;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intae;

public class Intake extends CommandBase {
    
    private final Intake intake;
    private final DoubleSupplier sliderSupplier;
    private final DoubleSupplier rollerSupplier;

    public Intake (Intake intake, DoubleSupplier sliderSupplier, DoubleSupplier rollerSupplier) {

        this.intake = intake;
        this.sliderSupplier = sliderSupplier;
        this.rollerSupplier = rollerSupplier;
    
        this.addRequirements(this.intake);
    }

    @Override
    public void execute () {

        this.intake.setSlider(this.sliderSupplier.getAsDouble());
        this.intake.setRollers(this.rollerSupplier.getAsDouble());
    }

    @Override
    public void end (boolean interrupted) { 

        this.intake.setSlider(0.0);
        this.intae.setRollers(0.0);
    }
}
