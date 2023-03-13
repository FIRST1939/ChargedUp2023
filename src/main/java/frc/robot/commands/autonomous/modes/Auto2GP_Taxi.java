package frc.robot.commands.autonomous.modes;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.autonomous.drivetrain.DriveStraightDistance;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

/**
 * Scores One High Cone and One High Cube.
 * Starts From Both Red and Blue Side Near the Loading Station Wall
 */
public class Auto2GP_Taxi extends SequentialCommandGroup {

    public Auto2GP_Taxi (WestCoastDrive westCoastDrive, Manipulator manipulator, AHRS navX, LEDs leds) {

        this.addCommands(
            new Auto1GP(westCoastDrive, manipulator, leds),

            new DriveStraightDistance(westCoastDrive, -4, 0.55),
            // Intake Cube
            new DriveStraightDistance(westCoastDrive, 2.7, 0.55)
            // Shoot Cube
        );
    }
}
