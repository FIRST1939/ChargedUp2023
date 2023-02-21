package frc.robot.commands.autonomous.modes;

public class 1GP_Taxi extends SequentialCommandGroup {
    
    public 1GP_Taxi (WestCoastDrive westCoastDrive, Manipulator manipulator, Photonvision photonvision) {

        this.addCommands(
            new WaitCommand(SmartDashboard.getNumber("Auto Start Wait", 0.0)),
            new HoldArmPosition(manipulator, Constants.ManipulatorConstants.ARM_POSITIONS.TOP.position).withTimeout(1.2),

            new ParallelCommandGroup(
                new HoldArmPosition(manipulator, Constants.ManipulatorConstants.ARM_POSITIONS.TOP.position),
                new RunManipulator(manipulator, 0.8)
            ).withTimeout(1.2),

            new DriveStraightDistance(westCoastDrive, 0.25, 0.35),
            new ResetArmPosition(manipulator, 0.5),
            new DriveAprilTagDistance(westCoastDrive, photonvision, 2.8, 0.4)
        );
    }
}
