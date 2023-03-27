package frc.robot.commands.autonomous.drivetrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.WestCoastDrive;

public class DriveRampedDistance extends CommandBase {
    
    final private WestCoastDrive westCoastDrive;
    final private double meters;

    public DriveRampedDistance (WestCoastDrive westCoastDrive, double meters) {

        this.westCoastDrive = westCoastDrive;
        this.meters = meters;

        this.addRequirements(this.westCoastDrive);
    }

    @Override
    public void initialize () {

        this.westCoastDrive.resetDistance();
        this.westCoastDrive.resetHeading();
    }

    @Override
    public void execute () {

        double powerValue;

        if (Math.abs(this.westCoastDrive.getAverageDistance()) <= Math.abs(this.meters) / 2.0) {

            powerValue = Math.sqrt(2 * Constants.AutonomousConstants.MAX_ACCELERATION * Math.abs(this.westCoastDrive.getAverageDistance()));
            
            powerValue /= 3.81;
            if (Math.abs(powerValue) < 0.7) { powerValue = Math.signum(this.meters) * 0.7; }
        } else {

            double peakVelocity = Math.sqrt(2 * Constants.AutonomousConstants.MAX_ACCELERATION * (Math.abs(this.meters) / 2.0));
            powerValue = Math.sqrt(Math.pow(peakVelocity, 2) + (2 * -Constants.AutonomousConstants.MAX_ACCELERATION * ((Math.abs(this.meters) / 2.0) - (Math.abs(this.meters) - Math.abs(this.westCoastDrive.getAverageDistance())))));
            
            powerValue /= 3.81;
            if (Math.abs(powerValue) < 0.5) { powerValue = Math.signum(this.meters) * 0.5; }
        }

        powerValue = Math.signum(this.meters) * Math.abs(powerValue);
        if (DriverStation.getMatchTime() <= 1.0) { powerValue *= 0.75; }

        //double turningValue = Math.signum(this.meters) * this.westCoastDrive.getHeading() * Constants.AutonomousConstants.GYRO_STRAIGHT_KP;
        this.westCoastDrive.drive(powerValue, 0.0);
    }

    @Override
    public boolean isFinished () { return Math.abs(this.westCoastDrive.getAverageDistance()) >= Math.abs(this.meters); }

    @Override
    public void end (boolean interrupted) { this.westCoastDrive.stop(); }
}
