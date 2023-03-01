package frc.robot.commands.indexer;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Manipulator;

public class RunIndexer extends CommandBase {
    
    private final Indexer indexer;
    private final Manipulator manipulator;

    private final DoubleSupplier indexerSupplier;
    private final DoubleSupplier manipulatorSupplier;

    public RunIndexer (Indexer indexer, Manipulator manipulator, DoubleSupplier indexerSupplier, DoubleSupplier manipulatorSupplier) {

        this.indexer = indexer;
        this.manipulator = manipulator;

        this.indexerSupplier = indexerSupplier;
        this.manipulatorSupplier = manipulatorSupplier;

        this.addRequirements(this.indexer, this.manipulator);
    }
    
    @Override
    public void execute () { 
        
        this.indexer.setIndexer(this.indexerSupplier.getAsDouble() * 0.8);
        this.manipulator.setRollers(this.manipulatorSupplier.getAsDouble()* 0.8);
    }

    @Override
    public boolean isFinished () { return false; }

    @Override
    public void end (boolean interrupted) { 
        
        this.indexer.setIndexer(0.0);
        this.manipulator.setRollers(0.0);
    }
}
