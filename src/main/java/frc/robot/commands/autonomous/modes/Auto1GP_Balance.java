package frc.robot.commands.autonomous.modes;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.autonomous.BalanceChargingStation;
import frc.robot.commands.manipulator.RunManipulator;
import frc.robot.commands.manipulator.SetGamePiece;
import frc.robot.commands.manipulator.HoldArmPosition;
import frc.robot.commands.manipulator.ResetArmPosition;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class Auto1GP_Balance extends SequentialCommandGroup {
    
    public Auto1GP_Balance (WestCoastDrive westCoastDrive, Manipulator manipulator, AHRS navX, LEDs leds) {

        this.addCommands(
            new WaitCommand(SmartDashboard.getNumber("Auto Start Wait", 0.0)),
            new SetGamePiece(manipulator, leds, -1),
            new HoldArmPosition(manipulator, 66000).withTimeout(3.0),
            new RunManipulator(manipulator, () -> 0.8).withTimeout(0.8),
            new ResetArmPosition(manipulator, 0.75).withTimeout(3.0),
            new SetGamePiece(manipulator, leds, 0),
            new BalanceChargingStation(westCoastDrive, navX)
        );
    }
}
