package frc.robot.commands.intaker;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.lib.PID;
import frc.robot.Constants;
import frc.robot.Constants.IntakerConstants.SLIDER_POSITIONS;
import frc.robot.subsystems.Intaker;

public class HoldSliderPosition extends CommandBase {
    
    private final Intaker intaker;

    private final PID sliderPID;
    private final int position;

    public HoldSliderPosition (Intaker intaker, int sliderPosition) {

        this.intaker = intaker;
        this.sliderPID = new PID(Constants.IntakerConstants.SLIDER_KP, Constants.IntakerConstants.SLIDER_KI, Constants.IntakerConstants.SLIDER_KD, 20000);
        this.position = sliderPosition;

        this.addRequirements(this.intaker);
    }

    public HoldSliderPosition (Intaker intaker, SLIDER_POSITIONS sliderPosition) {

        this.intaker = intaker;
        this.sliderPID = new PID(Constants.IntakerConstants.SLIDER_KP, Constants.IntakerConstants.SLIDER_KI, Constants.IntakerConstants.SLIDER_KD, 20000);
        this.position = sliderPosition.position;
    
        this.addRequirements(this.intaker);
    }

    @Override
    public void execute () {

        double error = this.position - this.intaker.getSliderPosition();
        double sliderPower = this.sliderPID.calculate(error);

        if (error > 20000) { sliderPower *= (20000 / error); }
        this.intaker.setSlider(sliderPower);
    }
}
