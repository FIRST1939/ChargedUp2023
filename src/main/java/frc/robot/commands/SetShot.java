package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.CubertConstants.SHOTS;
import frc.robot.subsystems.Cubert;

public class SetShot extends CommandBase {
    
    private final Cubert cubert;
    private final int velocity;

    public SetShot (Cubert cubert, SHOTS shot) {

        this.cubert = cubert;
        this.velocity = shot.velocity;

        this.addRequirements(this.cubert);
    }

    @Override
    public void execute () { this.cubert.setShooter(this.velocity); }

    @Override
    public boolean isFinished () { return false; }

    @Override
    public void end (boolean interrupted) { this.cubert.setShooter(0.0); }
}
