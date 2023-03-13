package frc.robot.commands.autonomous.modes;

import edu.wpAuto2GPlibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.autonomous.drivetrain.DriveStraightDistance;
import frc.robot.commands.autonomous.drivetrain.TurnToRelativeAngle;
import frc.robot.commands.manipulator.HoldArmPosition;
import frc.robot.commands.manipulator.ResetArmPosition;
import frc.robot.commands.manipulator.RunManipulator;
import frc.robot.commands.manipulator.SetGamePiece;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class Auto2GP extends SequentialCommandGroup {

    public Auto2GP(WestCoastDrive westCoastDrive, Manipulator manipulator, AHRS navX, LEDs leds) {

        this.addCommands(
                new WaitCommand(SmartDashboard.getNumber("Auto Start Wait", 0.0)),
                // This auto score 2GP + Taxi (one hige cone, one high cube)
                // This auto can start from both red and blue side near the loading station wall

                // Score Cone
                /*
                 * new SetGamePiece(manipulator, leds, -1),
                 * new HoldArmPosition(manipulator,
                 * Constants.ManipulatorConstants.ARM_POSITIONS.TOP).withTimeout(2.6),
                 * new RunManipulator(manipulator, () -> 0.8).withTimeout(0.8),
                 * new ResetArmPosition(manipulator, 0.75).withTimeout(3.0),
                 * new SetGamePiece(manipulator, leds, 0),
                 */
                new DriveStraightDistance(westCoastDrive, -4, 0.55),
                // INTAKE OUT
                new WaitCommand(1),
                new DriveStraightDistance(westCoastDrive, 2.7, 0.55)
        // SHOOTING

        );
    }
}
