package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.subsystems.LEDs;

public class SetLEDs extends InstantCommand {
    
    private final LEDs leds;
    private final Constants.ElectronicConstants.LED_COLORS ledColor;

    public SetLEDs (LEDs leds, Constants.ElectronicConstants.LED_COLORS ledColor) {
        
        this.leds = leds;
        this.ledColor = ledColor;
    }

    @Override
    public void initialize () { this.leds.setHue(this.ledColor, true); }
}
