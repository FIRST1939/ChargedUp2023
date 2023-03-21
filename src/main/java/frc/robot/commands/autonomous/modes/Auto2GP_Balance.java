package frc.robot.commands.autonomous.modes;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.SetShot;
import frc.robot.commands.autonomous.drivetrain.DriveStraightDistance;
import frc.robot.commands.autonomous.drivetrain.TurnToRelativeAngle;
import frc.robot.commands.cubert.RunCubert;
import frc.robot.subsystems.Cubert;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class Auto2GP_Balance extends SequentialCommandGroup{
    
    public Auto2GP_Balance (WestCoastDrive westCoastDrive, Cubert cubert, Manipulator manipulator, LEDs leds) {

        this.addCommands(
            new Auto1GP(westCoastDrive, manipulator, leds),

            new ParallelCommandGroup(
                new DriveStraightDistance(westCoastDrive, -4.3688).andThen(new TurnToRelativeAngle(westCoastDrive, 8.0, 0.45)),
                new RunCubert(cubert, () -> 1.0, () -> 0.0).withTimeout(5.0)
            ),

            new TurnToRelativeAngle(westCoastDrive, -53.0, 0.45),
            new DriveStraightDistance(westCoastDrive, 2.1),
            new TurnToRelativeAngle(westCoastDrive, 45.0, 0.45),

            new SetShot(cubert, Constants.CubertConstants.SHOTS.UP
            ),
            new RunCubert(cubert, () -> 0.0, () -> 1.0).withTimeout(0.8),
            new SetShot(cubert, Constants.CubertConstants.SHOTS.LEFT)
        );
    }
}