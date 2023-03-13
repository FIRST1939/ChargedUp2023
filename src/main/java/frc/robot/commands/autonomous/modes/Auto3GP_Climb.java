package frc.robot.commands.autonomous.modes;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

public class Auto3GP_Climb extends SequentialCommandGroup {

    public Auto3GP_Climb(WestCoastDrive westCoastDrive, Manipulator manipulator, AHRS navX, LEDs leds) {

        this.addCommands(
                new WaitCommand(SmartDashboard.getNumber("Auto Start Wait", 0.0)),

                // Shoot 1st cube
                new WaitCommand(1.0),

                // SHOOTING

                // drive to intake 2nd cube, comeback and shoot
                new DriveStraightDistance(westCoastDrive, -2.3, 0.5),
                // INTAKE OUT
                new WaitCommand(1.0),
                new DriveStraightDistance(westCoastDrive, 1.9, 0.5),
                // SHOOTING
                new WaitCommand(1.0),
                // drive to intake 3rd cube and climb
                new DriveStraightDistance(westCoastDrive, -2.3, 0.5),
                new WaitCommand(1.0),
                new TurnToRelativeAngle(westCoastDrive, 90, 0.35),

                new WaitCommand(1.0),

                new DriveStraightDistance(westCoastDrive, -2.0, 0.5),
                // INTAKE OUT
                new WaitCommand(1.0),
                // after intake 3rd cube

                // drive to climb
                new TurnToRelativeAngle(westCoastDrive, -90, 0.4),
                new WaitCommand(1.0),
                // Balance Charging Station
                new DriveStraightDistance(westCoastDrive, 0.3, 0.35),
                new TurnToRelativeAngle(westCoastDrive, 180, 0.35),
                new BalanceChargingStation(westCoastDrive, navX)

        // after balance, shoot 3rd cube
        // SHOOTING
        );
    }
}
