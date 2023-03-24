package frc.robot.commands.autonomous.modes;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.autonomous.drivetrain.DriveRampedDistance;
import frc.robot.commands.autonomous.drivetrain.TurnToRelativeAngle;
import frc.robot.commands.autonomous.drivetrain.WaitDistance;
import frc.robot.commands.cubert.RunCubert;
import frc.robot.commands.cubert.SetShot;
import frc.robot.commands.manipulator.SetGamePiece;
import frc.robot.subsystems.Cubert;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class Auto3GP_Short extends SequentialCommandGroup {
    
    public Auto3GP_Short (WestCoastDrive westCoastDrive, Cubert cubert, Manipulator manipulator, LEDs leds) {

        this.addCommands(
            new Auto1GP(westCoastDrive, manipulator, leds),
            new SetGamePiece(manipulator, leds, 1),

            new ParallelCommandGroup(
                new DriveRampedDistance(westCoastDrive, -4.3688).andThen(new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Blue ? 1.0 : -1.0) * 8.0, 0.55)),
                new RunCubert(cubert, leds, () -> 0.8, () -> 0.8).withTimeout(0.8).beforeStarting(new WaitDistance(westCoastDrive, -3.3688))
            ),

            new DriveRampedDistance(westCoastDrive, 3.68).beforeStarting(new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Blue ? 1.0 : -1.0) * -8.0, 0.55)),

            new SetShot(cubert, Constants.CubertConstants.SHOTS.UP, leds),
            new RunCubert(cubert, leds, () -> 0.0, () -> 0.8).withTimeout(0.8),
            new SetShot(cubert, Constants.CubertConstants.SHOTS.LEFT, leds),

            new DriveRampedDistance(westCoastDrive, -2.0),
            new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Blue ? 1.0 : -1.0) * 29.38, 0.55),

            new ParallelCommandGroup(
                new DriveRampedDistance(westCoastDrive, -2.07),
                new RunCubert(cubert, leds, () -> 0.8, () -> 0.8).withTimeout(0.8).beforeStarting(new WaitDistance(westCoastDrive, -1.07))
            ),

            new DriveRampedDistance(westCoastDrive, 2.07),
            new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Blue ? 1.0 : -1.0) * -29.38, 0.55),
            new DriveRampedDistance(westCoastDrive, 2.0),

            new SetShot(cubert, Constants.CubertConstants.SHOTS.UP, leds),
            new RunCubert(cubert, leds, () -> 0.0, () -> 0.8).withTimeout(0.8),
            new SetShot(cubert, Constants.CubertConstants.SHOTS.LEFT, leds)
        );
    }
}
