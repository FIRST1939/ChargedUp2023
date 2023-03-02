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
        this.addressableLEDBuffer = new AddressableLEDBuffer(Constants.ElectronicConstants.LED_LENGTHS.stream().mapToInt(Integer::intValue).sum()); 

        this.addressableLED.setLength(this.addressableLEDBuffer.getLength());
        this.addressableLED.setData(this.addressableLEDBuffer);
        this.addressableLED.start();
    }

    public void setHue (Constants.ElectronicConstants.LED_COLORS ledColor) { 

        int ledIndex = 0;
        
        for (int stripIndex = 0; stripIndex < Constants.ElectronicConstants.LED_LENGTHS.size(); stripIndex++) {

            int ledLength = Constants.ElectronicConstants.LED_LENGTHS.get(stripIndex);
            int ledDirection = Constants.ElectronicConstants.LED_DIRECTIONS.get(stripIndex);

            switch (ledDirection) {

                case (-1):
                    for (int i = 0; i < ledLength; i++) { 
                    
                        int hue = ((ledColor.absoluteHue - ledColor.hueDeviation) + (i * (ledColor.hueDeviation) / ledLength)) % 180;
                        this.addressableLEDBuffer.setHSV(ledIndex, hue, 255, 128); 
                    }
                case (1):
                    for (int i = ledLength; i >= 0; i++) { 
                    
                        int hue = ((ledColor.absoluteHue - ledColor.hueDeviation) + (i * (ledColor.hueDeviation) / ledLength)) % 180;
                        this.addressableLEDBuffer.setHSV(ledIndex, hue, 255, 128); 
                    }
            }

            ledIndex++;
        }

        this.addressableLED.setData(this.addressableLEDBuffer);
    }
}
