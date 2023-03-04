package frc.robot.commands.autonomous.modes;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.autonomous.DriveStraightDistance;
import frc.robot.commands.autonomous.MaintainChargingStation;
import frc.robot.commands.autonomous.ContactChargingStation;
import frc.robot.subsystems.WestCoastDrive;

public class BalanceChargingStation extends SequentialCommandGroup {
    
    public BalanceChargingStation (WestCoastDrive westCoastDrive, AHRS navX) {

        this.addCommands(
            new WaitCommand(SmartDashboard.getNumber("Auto Start Wait", 0.0)),
            new ContactChargingStation(westCoastDrive, navX),
            new DriveStraightDistance(westCoastDrive, 0.955, 0.35),
            new MaintainChargingStation(westCoastDrive, navX)
        );
    }
}
