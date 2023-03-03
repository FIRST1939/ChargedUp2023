package frc.robot.commands.autonomous.modes;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.manipulator.HoldArmPosition;
import frc.robot.commands.manipulator.ResetArmPosition;
import frc.robot.commands.manipulator.RunManipulator;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.WestCoastDrive;

public class Auto1GP extends SequentialCommandGroup {
    
    public Auto1GP (WestCoastDrive westCoastDrive, Manipulator manipulator) {

        this.addCommands(
            new WaitCommand(SmartDashboard.getNumber("Auto Start Wait", 0.0)),
            new HoldArmPosition(manipulator, Constants.ManipulatorConstants.ARM_POSITIONS.TOP).withTimeout(2.6),
            new RunManipulator(manipulator, () -> 0.8).withTimeout(0.8),
            new ResetArmPosition(manipulator, 0.75).withTimeout(3.0)
        );
    }
}
