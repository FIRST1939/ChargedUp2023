package frc.robot.commands.cubert;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cubert;
import frc.robot.subsystems.LEDs;

public class RunCubert extends CommandBase {
    
    private final Cubert cubert;
    private final LEDs leds;
    private final DoubleSupplier intakeSupplier;
    private final DoubleSupplier indexerSupplier;
    private boolean initialBeamBreakValue;

    public RunCubert (Cubert cubert, LEDs leds, DoubleSupplier intakeSupplier, DoubleSupplier indexerSupplier) {

        this.cubert = cubert;
        this.leds = leds;
        this.intakeSupplier = intakeSupplier;
        this.indexerSupplier = indexerSupplier;

        this.addRequirements(this.cubert, this.leds);
    }

    @Override
    public void initialize () { 
        
        if (this.intakeSupplier.getAsDouble() != 0.0) { this.cubert.setIntakePistons((Boolean) true); } 
        this.initialBeamBreakValue = !this.cubert.isCubeLoaded();
    }

    @Override
    public void execute () {

        this.cubert.setIntakeRollers(this.intakeSupplier.getAsDouble());
        this.cubert.setIndexer(this.indexerSupplier.getAsDouble());
    }

    @Override
    public boolean isFinished () { 
        
        if (this.initialBeamBreakValue && this.intakeSupplier.getAsDouble() != 0.0) { return this.cubert.isCubeLoaded(); }
        if (!this.initialBeamBreakValue && this.indexerSupplier.getAsDouble() > 0.0) { return !this.cubert.isCubeLoaded(); }
        return false;
    }

    @Override
    public void end (boolean interrupted) {

        this.cubert.setIntakePistons((Boolean) false);
        this.cubert.setIntakeRollers(0.0);
        this.cubert.setIndexer(0.0);

        if (!this.initialBeamBreakValue) { this.leds.restoreDisplay(); }
    }
}
