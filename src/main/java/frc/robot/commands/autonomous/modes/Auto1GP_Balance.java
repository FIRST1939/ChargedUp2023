package frc.robot.commands.autonomous.modes;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.autonomous.drivetrain.DriveStraightDistance;
import frc.robot.commands.autonomous.drivetrain.TurnToRelativeAngle;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class Auto1GP_Balance extends SequentialCommandGroup {
    
    public Auto1GP_Balance (WestCoastDrive westCoastDrive, Manipulator manipulator, AHRS navX, LEDs leds) {

        this.addCommands(
            new Auto1GP(westCoastDrive, manipulator, leds),

            // Balance Charging Station
            new DriveStraightDistance(westCoastDrive, 0.3),
            new TurnToRelativeAngle(westCoastDrive, 180, 0.35),
            new BalanceChargingStation(westCoastDrive, navX)
        );
    }
}
