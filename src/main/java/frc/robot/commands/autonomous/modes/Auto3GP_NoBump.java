package frc.robot.commands.autonomous.modes;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.SetShot;
import frc.robot.commands.autonomous.drivetrain.DriveRampedDistance;
import frc.robot.commands.autonomous.drivetrain.TurnToRelativeAngle;
import frc.robot.commands.autonomous.drivetrain.WaitDistance;
import frc.robot.commands.cubert.FeedToBeamBreak;
import frc.robot.commands.cubert.FeedToShooter;
import frc.robot.commands.manipulator.SetGamePiece;
import frc.robot.subsystems.Cubert;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class Auto3GP_NoBump extends SequentialCommandGroup {
    
    public Auto3GP_NoBump (WestCoastDrive westCoastDrive, Cubert cubert, Manipulator manipulator, LEDs leds) {

        this.addCommands(
            new Auto1GP(westCoastDrive, manipulator, leds),
            new SetGamePiece(manipulator, leds, 1),
            new SetShot(cubert, Constants.CubertConstants.SHOTS.UP),

            new ParallelDeadlineGroup(
                new DriveRampedDistance(westCoastDrive, -4.3688).andThen(new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Red ? 1.0 : -1.0) * 8.0)),
                new FeedToBeamBreak(cubert, () -> 0.8, () -> 0.8)
            ),

            new FeedToBeamBreak(cubert, () -> 0.8, () -> 0.8).withTimeout(0.5),

            new ParallelDeadlineGroup(
                new DriveRampedDistance(westCoastDrive, 3.68).beforeStarting(new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Red ? 1.0 : -1.0) * -8.0)),
                new FeedToBeamBreak(cubert, () -> 0.0, () -> 0.8)
            ),

            new FeedToShooter(cubert, () -> 0.35, () -> 1.0).withTimeout(2.0),
            new DriveRampedDistance(westCoastDrive, -2.0),
            new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Red ? 1.0 : -1.0) * 29.38),

            new ParallelDeadlineGroup(
                new DriveRampedDistance(westCoastDrive, -2.07),
                new FeedToBeamBreak(cubert, () -> 0.8, () -> 0.8).beforeStarting(new WaitDistance(westCoastDrive, -0.5))
            ),

            new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    new DriveRampedDistance(westCoastDrive, 2.07),
                    new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Red ? 1.0 : -1.0) * -29.38),
                    new DriveRampedDistance(westCoastDrive, 2.0)
                ),

                new FeedToBeamBreak(cubert, () -> 0.0, () -> 0.8)
            ),

            new FeedToShooter(cubert, () -> 0.35, () -> 1.0).withTimeout(2.0),
            new SetShot(cubert, Constants.CubertConstants.SHOTS.LEFT)
        );
    }
}
