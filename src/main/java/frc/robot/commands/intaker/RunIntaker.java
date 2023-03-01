package frc.robot.commands.intaker;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intaker;

public class RunIntaker extends CommandBase {
    
    private final Intaker intaker;
    private final DoubleSupplier powerSupplier;

    public RunIntaker (Intaker intaker, DoubleSupplier powerSupplier) {

        this.intaker = intaker;
        this.powerSupplier = powerSupplier;

        this.addRequirements(this.intaker);
    }

    @Override
    public void execute () { this.intaker.setRollers(this.powerSupplier.getAsDouble() * 0.8); }

    @Override
    public boolean isFinished () { return false; }

    @Override
    public void end (boolean interrupted) { this.intaker.setRollers(0.0); }
}
