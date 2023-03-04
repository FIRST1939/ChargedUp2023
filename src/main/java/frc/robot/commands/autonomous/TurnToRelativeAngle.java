package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WestCoastDrive;

public class TurnToRelativeAngle extends CommandBase {
    
    private final WestCoastDrive westCoastDrive;
    private final double relativeAngle;
    private final double power;

    /*
     * @param relativeAngle: The relative angle where turning clockwise moves the angle negatively.
     */
    public TurnToRelativeAngle (WestCoastDrive westCoastDrive, double relativeAngle, double power) {

        this.westCoastDrive = westCoastDrive;
        this.relativeAngle = relativeAngle;
        this.power = power;

        this.addRequirements(this.westCoastDrive);
    }

    @Override
    public void initialize () { this.westCoastDrive.resetHeading(); }

    @Override
    public void execute () { this.westCoastDrive.drive(0.0, -this.power * Math.signum(this.relativeAngle)); }

    @Override
    public boolean isFinished () { return Math.abs(this.relativeAngle) >= this.westCoastDrive.getHeading(); }

    @Override
    public void end (boolean interrupted) { this.westCoastDrive.stop(); }
}
