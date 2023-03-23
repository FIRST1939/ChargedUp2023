package frc.robot.commands.autonomous.modes;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.SetShot;
import frc.robot.commands.autonomous.drivetrain.DriveRampedDistance;
import frc.robot.commands.autonomous.drivetrain.TurnToRelativeAngle;
import frc.robot.commands.autonomous.drivetrain.WaitDistance;
import frc.robot.commands.cubert.RunCubert;
import frc.robot.commands.manipulator.SetGamePiece;
import frc.robot.subsystems.Cubert;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class Auto2GP_Balance extends SequentialCommandGroup{
    
    public Auto2GP_Balance (WestCoastDrive westCoastDrive, AHRS navX, Cubert cubert, Manipulator manipulator, LEDs leds) {

        this.addCommands(
            new Auto1GP(westCoastDrive, manipulator, leds),
            new SetGamePiece(manipulator, leds, 1),

            new ParallelCommandGroup(
                new DriveRampedDistance(westCoastDrive, -4.3688).andThen(new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Blue ? 1.0 : -1.0) * 8.0, 0.55)),
                new RunCubert(cubert, () -> 0.8, () -> 0.8).withTimeout(0.8).beforeStarting(new WaitDistance(westCoastDrive, -3.3688))
            ),

            new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Blue ? 1.0 : -1.0) * -58.0, 0.55),
            new DriveRampedDistance(westCoastDrive, 2.1),
            new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Blue ? 1.0 : -1.0) * 50.0, 0.55),

            new ParallelCommandGroup(
                new BalanceChargingStation(westCoastDrive, navX),

                new SequentialCommandGroup(
                    new SetShot(cubert, Constants.CubertConstants.SHOTS.UP),
                    new RunCubert(cubert, () -> 0.0, () -> 0.8).withTimeout(0.8),
                    new SetShot(cubert, Constants.CubertConstants.SHOTS.LEFT)
                )
            )
        );
    }
}
