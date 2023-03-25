package frc.robot.commands.autonomous.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WestCoastDrive;

public class TurnToRelativeAngle extends CommandBase {
    
    private final WestCoastDrive westCoastDrive;
    private final double relativeAngle;

    /*
     * @param relativeAngle: The relative angle where turning clockwise moves the angle negatively.
     */
    public TurnToRelativeAngle (WestCoastDrive westCoastDrive, double relativeAngle) {

        this.westCoastDrive = westCoastDrive;
        this.relativeAngle = relativeAngle;

        this.addRequirements(this.westCoastDrive);
    }

    @Override
    public void initialize () { this.westCoastDrive.resetHeading(); }

    @Override
    public void execute () { 
        
        System.out.println(this.westCoastDrive.getHeading());
        double distance = (Math.abs(this.relativeAngle) - Math.abs(this.westCoastDrive.getHeading()));
        double powerValue;

        if (distance >= 60) { powerValue = 0.85; }
        else if (distance >= 45) { powerValue = 0.75; }
        else if (distance >= 15) { powerValue = 0.6; }
        else if (distance >= 10) { powerValue = 0.45; }
        else { powerValue = 0.4; }

        powerValue *= Math.signum(this.relativeAngle);
        this.westCoastDrive.drive(0.0, powerValue); 
    }

    @Override
    public boolean isFinished () { return Math.abs(this.westCoastDrive.getHeading()) >= Math.abs(this.relativeAngle); }

    @Override
    public void end (boolean interrupted) { this.westCoastDrive.stop(); }
}
