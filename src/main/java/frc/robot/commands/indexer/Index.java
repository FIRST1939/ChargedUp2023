package frc.robot.commands.indexer;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;

public class Index extends CommandBase {
 
    private final Indexer indexer;
    private final DoubleSupplier indexerSupplier;

    public Index (Indexer indexer, DoubleSupplier indexerSupplier) {

        this.indexer = indexer;
        this.indexerSupplier = indexerSupplier;
        this.addRequirements(this.indexer);
    }

    @Override
    public void execute () { this.indexer.setIndexer(this.indexerSupplier.getAsDouble()); }

    @Override
    public void end (boolean interrupted) { this.indexer.setIndexer(0.0); }
}
