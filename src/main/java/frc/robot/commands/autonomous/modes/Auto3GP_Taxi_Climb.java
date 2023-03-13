package frc.robot.commands.autonomous.modes;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.autonomous.drivetrain.DriveStraightDistance;
import frc.robot.commands.autonomous.drivetrain.TurnToRelativeAngle;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class Auto3GP_Taxi_Climb extends SequentialCommandGroup {

    public Auto3GP_Taxi_Climb (WestCoastDrive westCoastDrive, Manipulator manipulator, AHRS navX, LEDs leds) {
        
        this.addCommands(
            new WaitCommand(SmartDashboard.getNumber("Auto Start Wait", 0.0)),
            // Shoot Cube

            new DriveStraightDistance(westCoastDrive, -2.3, 0.5),
            // Intake Cube
            new DriveStraightDistance(westCoastDrive, 1.9, 0.5),
            // Shoot Cube
            
            new DriveStraightDistance(westCoastDrive, -2.3, 0.5),
            new TurnToRelativeAngle(westCoastDrive, 90, 0.35),
            new DriveStraightDistance(westCoastDrive, -2.0, 0.5),
            // Intake Cube

            new TurnToRelativeAngle(westCoastDrive, -90, 0.4),
            new DriveStraightDistance(westCoastDrive, 0.3, 0.35),
            new TurnToRelativeAngle(westCoastDrive, 180, 0.35),
            new BalanceChargingStation(westCoastDrive, navX)
            // Shoot Cube
        );
    }
}
