package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LEDs extends SubsystemBase {
    
    private final AddressableLED addressableLED;
    private final AddressableLEDBuffer addressableLEDBuffer;

    private Constants.ElectronicConstants.LED_COLORS lastLEDColor;
    private int hueIncrement = 0;

    public LEDs (AddressableLED addressableLED, AddressableLEDBuffer addressableLEDBuffer) {

        this.addressableLED = addressableLED;
        this.addressableLEDBuffer = addressableLEDBuffer;

        this.addressableLED.setLength(this.addressableLEDBuffer.getLength());
        this.addressableLED.setData(this.addressableLEDBuffer);
        this.addressableLED.start();
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
        this.addressableLED.setData(this.addressableLEDBuffer);
    }
}
