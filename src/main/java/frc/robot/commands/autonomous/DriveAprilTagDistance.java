package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Photonvision;
import frc.robot.subsystems.WestCoastDrive;

public class DriveAprilTagDistance extends CommandBase {
    
    final private WestCoastDrive westCoastDrive;
    final private Photonvision photonvision;
    final private double meters;
    final private double power;

    public DriveAprilTagDistance (WestCoastDrive westCoastDrive, Photonvision photonvision, double meters, double power) {

        this.westCoastDrive = westCoastDrive;
        this.photonvision = photonvision;
        this.meters = meters;
        this.power = power;
        
        this.addRequirements(this.westCoastDrive, this.photonvision);
    }

    @Override
    public void initialize () { this.westCoastDrive.resetHeading(); }

    @Override
    public void execute () {

        double turningValue = this.westCoastDrive.getHeading() * Constants.AutonomousConstants.GYRO_STRAIGHT_KP;
        this.westCoastDrive.drive(this.power, turningValue);
    }

    @Override
    public boolean isFinished () { return -this.photonvision.getBestPosition().getX() <= this.meters; }

    @Override
    public void end (boolean interrupted) { this.westCoastDrive.stop(); }
}
