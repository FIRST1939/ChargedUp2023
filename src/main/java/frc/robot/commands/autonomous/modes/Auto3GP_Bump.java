package frc.robot.commands.autonomous.modes;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.autonomous.drivetrain.DriveRampedDistance;
import frc.robot.commands.autonomous.drivetrain.TurnToRelativeAngle;
import frc.robot.commands.autonomous.drivetrain.WaitDistance;
import frc.robot.commands.cubert.FeedToBeamBreak;
import frc.robot.commands.cubert.FeedToShooter;
import frc.robot.commands.cubert.SetShot;
import frc.robot.commands.manipulator.SetGamePiece;
import frc.robot.subsystems.Cubert;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class Auto3GP_Bump extends SequentialCommandGroup {
    
    public Auto3GP_Bump (WestCoastDrive westCoastDrive, Cubert cubert, Manipulator manipulator, LEDs leds) {

        this.addCommands(
            new SetGamePiece(manipulator, leds, 1),
            new SetShot(cubert, leds, Constants.CubertConstants.SHOTS.UP),
            new FeedToShooter(cubert, leds, () -> 0.35, () -> 1.0),

            new ParallelDeadlineGroup(
                new DriveRampedDistance(westCoastDrive, -2.318),
                new FeedToBeamBreak(cubert, () -> 0.8, () -> 0.8)
            ),
            
            new ParallelDeadlineGroup(
                new WaitCommand(0.5),
                new FeedToBeamBreak(cubert, () -> 0.8, () -> 0.8)
            ),

            new ParallelDeadlineGroup(
                new DriveRampedDistance(westCoastDrive, 1.6),
                new FeedToBeamBreak(cubert, () -> 0.0, () -> 0.8)
            ),

            new FeedToShooter(cubert, leds, () -> 0.35, () -> 1.0).withTimeout(3.0),
            new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Red ? 1.0 : -1.0) * -29.38),

            new ParallelDeadlineGroup(
                new DriveRampedDistance(westCoastDrive, -2.07),
                new FeedToBeamBreak(cubert, () -> 0.8, () -> 0.8).beforeStarting(new WaitDistance(westCoastDrive, -0.5))
            ),

            new ParallelDeadlineGroup(
                new WaitCommand(0.5),
                new FeedToBeamBreak(cubert, () -> 0.8, () -> 0.8)
            ),

            new ParallelDeadlineGroup(
                new SequentialCommandGroup(
                    new DriveRampedDistance(westCoastDrive, 2.07),
                    new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Red ? 1.0 : -1.0) * 54.38)
                ),

                new FeedToBeamBreak(cubert, () -> 0.0, () -> 0.8)
            ),

            new FeedToShooter(cubert, leds, () -> 0.35, () -> 3.0),
            new SetShot(cubert, leds, Constants.CubertConstants.SHOTS.LEFT),

            new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Red ? 1.0 : -1.0) * -54.38),

            new DriveRampedDistance(westCoastDrive, -2.07),
            
            new TurnToRelativeAngle(westCoastDrive, (DriverStation.getAlliance() == DriverStation.Alliance.Red ? 1.0 : -1.0) * -60.62)
        );
    }
}
