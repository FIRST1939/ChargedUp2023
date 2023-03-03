package frc.robot.commands.autonomous.modes;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.autonomous.DriveStraightDistance;
import frc.robot.commands.intaker.RunIntaker;
import frc.robot.commands.intaker.RunSlider;
import frc.robot.commands.manipulator.HoldArmPosition;
import frc.robot.commands.manipulator.ResetArmPosition;
import frc.robot.commands.manipulator.RunManipulator;
import frc.robot.commands.manipulator.SetGamePiece;
import frc.robot.subsystems.Intaker;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class Auto1GP_Taxi extends SequentialCommandGroup {
    
    public Auto1GP_Taxi (WestCoastDrive westCoastDrive, Manipulator manipulator, Intaker intaker, LEDs leds) {

        this.addCommands(
            new WaitCommand(SmartDashboard.getNumber("Auto Start Wait", 0.0)),
            new SetGamePiece(manipulator, leds, -1),
            new HoldArmPosition(manipulator, Constants.ManipulatorConstants.ARM_POSITIONS.TOP).withTimeout(2.6),
            new RunManipulator(manipulator, () -> 0.8).withTimeout(0.8),
            new ResetArmPosition(manipulator, 0.75).withTimeout(3.0),
            new SetGamePiece(manipulator, leds, 0),
            new DriveStraightDistance(westCoastDrive, -4.1, 0.55),

            new SetGamePiece(manipulator, leds, 1),
            new RunSlider(intaker, () -> 1.0).withTimeout(0.5),
            new RunIntaker(intaker, () -> 1.0).withTimeout(0.8),
            new RunSlider(intaker, () -> -1.0).withTimeout(0.5),
            new DriveStraightDistance(westCoastDrive, 3.5, 0.45)
        );
    }
}
