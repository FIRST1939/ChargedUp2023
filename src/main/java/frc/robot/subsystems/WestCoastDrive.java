package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class WestCoastDrive extends SubsystemBase {
    
  private final AHRS navX;
  private final MotorGroup lefMotorGroup;
  private final MotorGroup rightMotorGroup;
  private final DifferentialDrive differentialDrive;
  public final DifferentialDriveKinematics differentialDriveKinematics;

  public WestCoastDrive (AHRS navX) {
    
    this.navX = navX;

    this.lefMotorGroup = new MotorGroup(
      Constants.WestCoastConstants.BACK_LEFT_MOTOR, 
      Constants.WestCoastConstants.CENTER_LEFT_MOTOR, 
      Constants.WestCoastConstants.FRONT_LEFT_MOTOR
    );

    this.rightMotorGroup = new MotorGroup(
      Constants.WestCoastConstants.BACK_RIGHT_MOTOR,
      Constants.WestCoastConstants.CENTER_RIGHT_MOTOR,
      Constants.WestCoastConstants.FRONT_RIGHT_MOTOR
    );

    this.differentialDrive = new DifferentialDrive(this.lefMotorGroup, this.rightMotorGroup);
    this.differentialDriveKinematics = new DifferentialDriveKinematics(Constants.WestCoastConstants.TRACK_WIDTH);
  }

  public void periodic () {

    // NavX's getAngle method is inverted from the generally agrred upon standard Rotation2d.
    SmartDashboard.putNumber("NavX Raw Angle", this.navX.getAngle());
    SmartDashboard.putNumber("NavX Processed Rotation", this.navX.getRotation2d().getDegrees());
    }

  /**
   * @param speed    the raw speed input.
   * @param rotation the raw rotation input.
   */
  public void drive (double speed, final double rotation) {

    double arcadeSpeed = speed;
    double arcadeRotation = 0.7 * rotation;
    this.differentialDrive.arcadeDrive(arcadeSpeed, arcadeRotation);
  }

  public void stop () { this.drive(0.0, 0.0); }

  /**
   * Returns the current adjusted robot oriented heading. For time limited robot
   * oriented movements, prefer this method over getYaw.
   * 
   * @return the current robot oriented heading.
   */
  public double getHeading () { return this.navX.getRotation2d().getDegrees(); }

  /**
   * Configures the getHeading method to return 0. For time limited robot oriented
   * movements, prefer this method over resetYaw.
   */
  public void resetHeading () { this.navX.setAngleAdjustment(-this.getYaw()); }

  /**
   * This should rarely be used. For time limited robot oriented movements, prefer
   * resetHeading and getHeading.
   * 
   * @return the current yaw value in degrees (-180 to 180).
   */
  public float getYaw () { return this.navX.getYaw(); }

  /**
   * Resets the robot's yaw to 0.
   * 
   * WARNING: Use this only in extreme cases. Sometimes the reset can take time
   * and getYaw could return non-zero values for a short time after this is
   * called. For time limited robot oriented movements, prefer resetHeading and
   * getHeading.
   */
  public void resetYaw() { this.navX.reset(); }
}
