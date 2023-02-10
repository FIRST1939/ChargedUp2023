package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LEDs extends SubsystemBase {
    
    private final List<AddressableLED> addressableLEDs = new ArrayList<AddressableLED>();
    private final AddressableLEDBuffer addressableLEDBuffer;

    private Constants.ElectronicConstants.LED_COLORS lastLEDColor;
    private int hueIncrement = 0;

    public LEDs (AddressableLEDBuffer addressableLEDBuffer) {

        for (int ledPortID : Constants.ElectronicConstants.LED_PWMS) { this.addressableLEDs.add(new AddressableLED(ledPortID)); }
        this.addressableLEDBuffer = addressableLEDBuffer;

        for (AddressableLED addressableLED : this.addressableLEDs) {

            addressableLED.setLength(this.addressableLEDBuffer.getLength());
            addressableLED.setData(this.addressableLEDBuffer);
            addressableLED.start();
        }
    }

    public void animateHue (Constants.ElectronicConstants.LED_COLORS ledColor) {
        
        if (this.lastLEDColor != ledColor) {

            this.hueIncrement = 0;
            this.lastLEDColor = ledColor;
        }

        for (int i = 0; i < this.addressableLEDBuffer.getLength(); i++) {

            int hueIncrease = (this.hueIncrement + (i * (ledColor.hueDeviation * 2) / 10)) % (ledColor.hueDeviation * 2);
            int hue = (((ledColor.absoluteHue - ledColor.hueDeviation) % 180) + hueIncrease) % 180;
            this.addressableLEDBuffer.setHSV(i, hue, 255, 128);
        }

        this.hueIncrement += ((ledColor.hueDeviation * 2) / 60);
        for (AddressableLED addressableLED : this.addressableLEDs) { addressableLED.setData(this.addressableLEDBuffer); }
    }
}
