package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class LEDs extends SubsystemBase {
    
    private final AddressableLED addressableLED;
    private final AddressableLEDBuffer addressableLEDBuffer;

    private final Timer timer = new Timer();
    public Constants.ElectronicConstants.LED_COLORS ledColor = null;
    public Constants.ElectronicConstants.LED_MODES ledMode = null;

    private final Timer waveTimer = new Timer();
    private int animatedRainbowStartingHue = 90;
    private int animatedRainbowDirection = 1;

    public LEDs () {

        this.addressableLED = new AddressableLED(Constants.ElectronicConstants.LED_PWM);
        this.addressableLEDBuffer = new AddressableLEDBuffer(Constants.ElectronicConstants.LED_LENGTHS.stream().mapToInt(Integer::intValue).sum()); 

        this.addressableLED.setLength(this.addressableLEDBuffer.getLength());
        this.addressableLED.setData(this.addressableLEDBuffer);
        this.addressableLED.start();

        this.timer.start();
    }

    public void periodic () {

        if (this.ledMode == null) { return; }

        if (this.ledMode == Constants.ElectronicConstants.LED_MODES.RAINBOW) { 
            
            if (this.timer.get() >= 0.02) { 
                
                this.animateRainbow();
                this.timer.reset();
            } 
        } else if (this.ledMode == Constants.ElectronicConstants.LED_MODES.SPOTLIGHT) { 
            
            if (this.timer.get() >= 0.04) { 
                
                this.animateSpotlight(); 
                this.timer.reset();
            } 
        } else { 
            
            if (this.timer.get() >= 0.02) { 
                
                this.animateWaves(); 
                this.timer.reset();
            } 
        }
    }

    public void setColor (Constants.ElectronicConstants.LED_COLORS ledColor, boolean persist) { 

        int ledIndex = 0;
        
        for (int stripIndex = 0; stripIndex < Constants.ElectronicConstants.LED_LENGTHS.size(); stripIndex++) {

            int ledLength = Constants.ElectronicConstants.LED_LENGTHS.get(stripIndex);
            int ledDirection = Constants.ElectronicConstants.LED_DIRECTIONS.get(stripIndex);

            if (ledDirection == 1) {

                for (int i = 0; i < ledLength; i++) { 

                    int leadup = (i * (2 * ledColor.hueDeviation) / ledLength) % (2 * ledColor.hueDeviation);
                    int hue = (leadup + (ledColor.absoluteHue - ledColor.hueDeviation)) % 180;
                    this.addressableLEDBuffer.setHSV(ledIndex, hue, 255, 128); 

                    ledIndex++;
                }
            } else if (ledDirection == -1) {
            
                for (int i = ledLength; i > 0; i--) { 
                
                    int leadup =  (i * (2 * ledColor.hueDeviation) / ledLength) % (2 * ledColor.hueDeviation);
                    int hue = (leadup + (ledColor.absoluteHue - ledColor.hueDeviation)) % 180;
                    this.addressableLEDBuffer.setHSV(ledIndex, hue, 255, 128); 

                    ledIndex++;
                }
            }
        }

        this.addressableLED.setData(this.addressableLEDBuffer);
        if (persist) { this.ledColor = ledColor; }
        this.ledMode = null;
    }

    public void clear () {

        int ledIndex = 0;
        for (int stripIndex = 0; stripIndex < Constants.ElectronicConstants.LED_LENGTHS.size(); stripIndex++) {

            int ledLength = Constants.ElectronicConstants.LED_LENGTHS.get(stripIndex);
            for (int i = 0; i < ledLength; i++) { 
                
                this.addressableLEDBuffer.setHSV(ledIndex, 0, 0, 0); 
                ledIndex++;
            }
        }

        this.addressableLED.setData(this.addressableLEDBuffer);
    }

    public void dimAll () {

        for (int ledIndex = 0; ledIndex < Constants.ElectronicConstants.LED_LENGTHS.stream().mapToInt(Integer::intValue).sum(); ledIndex++) {

            Color color = this.addressableLEDBuffer.getLED(ledIndex);

            if (!color.toHexString().equals("#000000")) {

                this.addressableLEDBuffer.setRGB(ledIndex, (int) (color.red * 255.0 * 0.85), (int) (color.green * 255.0 * 0.85), (int) (color.blue * 255.0 * 0.85));
            }
        }
    
        this.addressableLED.setData(this.addressableLEDBuffer);
    }

    public void setMode (Constants.ElectronicConstants.LED_MODES ledMode) { 
        
        this.ledMode = ledMode; 
        this.animatedRainbowStartingHue = 90;

        this.waveTimer.reset();
        this.waveTimer.start();
        this.clear();
    }

    public void animateRainbow () {

        int ledIndex = 0;

        for (int stripIndex = 0; stripIndex < Constants.ElectronicConstants.LED_LENGTHS.size(); stripIndex++) {

            int ledLength = Constants.ElectronicConstants.LED_LENGTHS.get(stripIndex);
            int ledDirection = Constants.ElectronicConstants.LED_DIRECTIONS.get(stripIndex);

            if (ledDirection == 1) {

                for (int i = 0; i < ledLength; i++) { 

                    int hue = (this.animatedRainbowStartingHue + (i * 180 / ledLength)) % (180);
                    this.addressableLEDBuffer.setHSV(ledIndex, hue, 255, 255); 
                    ledIndex++;
                }
            } else if (ledDirection == -1) {
            
                for (int i = ledLength; i > 0; i--) { 
                
                    int hue = (this.animatedRainbowStartingHue + (i * 180 / ledLength)) % (180);
                    this.addressableLEDBuffer.setHSV(ledIndex, hue, 255, 255); 
                    ledIndex++;
                }
            }
        }

        this.addressableLED.setData(this.addressableLEDBuffer);

        if (this.animatedRainbowStartingHue >= 180 && this.animatedRainbowDirection == 1) { this.animatedRainbowDirection *= -1; }
        else if (this.animatedRainbowStartingHue <= 0 && this.animatedRainbowDirection == -1) { this.animatedRainbowDirection *= -1; }

        if (this.animatedRainbowDirection == 1) { this.animatedRainbowStartingHue += 2; }
        else { this.animatedRainbowStartingHue -= 2; }
    }

    public void animateSpotlight () {

        int spotlightLEDIndex = (int) (Math.random() * Constants.ElectronicConstants.LED_LENGTHS.stream().mapToInt(Integer::intValue).sum());
        int spotlightHue = (int) (Math.random() * 180.0);

        this.addressableLEDBuffer.setHSV(spotlightLEDIndex, spotlightHue, 255, 255);
        this.dimAll();
    }

    public void animateWaves () {

        int ledIndex = 0;

        for (int stripIndex = 0; stripIndex < Constants.ElectronicConstants.LED_LENGTHS.size(); stripIndex++) {

            int ledLength = Constants.ElectronicConstants.LED_LENGTHS.get(stripIndex);
            int beatPositionOne = this.beatsin8(60, ledIndex, ledIndex + ledLength - 1, this.waveTimer.get(), 0);
            int beatPositionTwo = this.beatsin8(120, ledIndex, ledIndex + ledLength - 1, this.waveTimer.get(), 0);
            int beatPositionThree = this.beatsin16(60, ledIndex, ledIndex + ledLength - 1, this.waveTimer.get(), 127);
            int beatPositionFour = this.beatsin16(120, ledIndex, ledIndex + ledLength - 1, this.waveTimer.get(), 127);
            int beat = beatsin8(90, 0, 255, this.waveTimer.get(), 0);

            int positionOne = (int) ((beatPositionOne + beatPositionTwo) / 2.0);
            int positionTwo = (int) ((beatPositionThree + beatPositionFour) / 2.0);

            this.addressableLEDBuffer.setHSV(positionOne, beat, 255, 255);
            this.addressableLEDBuffer.setHSV(positionTwo, beat, 255, 255);
            ledIndex += ledLength;
        }

        this.dimAll();
    }

    public int beatsin8 (int beatsPerMinute, int minimumValue, int maximumValue, double timebase, int phaseOffset) {

        double beatsPerSecond = beatsPerMinute / 60.0;
        double phase = ((timebase + phaseOffset) * beatsPerSecond * 2.0 * Math.PI) / 256.0;
        double amplitude = (maximumValue - minimumValue) / 2.0;
        double offset = minimumValue + amplitude;
        return (int) (Math.sin(phase) * amplitude + offset);
    }

    public int beatsin16 (int beatsPerMinute, int minimumValue, int maximumValue, double timebase, int phaseOffset) {

        double beatsPerSecond = beatsPerMinute / 60.0;
        double phase = ((timebase + phaseOffset) * beatsPerSecond * 2.0 * Math.PI) / 65536.0;
        double amplitude = (maximumValue - minimumValue) / 2.0;
        double offset = minimumValue + amplitude;
        return (int) (Math.sin(phase) * amplitude + offset);
    }
}
