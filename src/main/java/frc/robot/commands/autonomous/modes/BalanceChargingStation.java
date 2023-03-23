package frc.robot.commands.autonomous.modes;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.autonomous.charging_station.ContactChargingStation;
import frc.robot.commands.autonomous.charging_station.MaintainChargingStation;
import frc.robot.commands.autonomous.drivetrain.DriveStaticDistance;
import frc.robot.subsystems.WestCoastDrive;

public class BalanceChargingStation extends SequentialCommandGroup {
    
    public BalanceChargingStation (WestCoastDrive westCoastDrive, AHRS navX) {

        this.addCommands(
            new ContactChargingStation(westCoastDrive, navX),
            new DriveStaticDistance(westCoastDrive, 0.955, 0.35),
            new MaintainChargingStation(westCoastDrive, navX)
        );
    }
}
