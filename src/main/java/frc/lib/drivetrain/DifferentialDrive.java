package frc.lib.drivetrain;

public class DifferentialDrive {
    
    private MotorGroup leftMotorGroup;
    private MotorGroup rightMotorGroup;

    public DifferentialDrive (MotorGroup leftMotorGroup, MotorGroup rightMotorGroup) {

        this.leftMotorGroup = leftMotorGroup;
        this.rightMotorGroup = rightMotorGroup;
    }

    public void arcadeDrive (double speed, double rotation) {

        var wheelStates = edu.wpi.first.wpilibj.drive.DifferentialDrive.arcadeDriveIK(speed, rotation, true);
        this.leftMotorGroup.set(wheelStates.left);
        this.rightMotorGroup.set(-wheelStates.right);
    }
}
