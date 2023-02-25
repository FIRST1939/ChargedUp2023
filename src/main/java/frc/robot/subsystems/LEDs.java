package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LEDs extends SubsystemBase {
    
    private final AddressableLED addressableLED;
    private final AddressableLEDBuffer addressableLEDBuffer;

    public LEDs () {

        this.addressableLED = new AddressableLED(Constants.ElectronicConstants.LED_PWM);
        this.addressableLEDBuffer = new AddressableLEDBuffer(Constants.ElectronicConstants.LED_LENGTH); 

        this.addressableLED.setLength(this.addressableLEDBuffer.getLength());
        this.addressableLED.setData(this.addressableLEDBuffer);
        this.addressableLED.start();
    }

    public void setHue (Constants.ElectronicConstants.LED_COLORS ledColor) { 

        for (int i = 0; i < this.addressableLEDBuffer.getLength(); i++) { 
                
            int hue = ((ledColor.absoluteHue - ledColor.hueDeviation) + (i * (ledColor.hueDeviation) / this.addressableLEDBuffer.getLength())) % 180;
            this.addressableLEDBuffer.setHSV(i, hue, 255, 128); 
        }

        this.addressableLED.setData(this.addressableLEDBuffer);
    }
}
