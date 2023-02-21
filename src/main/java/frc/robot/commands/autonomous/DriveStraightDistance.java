package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.WestCoastDrive;

public class DriveStraightDistance extends CommandBase {
    
    final private WestCoastDrive westCoastDrive;
    final private double meters;
    final private double power;

    public DriveStraightDistance (WestCoastDrive westCoastDrive, double meters, double power) {

        this.westCoastDrive = westCoastDrive;
        this.meters = meters;
        this.power = power;

        this.addRequirements(this.westCoastDrive);
    }

    @Override
    public void initialize () {

        this.westCoastDrive.resetDistance();
        this.westCoastDrive.resetHeading();
    }

    @Override
    public void execute () {

        double powerValue = Math.signum(this.meters) * this.power;
        double turningValue = this.westCoastDrive.getHeading() * Constants.AutonomousConstants.GYRO_STRAIGHT_KP;
        this.westCoastDrive.drive(powerValue, turningValue);
    }

    @Override
    public boolean isFinished () { return this.westCoastDrive.getAverageDistance() >= Math.abs(this.meters); }

    @Override
    public void end (boolean interrupted) { this.westCoastDrive.stop(); }
}
