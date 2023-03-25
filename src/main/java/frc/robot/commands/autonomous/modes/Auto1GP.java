package frc.robot.commands.autonomous.modes;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.manipulator.HoldArmPosition;
import frc.robot.commands.manipulator.ResetArmPosition;
import frc.robot.commands.manipulator.RunManipulator;
import frc.robot.commands.manipulator.SetGamePiece;
import frc.robot.subsystems.LEDs;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class Auto1GP extends SequentialCommandGroup {
    
    public Auto1GP (WestCoastDrive westCoastDrive, Manipulator manipulator, LEDs leds) {

        this.addCommands(
            new SetGamePiece(manipulator, leds, -1),
            new HoldArmPosition(manipulator, Constants.ManipulatorConstants.ARM_POSITIONS.HIGH).withTimeout(1.95),
            new RunManipulator(manipulator, () -> 0.8).withTimeout(0.8),
            new ResetArmPosition(manipulator).withTimeout(3.0),
            new SetGamePiece(manipulator, leds, 0)
        );
    }
}
