package frc.robot.commands.intaker;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intaker;

public class ZeroSlider extends InstantCommand {
    
    private Intaker intaker;

    public ZeroSlider (Intaker intaker) {

        this.intaker = intaker;
        this.addRequirements(this.intaker);
    }

    @Override
    public void initialize () { this.intaker.zeroSlider(); }
}
