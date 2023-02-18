package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.WestCoastDrive;

public class TestAuto extends SequentialCommandGroup {

    public TestAuto (final WestCoastDrive westCoastDrive) {

        this.addCommands(

            new WaitCommand(SmartDashboard.getNumber("Auto Start Wait", 0.0)),

            // Start from Grid 9 on red, 1 on Blue

            // Score for preload cargo
       
            
            //westCoastDrive.drive(5, 10)
            // put arm back

            // Drive to Second GP
            new DriveStraightDistance(westCoastDrive, .5, 0.6), //90
            new TurnToRelativeAngle(westCoastDrive, 90),
            
            new DriveStraightDistance(westCoastDrive, .5, 0.6), 
            new TurnToRelativeAngle(westCoastDrive, 90),
            
            new DriveStraightDistance(westCoastDrive, .5, 0.6), //90
            new TurnToRelativeAngle(westCoastDrive, 90),
            
            new DriveStraightDistance(westCoastDrive, .5, 0.6),
            new TurnToRelativeAngle(westCoastDrive, 90)
            // new DriveStraightDistance(westCoastDrive, -1.524, 0.2), //60

            // // Intake Second GP 
            // new WaitCommand(1.5),

            // // Drive to Community 
            // new DriveStraightDistance(westCoastDrive, 1.524, 0.2), //60
            
            // // Extend Arm to Low Row

            // // Drop Second GP on the Ground 
            // new WaitCommand(1.5),
            // // Drive to Thrid GP
            // new TurnToRelativeAngle(westCoastDrive, 25.5),
            // new DriveStraightDistance(westCoastDrive, -2.032, 0.3), //80
            
            // // Intake Thrid GP 
            // new WaitCommand(1.5),
            
            // // Drive to Community 
            // new DriveStraightDistance(westCoastDrive, 2.032, 0.3), //80

            // // Extend Arm to Low Row

            // // Drop Third GP on the Ground
            // new WaitCommand(1.5),
            // // Drive to Fourth GP 
            // new TurnToRelativeAngle(westCoastDrive, -15),
            // new DriveStraightDistance(westCoastDrive, -2.794, 0.3) //110

            // // Intake Fourth GP
        );
    }
}