package frc.robot.commands.autonomous.modes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.autonomous.drivetrain.DriveStraightDistance;
import frc.robot.commands.manipulator.SetGamePiece;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class Auto1GP_Taxi_Intake extends SequentialCommandGroup {

    public Auto1GP_Taxi_Intake (WestCoastDrive westCoastDrive, Manipulator manipulator, LEDs leds) {

        this.addCommands(
            new Auto1GP(westCoastDrive, manipulator, leds),

            // Backup and Intake Cube.
            new DriveStraightDistance(westCoastDrive, -3.8, 0.55),
            new SetGamePiece(manipulator, leds, -1)
        );
    }
}
