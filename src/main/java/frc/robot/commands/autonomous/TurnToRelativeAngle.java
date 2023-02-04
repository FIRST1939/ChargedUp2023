package frc.robot.commands.autonomous;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants;
import frc.robot.subsystems.WestCoastDrive;

public class TurnToRelativeAngle extends PIDCommand {
    
    private final WestCoastDrive westCoastDrive;

    /*
     * @param relativeAngle: The relative angle where turning clockwise moves the angle negatively.
     */
    public TurnToRelativeAngle (WestCoastDrive westCoastDrive, double relativeAngle) {

        super(
            new PIDController(Constants.AutonomousConstants.GYRO_TURNING_KP, 0.0, 0.0),
            westCoastDrive::getHeading,
            relativeAngle,
            output -> useOutput(westCoastDrive, output),
            westCoastDrive
        );

        this.westCoastDrive = westCoastDrive;
        this.getController().enableContinuousInput(-180.0, 180.0);
        this.getController().setTolerance(Constants.AutonomousConstants.TURNING_ANGLE_TOLERANCE, Constants.AutonomousConstants.TURNUNG_ANGLE_TURN_RATE_TOLERANCE);
    }

    @Override
    public void initialize () {

        super.isScheduled();
        this.westCoastDrive.resetDistance();
        this.westCoastDrive.resetHeading();
    }

    private static void useOutput (final WestCoastDrive westCoastDrive, final double pidControllerOutput) {

        double output = pidControllerOutput;
        if (pidControllerOutput >= 0) { output += Constants.AutonomousConstants.TURNING_ANGLE_KF; }
        else { output -= Constants.AutonomousConstants.TURNING_ANGLE_KF; }

        westCoastDrive.drive(0.0, output);
      }

    @Override
    public boolean isFinished () { return getController().atSetpoint(); }
}
