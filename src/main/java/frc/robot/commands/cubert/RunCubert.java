package frc.robot.commands.cubert;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cubert;

public class RunCubert extends CommandBase {
    
    private final Cubert cubert;
    private final DoubleSupplier intakeSupplier;
    private final DoubleSupplier indexerSupplier;

    public RunCubert (Cubert cubert, DoubleSupplier intakeSupplier, DoubleSupplier indexerSupplier) {

        this.cubert = cubert;
        this.intakeSupplier = intakeSupplier;
        this.indexerSupplier = indexerSupplier;

        this.addRequirements(this.cubert);
    }

    @Override
    public void initialize () { if (this.intakeSupplier.getAsDouble() != 0.0) { this.cubert.setIntakePistons((Boolean) true); } }

    @Override
    public void execute () {

        this.cubert.setIntakeRollers(this.intakeSupplier.getAsDouble());
        this.cubert.setIndexer(this.indexerSupplier.getAsDouble());
    }

    @Override
    public boolean isFinished () { return this.cubert.isCubeLoaded(); }

    @Override
    public void end (boolean interrupted) {

        this.cubert.setIntakePistons((Boolean) false);
        this.cubert.setIntakeRollers(0.0);
        this.cubert.setIndexer(0.0);
    }
}
