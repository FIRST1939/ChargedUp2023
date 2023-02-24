package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Intake;

public class ZeroSlider extends InstantCommand {
    
    private Intake intake;

    public ZeroSlider (Intake intake) {

        this.intake = intake;
        this.addRequirements(this.intake);
    }

    @Override
    public void initialize () { this.intake.zeroSlider(); }
}
