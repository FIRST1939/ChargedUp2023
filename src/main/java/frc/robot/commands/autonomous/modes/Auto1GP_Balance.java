package frc.robot.commands.autonomous.modes;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.autonomous.drivetrain.DriveRampedDistance;
import frc.robot.commands.autonomous.drivetrain.TurnToRelativeAngle;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class Auto1GP_Balance extends SequentialCommandGroup {
    
    public Auto1GP_Balance (WestCoastDrive westCoastDrive, Manipulator manipulator, AHRS navX, LEDs leds) {

        this.addCommands(
            new Auto1GP(westCoastDrive, manipulator, leds),

            // Balance Charging Station
            new DriveRampedDistance(westCoastDrive, -0.3),
            new TurnToRelativeAngle(westCoastDrive, 150),
            new TurnToRelativeAngle(westCoastDrive, 30),
            new BalanceChargingStation(westCoastDrive, navX)
            //TestTest
        );
    }
}
