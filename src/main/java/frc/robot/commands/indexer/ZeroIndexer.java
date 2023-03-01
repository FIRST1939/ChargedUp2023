package frc.robot.commands.indexer;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Indexer;

public class ZeroIndexer extends InstantCommand {
    

    private Indexer indexer;

    public ZeroIndexer (Indexer indexer) {

        this.indexer = indexer;
        this.addRequirements(this.indexer);
    }

    @Override
    public void initialize () { this.indexer.zeroIndexer(); }
}
