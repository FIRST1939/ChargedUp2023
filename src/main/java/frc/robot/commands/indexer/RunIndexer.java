package frc.robot.commands.indexer;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;

public class RunIndexer extends CommandBase {
    
    private final Indexer indexer;
    private final DoubleSupplier directionSupplier;

    public RunIndexer (Indexer indexer, DoubleSupplier directionSupplier) {

        this.indexer = indexer;
        this.directionSupplier = directionSupplier;
        this.addRequirements(this.indexer);
    }
    
    @Override
    public void execute () { this.indexer.setIndexer(this.directionSupplier.getAsDouble() * 0.8); }

    @Override
    public boolean isFinished () { return false; }

    @Override
    public void end (boolean interrupted) { this.indexer.setIndexer(0.0); }
}
