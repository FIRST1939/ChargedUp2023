package frc.robot.commands.autonomous.modes;

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

public class Auto3GP_Far extends SequentialCommandGroup {
    
    public Auto3GP_Far (WestCoastDrive westCoastDrive, Cubert cubert, Manipulator manipulator, LEDs leds) {

        this.addCommands(
            new SetGamePiece(manipulator, leds, 1),

            new SetShot(cubert, Constants.CubertConstants.SHOTS.UP),
            new RunCubert(cubert, () -> 0.0, () -> 0.8).withTimeout(0.6),
            new SetShot(cubert, Constants.CubertConstants.SHOTS.LEFT),

            new ParallelCommandGroup(
                new DriveRampedDistance(westCoastDrive, -2.16),
                new RunCubert(cubert, () -> 0.8, () -> 0.8).withTimeout(0.8).beforeStarting(new WaitDistance(westCoastDrive, -1.16))
            ),

            new DriveRampedDistance(westCoastDrive, 1.68),

            new SetShot(cubert, Constants.CubertConstants.SHOTS.UP),
            new RunCubert(cubert, () -> 0.0, () -> 0.8).withTimeout(0.6),
            new SetShot(cubert, Constants.CubertConstants.SHOTS.LEFT),

            new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Blue ? 1.0 : -1.0) * -29.38, 0.55),

            new ParallelCommandGroup(
                new DriveRampedDistance(westCoastDrive, -2.07),
                new RunCubert(cubert, () -> 0.8, () -> 0.8).withTimeout(0.8).beforeStarting(new WaitDistance(westCoastDrive, -1.07))
            ),

            new DriveRampedDistance(westCoastDrive, 2.07),
            new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Blue ? 1.0 : -1.0) * 44.38, 0.55),

            new SetShot(cubert, Constants.CubertConstants.SHOTS.UP),
            new RunCubert(cubert, () -> 0.0, () -> 0.8),
            new SetShot(cubert, Constants.CubertConstants.SHOTS.LEFT)
        );
    }
}
