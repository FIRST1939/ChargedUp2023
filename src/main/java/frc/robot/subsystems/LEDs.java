package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDs extends SubsystemBase {
    
    private final AddressableLED addressableLED;
    private final AddressableLEDBuffer addressableLEDBuffer;

    private int firstPixelHue = 240;

    public LEDs (AddressableLED addressableLED, AddressableLEDBuffer addressableLEDBuffer) {

        this.addressableLED = addressableLED;
        this.addressableLEDBuffer = addressableLEDBuffer;

        this.addressableLED.setLength(this.addressableLEDBuffer.getLength());
        this.addressableLED.setData(this.addressableLEDBuffer);
        this.addressableLED.start();
    }

    public void setLED (int index, int red, int green, int blue) { this.addressableLEDBuffer.setRGB(index, red, green, blue); }

    public void setStrip (int red, int green, int blue) {

        for (int i = 0; i < this.addressableLEDBuffer.getLength(); i++) { this.setLED(i, red, green, blue); }
        this.addressableLED.setData(this.addressableLEDBuffer);
    }

    public void rainbow () {

        for (int i = 0; i < this.addressableLEDBuffer.getLength(); i++) {

            int hue = (this.firstPixelHue + (i * 180 / this.addressableLEDBuffer.getLength())) % 180;
            this.addressableLEDBuffer.setHSV(i, hue, 255, 128);
        }

        this.firstPixelHue += 3;
        this.firstPixelHue %= 180;

        this.addressableLED.setData(this.addressableLEDBuffer);
    }
}
