package frc.robot.commands.cubert;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.CubertConstants.SHOTS;
import frc.robot.subsystems.Cubert;
import frc.robot.subsystems.LEDs;

public class SetShot extends CommandBase {
    
    private final Cubert cubert;
    private final int velocity;
    private final LEDs leds;

    public SetShot (Cubert cubert, SHOTS shot, LEDs leds) {

        this.cubert = cubert;
        this.velocity = shot.velocity;
        this.leds = leds;

        this.addRequirements(this.cubert, this.leds);
    }

    @Override
    public void execute () { 
        
        this.cubert.setShooter(this.velocity); 
        this.leds.setPercentage(this.cubert.getShooter() / (this.velocity - 350));
    }

    @Override
    public boolean isFinished () { return Math.abs(this.cubert.getShooter() - this.velocity) <= 350; }

    @Override
    public void end (boolean interrupted) { if (interrupted) { this.cubert.setShooter(0.0); } }
}
