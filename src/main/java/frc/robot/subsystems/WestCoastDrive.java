package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.drivetrain.DifferentialDrive;
import frc.lib.drivetrain.MotorGroup;
import frc.lib.drivetrain.Utils;
import frc.robot.Constants;

public class WestCoastDrive extends SubsystemBase {
    
  private final AHRS navX;
  private final MotorGroup lefMotorGroup;
  private final MotorGroup rightMotorGroup;
  private final DifferentialDrive differentialDrive;
  private final RelativeEncoder leftNeoEncoder;
  private final RelativeEncoder rightNeoEncoder;

  public final DifferentialDriveKinematics differentialDriveKinematics;
  public final DifferentialDriveOdometry differentialDriveOdometry;

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
    this.leftNeoEncoder = this.lefMotorGroup.backMotor.getEncoder();
    this.rightNeoEncoder = this.rightMotorGroup.backMotor.getEncoder();

    this.differentialDriveKinematics = new DifferentialDriveKinematics(Constants.WestCoastConstants.TRACK_WIDTH);
    this.differentialDriveOdometry = new DifferentialDriveOdometry(this.navX.getRotation2d(), this.getLeftDistance(), this.getRightDistance());
    this.differentialDriveOdometry.resetPosition(this.navX.getRotation2d(), this.getLeftDistance(), this.getRightDistance(), new Pose2d());
  }

  public void periodic () {

    SmartDashboard.putNumber("NavX Angle", this.navX.getRotation2d().getDegrees());
    SmartDashboard.putNumber("NavX Pitch", this.navX.getPitch());

    this.differentialDriveOdometry.update(this.navX.getRotation2d(), this.leftNeoEncoder.getPosition(), this.rightNeoEncoder.getPosition());
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

  /**
   * Resets the distance sensors (aka encoders) of the drive train.
   */
  public void resetDistance () {

    this.leftNeoEncoder.setPosition(0.0);
    this.rightNeoEncoder.setPosition(0.0);
  }

  public double getLeftDistance () { return Utils.motorRotationsToMeters(-this.leftNeoEncoder.getPosition()); }
  public double getRightDistance () { return Utils.motorRotationsToMeters(this.rightNeoEncoder.getPosition()); }

  /**
   * Note that the returned meters are the average of the left and right side
   * sensors.
   * 
   * @return the distance traveled in inches since the last 
   * {@link #resetDistance()} call.
   */
  public double getAverageDistance () {

    double distance = 0.0;
    distance += this.getLeftDistance();
    distance += this.getRightDistance();
    return (distance / 2.0);
  }

  /**
   * @return the wheel speeds in meters per second.
   */
  public DifferentialDriveWheelSpeeds getWheelSpeeds () { 

    double leftVelocity = Utils.motorRotationsToMeters(this.leftNeoEncoder.getVelocity() / 60.0);
    double rightVelocity = Utils.motorRotationsToMeters(this.rightNeoEncoder.getVelocity() / 60.0);  
    return new DifferentialDriveWheelSpeeds(leftVelocity, rightVelocity);
  }

  /**
   * @return the average motor rotations/minute of the left and right sides.
   */
  public double getAverageVelocity () {
    
    double velocity = 0.0;
    velocity += this.leftNeoEncoder.getVelocity();
    velocity -= this.rightNeoEncoder.getVelocity();
    return (velocity / 2.0);
  }

  public Pose2d getPose () { return this.differentialDriveOdometry.getPoseMeters(); }
  public void resetOdometry (Pose2d pose2d) {

    this.resetDistance();
    this.differentialDriveOdometry.resetPosition(this.navX.getRotation2d(), this.getLeftDistance(), this.getRightDistance(), pose2d);
  }
}
