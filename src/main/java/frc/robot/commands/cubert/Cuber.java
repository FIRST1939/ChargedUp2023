package frc.robot.commands.cubert;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cubert;

public class Cuber extends CommandBase {
    
    private final Cubert cubert;
    private final DoubleSupplier indexerSupplier;

    public Cuber (Cubert cubert, DoubleSupplier indexerSupplier) {

        this.cubert = cubert;
        this.indexerSupplier = indexerSupplier;

        this.addRequirements(this.cubert);
    }

    @Override
    public void execute () { this.cubert.setIndexer(this.indexerSupplier.getAsDouble()); }

    @Override
    public void end (boolean interrupted) { this.cubert.setIndexer(0.0); }
}
