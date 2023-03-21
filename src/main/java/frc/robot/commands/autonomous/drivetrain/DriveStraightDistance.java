package frc.robot.commands.autonomous.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.WestCoastDrive;

public class DriveStraightDistance extends CommandBase {
    
    final private WestCoastDrive westCoastDrive;
    final private double meters;

    final private double trapezoidalTime;
    final private double trapezoidalMeters;

    public DriveStraightDistance (WestCoastDrive westCoastDrive, double meters) {

        this.westCoastDrive = westCoastDrive;
        this.meters = meters;

        this.trapezoidalTime = Constants.AutonomousConstants.MAX_VELOCITY / Constants.AutonomousConstants.MAX_ACCELERATION;
        this.trapezoidalMeters = (Constants.AutonomousConstants.MAX_ACCELERATION * Math.pow(this.trapezoidalTime, 2)) / 2.0;

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
            if (Math.abs(powerValue) < 0.45) { powerValue = Math.signum(this.meters) * 0.45; }
        } else {

            double peakVelocity = Math.sqrt(2 * Constants.AutonomousConstants.MAX_ACCELERATION * (Math.abs(this.meters) / 2.0));
            powerValue = Math.sqrt(Math.pow(peakVelocity, 2) + (2 * -Constants.AutonomousConstants.MAX_ACCELERATION * ((Math.abs(this.meters) / 2.0) - (Math.abs(this.meters) - Math.abs(this.westCoastDrive.getAverageDistance())))));
            
            powerValue /= 3.81;
            if (Math.abs(powerValue) < 0.3) { powerValue = Math.signum(this.meters) * 0.3; }
        }

        powerValue = Math.signum(this.meters) * Math.abs(powerValue);
        if (Math.abs(powerValue) > 0.78) { powerValue = Math.signum(this.meters) * 0.78; }

        double turningValue = Math.signum(this.meters) * this.westCoastDrive.getHeading() * Constants.AutonomousConstants.GYRO_STRAIGHT_KP;
        this.westCoastDrive.drive(powerValue, 0.0);
    }

    @Override
    public boolean isFinished () { return Math.abs(this.westCoastDrive.getAverageDistance()) >= Math.abs(this.meters); }

    @Override
    public void end (boolean interrupted) { this.westCoastDrive.stop(); }
}
