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

    public LEDs (AddressableLEDBuffer addressableLEDBuffer) {

        for (int ledPortID : Constants.ElectronicConstants.LED_PWMS) { this.addressableLEDs.add(new AddressableLED(ledPortID)); }
        this.addressableLEDBuffer = addressableLEDBuffer;

        for (AddressableLED addressableLED : this.addressableLEDs) {

            addressableLED.setLength(this.addressableLEDBuffer.getLength());
            addressableLED.setData(this.addressableLEDBuffer);
            addressableLED.start();
        }
    }

    public void setHue (Constants.ElectronicConstants.LED_COLORS ledColor) { 
        
        for (int i = 0; i < this.addressableLEDBuffer.getLength(); i++) { 
            
            int hue = ((ledColor.absoluteHue - ledColor.hueDeviation) + (i * (ledColor.hueDeviation) / this.addressableLEDBuffer.getLength())) % 180;
            this.addressableLEDBuffer.setHSV(i, hue, 255, 128); 
        }

        for (AddressableLED addressableLED : this.addressableLEDs) { addressableLED.setData(this.addressableLEDBuffer); }
    }
}
