package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LEDs extends SubsystemBase {
    
    private final AddressableLED addressableLED;
    private final AddressableLEDBuffer addressableLEDBuffer;

    private final Timer timer = new Timer();
    private int animatedRainbowStartingHue = 0;
    public Constants.ElectronicConstants.LED_COLORS ledColor = Constants.ElectronicConstants.LED_COLORS.RAINBOW;

    public LEDs () {

        this.addressableLED = new AddressableLED(Constants.ElectronicConstants.LED_PWM);
        this.addressableLEDBuffer = new AddressableLEDBuffer(Constants.ElectronicConstants.LED_LENGTHS.stream().mapToInt(Integer::intValue).sum()); 

        this.addressableLED.setLength(this.addressableLEDBuffer.getLength());
        this.addressableLED.setData(this.addressableLEDBuffer);
        this.addressableLED.start();

        this.timer.start();
    }

    public void periodic () {

        if (this.ledColor != Constants.ElectronicConstants.LED_COLORS.RAINBOW) { return; }
        
        if (this.timer.get() >= 0.02) {

            this.animatedRainbowStartingHue += 2;
            this.setHue(this.ledColor, true);
            this.timer.reset();
        }
    }

    public void setHue (Constants.ElectronicConstants.LED_COLORS ledColor, boolean persist) { 

        int ledIndex = 0;
        int hueLeadup = 0;

        if (ledColor == Constants.ElectronicConstants.LED_COLORS.RAINBOW) { hueLeadup = this.animatedRainbowStartingHue; }
        
        for (int stripIndex = 0; stripIndex < Constants.ElectronicConstants.LED_LENGTHS.size(); stripIndex++) {

            int ledLength = Constants.ElectronicConstants.LED_LENGTHS.get(stripIndex);
            int ledDirection = Constants.ElectronicConstants.LED_DIRECTIONS.get(stripIndex);

            if (ledDirection == 1) {

                for (int i = 0; i < ledLength; i++) { 

                    int leadup = (hueLeadup + (i * (2 * ledColor.hueDeviation) / ledLength)) % (2 * ledColor.hueDeviation);
                    int hue = (leadup + (ledColor.absoluteHue - ledColor.hueDeviation)) % 180;
                    this.addressableLEDBuffer.setHSV(ledIndex, hue, 255, 128); 

                    ledIndex++;
                }
            } else if (ledDirection == -1) {
            
                for (int i = ledLength; i > 0; i--) { 
                
                    int leadup = (hueLeadup + (i * (2 * ledColor.hueDeviation) / ledLength)) % (2 * ledColor.hueDeviation);
                    int hue = (leadup + (ledColor.absoluteHue - ledColor.hueDeviation)) % 180;
                    this.addressableLEDBuffer.setHSV(ledIndex, hue, 255, 128); 

                    ledIndex++;
                }
            }
        }

        this.addressableLED.setData(this.addressableLEDBuffer);
        if (persist) { this.ledColor = ledColor; }
    }
}
