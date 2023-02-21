package frc.robot.commands.autonomous.modes;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.autonomous.DriveStraightDistance;
import frc.robot.commands.autonomous.RunManipulator;
import frc.robot.commands.manipulator.HoldArmPosition;
import frc.robot.commands.manipulator.ResetArmPosition;
import frc.robot.subsystems.Manipulator;
import frc.robot.subsystems.Photonvision;
import frc.robot.subsystems.WestCoastDrive;

public class Auto1GP_Taxi extends SequentialCommandGroup {
    
    public Auto1GP_Taxi (WestCoastDrive westCoastDrive, Manipulator manipulator, Photonvision photonvision) {

        this.addCommands(
            new WaitCommand(SmartDashboard.getNumber("Auto Start Wait", 0.0)),
            new HoldArmPosition(manipulator, 66000).withTimeout(2.6),
            new RunManipulator(manipulator, 0.8).withTimeout(0.8),
            new ResetArmPosition(manipulator, 0.75).withTimeout(3.0),
            new DriveStraightDistance(westCoastDrive, -2.2, 0.4)
        );
    }
}
