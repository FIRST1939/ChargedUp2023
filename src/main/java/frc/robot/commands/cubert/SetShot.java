package frc.robot.commands.cubert;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Constants.CubertConstants.SHOTS;
import frc.robot.subsystems.Cubert;
import frc.robot.subsystems.LEDs;

public class SetShot extends CommandBase {
    
    private final Cubert cubert;
    private final LEDs leds;
    private final int velocity;

    public SetShot (Cubert cubert, LEDs leds, SHOTS shot) {

        this.cubert = cubert;
        this.leds = leds;
        this.velocity = shot.velocity;

        this.addRequirements(this.cubert, this.leds);
    }

    @Override
    public void initialize () { if (this.velocity == 0) { this.leds.setColor(this.leds.ledColor, true); } }

    @Override
    public void execute () { this.cubert.setShooter(this.velocity); }

    @Override
    public boolean isFinished () { return Math.abs(this.cubert.getShooter() - this.velocity) <= 350; }

    @Override
    public void end (boolean interrupted) { 
        
        if (interrupted) { this.cubert.setShooter(0.0); } 
        if (this.velocity != 0) { this.leds.setColor(Constants.ElectronicConstants.LED_COLORS.READY, false); }
    }
}
