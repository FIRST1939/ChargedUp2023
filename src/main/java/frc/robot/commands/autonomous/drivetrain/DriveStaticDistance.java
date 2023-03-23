package frc.robot.commands.autonomous.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.WestCoastDrive;

public class DriveStaticDistance extends CommandBase {
    
    private final WestCoastDrive westCoastDrive;
    private final double meters;
    private final double power;

    public DriveStaticDistance (WestCoastDrive westCoastDrive, double meters, double power) {

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
        //double turningValue = Math.signum(this.meters) * this.westCoastDrive.getHeading() * Constants.AutonomousConstants.GYRO_STRAIGHT_KP;
        this.westCoastDrive.drive(powerValue, 0.0);
    }

    @Override
    public boolean isFinished () { return Math.abs(this.westCoastDrive.getAverageDistance()) >= Math.abs(this.meters); }

    @Override
    public void end (boolean interrupted) { this.westCoastDrive.stop(); }
}
