package frc.robot.commands.autonomous.modes;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.cubert.RunCubert;
import frc.robot.commands.manipulator.SetGamePiece;
import frc.robot.subsystems.Cubert;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class AutoLow_Balance extends SequentialCommandGroup {
    
    public AutoLow_Balance (WestCoastDrive westCoastDrive, Cubert cubert, Manipulator manipulator, AHRS navX, LEDs leds) {

        this.addCommands(
            new SetGamePiece(manipulator, leds, 1),
            new RunCubert(cubert, () -> -1.0, () -> -1.0).withTimeout(2.0),
            new BalanceChargingStation(westCoastDrive, navX)
        );
    }
}
