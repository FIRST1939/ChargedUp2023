package frc.robot.commands.cubert;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Cubert;
import frc.robot.subsystems.LEDs;

public class FeedToShooter extends CommandBase {
    
    private final Cubert cubert;
    private final LEDs leds;
    private final DoubleSupplier intakeSupplier;
    private final DoubleSupplier indexerSupplier;
    private boolean initialCubeLoadedValue;

    public FeedToShooter (Cubert cubert, LEDs leds, DoubleSupplier intakeSupplier, DoubleSupplier indexerSupplier) {

        this.cubert = cubert;
        this.leds = leds;
        this.intakeSupplier = intakeSupplier;
        this.indexerSupplier = indexerSupplier;

        this.addRequirements(this.cubert, this.leds);
    }

    @Override
    public void initialize () { this.initialCubeLoadedValue = this.cubert.isCubeLoaded(); }

    @Override
    public void execute () { 
    
        this.cubert.setIntakeRollers(this.intakeSupplier.getAsDouble());
        this.cubert.setIndexer(this.indexerSupplier.getAsDouble()); 
    }

    @Override
    public boolean isFinished () { return this.cubert.isCubeLoaded() != this.initialCubeLoadedValue; }

    @Override
    public void end (boolean interrupted) { 
    
        this.cubert.setIntakeRollers(0.0);
        this.cubert.setIndexer(0.0);

        this.leds.setColor(this.leds.ledColor, true); 
    }
}
