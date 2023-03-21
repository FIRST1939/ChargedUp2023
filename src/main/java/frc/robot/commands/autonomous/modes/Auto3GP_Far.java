package frc.robot.commands.autonomous.modes;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.SetShot;
import frc.robot.commands.autonomous.drivetrain.DriveStraightDistance;
import frc.robot.commands.autonomous.drivetrain.TurnToRelativeAngle;
import frc.robot.commands.cubert.RunCubert;
import frc.robot.subsystems.Cubert;
import frc.robot.subsystems.WestCoastDrive;

public class Auto3GP_Far extends SequentialCommandGroup {
    
    public Auto3GP_Far (WestCoastDrive westCoastDrive, Cubert cubert) {

        this.addCommands(
            new SetShot(cubert, Constants.CubertConstants.SHOTS.UP),
            new RunCubert(cubert, () -> 0.0, () -> 1.0).withTimeout(0.6),
            new SetShot(cubert, Constants.CubertConstants.SHOTS.LEFT),

            new ParallelCommandGroup(
                new DriveStraightDistance(westCoastDrive, -2.16),
                new RunCubert(cubert, () -> 1.0, () -> 0.0).withTimeout(2.8)
            ),

            new DriveStraightDistance(westCoastDrive, 1.68),

            // Shoot Cube

            new SetShot(cubert, Constants.CubertConstants.SHOTS.UP),
            new RunCubert(cubert, () -> 0.0, () -> 1.0).withTimeout(0.8),
            new SetShot(cubert, Constants.CubertConstants.SHOTS.LEFT),

            new TurnToRelativeAngle(westCoastDrive, -29.38, 0.45),

            new ParallelCommandGroup(
                new DriveStraightDistance(westCoastDrive, -2.07),
                new RunCubert(cubert, () -> 1.0, () -> 0.0).withTimeout(3.0)
            ),

            new TurnToRelativeAngle(westCoastDrive, 29.38, 0.45),
            new DriveStraightDistance(westCoastDrive, 0.997),

            new SetShot(cubert, Constants.CubertConstants.SHOTS.UP),
            new RunCubert(cubert, () -> 0.0, () -> 1.0).withTimeout(2.0),
            new SetShot(cubert, Constants.CubertConstants.SHOTS.LEFT)
        );
    }
}
