package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Photonvision;
import frc.robot.subsystems.WestCoastDrive;

public class DriveAprilTagDistance extends CommandBase {
    
    final private WestCoastDrive westCoastDrive;
    final private Photonvision photonvision;
    private double meters;
    private double power;

    public DriveAprilTagDistance (WestCoastDrive westCoastDrive, Photonvision photonvision, double meters, double power) {

        this.westCoastDrive = westCoastDrive;
        this.photonvision = photonvision;
        this.meters = meters;
        this.power = power;
        
        this.addRequirements(this.westCoastDrive, this.photonvision);
    }

    @Override
    public void initialize () { 
        
        this.power *= Math.signum(-this.photonvision.getXDistance() - this.meters);
        this.westCoastDrive.resetHeading();
    }

    @Override
    public void execute () {

        double turningValue = -Math.signum(this.meters) * this.westCoastDrive.getHeading() * Constants.AutonomousConstants.GYRO_STRAIGHT_KP;
        this.westCoastDrive.drive(this.power, 0.0);
    }

    @Override
    public boolean isFinished () { return Math.signum(this.power) * (-this.photonvision.getXDistance() - this.meters) <= 0; }

    @Override
    public void end (boolean interrupted) { this.westCoastDrive.stop(); }
}
