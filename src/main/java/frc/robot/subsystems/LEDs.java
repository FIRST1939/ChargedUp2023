package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LEDs extends SubsystemBase {
    
    private final List<AddressableLED> addressableLEDs = new ArrayList<AddressableLED>();
    private final List<AddressableLEDBuffer> addressableLEDBuffers = new ArrayList<AddressableLEDBuffer>();

    public LEDs () {

        for (int ledPortID : Constants.ElectronicConstants.LED_PWMS) { this.addressableLEDs.add(new AddressableLED(ledPortID)); }
        for (int ledLength : Constants.ElectronicConstants.LED_LENGTHS) { this.addressableLEDBuffers.add(new AddressableLEDBuffer(ledLength)); }

        for (int i = 0; i < this.addressableLEDs.size(); i++) {

            AddressableLED addressableLED = this.addressableLEDs.get(i);
            AddressableLEDBuffer addressableLEDBuffer = this.addressableLEDBuffers.get(i);

            addressableLED.setLength(addressableLEDBuffer.getLength());
            addressableLED.setData(addressableLEDBuffer);
            addressableLED.start();
        }
    }

    public void setHue (Constants.ElectronicConstants.LED_COLORS ledColor) { 
        
        for (int i = 0; i < this.addressableLEDs.size(); i++) {

            AddressableLED addressableLED = this.addressableLEDs.get(i);
            AddressableLEDBuffer addressableLEDBuffer = this.addressableLEDBuffers.get(i);

            for (int j = 0; j < addressableLEDBuffer.getLength(); j++) { 
                
                int hue = ((ledColor.absoluteHue - ledColor.hueDeviation) + (i * (ledColor.hueDeviation) / addressableLEDBuffer.getLength())) % 180;
                addressableLEDBuffer.setHSV(i, hue, 255, 128); 
            }

            addressableLED.setData(addressableLEDBuffer);
        }
    }
}
