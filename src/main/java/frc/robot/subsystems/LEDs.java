package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDs extends SubsystemBase {
    
    private final AddressableLED addressableLED;
    private final AddressableLEDBuffer addressableLEDBuffer;

    public LEDs (AddressableLED addressableLED, AddressableLEDBuffer addressableLEDBuffer) {

        this.addressableLED = addressableLED;
        this.addressableLEDBuffer = addressableLEDBuffer;
    }

    public void initialize () {

        this.addressableLED.setLength(this.addressableLEDBuffer.getLength());
        this.addressableLED.setData(this.addressableLEDBuffer);
        this.addressableLED.start();

        this.setStrip(3, 170, 80);
    }

    public void setLED (int index, int red, int green, int blue) { this.addressableLEDBuffer.setRGB(index, red, green, blue); }

    public void setStrip (int red, int green, int blue) {

        for (int i = 0; i < this.addressableLEDBuffer.getLength(); i++) { this.addressableLEDBuffer.setRGB(i, red, green, blue); }
        this.addressableLED.setData(this.addressableLEDBuffer);
    }
}
