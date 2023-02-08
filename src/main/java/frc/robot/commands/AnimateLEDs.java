package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.LEDs;

public class AnimateLEDs extends CommandBase {
    
    private final LEDs leds;
    private final Constants.ElectronicConstants.LED_COLORS ledColor;

    public AnimateLEDs (LEDs leds, Constants.ElectronicConstants.LED_COLORS ledColor) {
        
        this.leds = leds;
        this.ledColor = ledColor;
    }

    @Override
    public void execute () { this.leds.animateHue(this.ledColor); }

    @Override
    public boolean isFinished () { return false; }
}
